package asm;

import asm.*;
import asm.operand.*;
import asm.instruction.*;

import ir.*;
import ir.instruction.*;
import ir.type.*;
import ir.entity.*;

import util.error.*;
import util.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.lang.Math;

public class InstructionSelector implements IRVisitor {
    public ASMModule module;
    public ASMFunction currentFunction;
    public ASMBlock currentBlock;

    public int virtualRegPos, totalStackBytes;

    public LinkedHashMap<IRBasicBlock, ASMBlock> blockMap = new LinkedHashMap<>();
    private ASMPhysicalRegister regSp = ASMPhysicalRegister.pRegMap.get("sp");
    private ASMPhysicalRegister regA0 = ASMPhysicalRegister.pRegMap.get("a0");
    private ASMPhysicalRegister regRa = ASMPhysicalRegister.pRegMap.get("ra");
    public InstructionSelector(ASMModule module) {
        this.module = module;
    }

//    private ASMRegister immGetRegister(ASMImmediate imm) {
//        ASMVirtualRegister reg = new ASMVirtualRegister(4);
//        currentBlock.addInstruction(new ASMLi(reg, imm));
//        return reg;
//    } // 不兼容，不 li 怎么知道立即数是多少

    private ASMRegister getASMRegister(Entity entity) {
//        if (entity.asmRegister instanceof ASMPhysicalRegister && ((ASMPhysicalRegister) entity.asmRegister).label.equals("s5")) {
//            System.out.println(entity.toString());
//        }
        if (entity.asmRegister == null || entity.asmRegister instanceof ASMStackLocation) {
            if (entity instanceof IRRegister) {
                entity.asmRegister = new ASMVirtualRegister(entity.type.getBytes()); // TODO: check
            }
            else if (entity instanceof IRConst) {
                entity.asmRegister = new ASMVirtualImmediate((IRConst) entity);
            }
        }
        return entity.asmRegister;
    }

    private void addStore(int size, ASMRegister value, ASMRegister addr, int imm) {
        // 存到 reg[addr] + imm
        if (imm < 2048 && imm >= -2048) {
            currentBlock.addInstruction(new ASMStore(size, value, new ASMImmediate(imm), addr));
        }
        else {
            ASMVirtualRegister reg = new ASMVirtualRegister(4);
            currentBlock.addInstruction(
                    new ASMRType("add", reg, addr,
                            new ASMVirtualImmediate(imm)
                    )
            );
            currentBlock.addInstruction(new ASMStore(size, value, reg));
        }
    }

    private void addLoad(int size, ASMRegister toReg, ASMRegister addr, int imm) {
        // 从 reg[addr] + imm 加载
        if (imm < 2048 && imm >= -2048) {
            currentBlock.addInstruction(new ASMLoad(size, toReg, new ASMImmediate(imm), addr));
        }
        else {
            ASMVirtualRegister reg = new ASMVirtualRegister(4);
            currentBlock.addInstruction(
                    new ASMRType("add", reg, addr,
                            new ASMVirtualImmediate(imm)
                    )
            );
            currentBlock.addInstruction(new ASMLoad(size, toReg, reg));
        }
    }

    @Override
    public void visit(IRProgram it) {
        for (var str: it.stringArray) {
            ASMGlobalString globalString = new ASMGlobalString(".str", str.value);
            module.strings.add(globalString);
            str.asmRegister = globalString;
        }

        for (var globalVar: it.globalVars) {
            globalVar.asmRegister = new ASMGlobalValue(globalVar);
            module.globalValues.add((ASMGlobalValue) globalVar.asmRegister);
        }

        for (var func: it.functions) {
            LinearScan linearScan = new LinearScan(func);
            currentFunction = new ASMFunction(func.name);
            module.functions.add(currentFunction);
            func.accept(this);
            currentFunction = null;
        }
    }

    @Override
    public void visit(IRFunction it) {
        ASMVirtualRegister.cnt = 0;
        int maxParam = 0;
        blockMap = new LinkedHashMap<>();

        for (IRBasicBlock block: it.blocks) {
            ASMBlock tmpBlock = new ASMBlock(block.label);
            blockMap.put(block, tmpBlock);
            currentFunction.add(tmpBlock);
            for (IRInstruction instruction: block.instructions) {
                if (instruction instanceof IRCall) {
                    maxParam = Math.max(maxParam, ((IRCall) instruction).args.size());
                }
            }
        }
        currentFunction.exceedingParamBytes = (maxParam > 8) ? (maxParam - 8) * 4 : 0;
        // caller 保存多余的参数

        for (int i = 0; i < it.paramList.size(); ++i) {
            IRRegister param = it.paramList.get(i);
            param.asmRegister = i < 8 ?
                new ASMPhysicalRegister("a" + String.valueOf(i)) :
                new ASMVirtualRegister(4, i); // exceeding parameter，由于函数的栈空间最靠近栈顶的位置就是存的 exceeding param.，所以继续往上走就能找到它
        }

        if (!it.blocks.isEmpty()) {
            currentBlock = blockMap.get(it.blocks.getFirst());
            addStore(4, regRa, regSp, currentFunction.exceedingParamBytes);
            // 把 ra 的值存到相应栈空间，ra 有可能被调用的其他函数占了
        }
        for (var irBlock: it.blocks) {
            currentBlock = blockMap.get(irBlock);
            irBlock.accept(this);
            currentBlock = null;
        }

        currentFunction.virtualRegCnt = ASMVirtualRegister.cnt;
        currentFunction.virtualRegBytes = currentFunction.virtualRegCnt << 2;
        currentFunction.totalStackSize = currentFunction.exceedingParamBytes
                + currentFunction.virtualRegBytes + currentFunction.allocaBytes;

        ASMBlock entryBlock = currentFunction.blocks.get(0);
        ASMBlock exitBlock = currentFunction.blocks.get(currentFunction.blocks.size() - 1);

        currentBlock = entryBlock;
        entryBlock.instructions.addFirst(
                new ASMRType("sub", regSp, regSp,
                        new ASMVirtualImmediate(currentFunction.totalStackSize))
        );

        currentBlock = exitBlock;
        exitBlock.instructions.add(new ASMRType(
                "add", regSp, regSp, new ASMVirtualImmediate(currentFunction.totalStackSize))
        );
        exitBlock.instructions.add(new ASMReturn());

        currentBlock = null;
    }

    @Override
    public void visit(IRBasicBlock it) {
        for (var instruction: it.instructions) {
            instruction.accept(this);
        }
        if (it.terminal != null) {
            it.terminal.accept(this);
        }
    }

    @Override
    public void visit(IRAlloca it) {
        currentBlock.addInstruction(
                new ASMRType("add", getASMRegister(it.reg), regSp,
                        new ASMVirtualImmediate(
                                currentFunction.exceedingParamBytes + currentFunction.allocaBytes
                                ))
        );
        currentFunction.allocaBytes += 4;
    }

    @Override
    public void visit(IRBinaryOpt it) {
        // eq, ne, sgt, sge, slt, sle
        ASMVirtualRegister reg = new ASMVirtualRegister(4);
        // 存中间结果的
        ASMRegister lhs = getASMRegister(it.lhs);
        ASMRegister rhs = getASMRegister(it.rhs);
        ASMRegister dest = getASMRegister(it.reg);

        switch (it.opType) {
            case eq -> {
                currentBlock.addInstruction(new ASMRType("xor", reg, lhs, rhs));
                currentBlock.addInstruction(new ASMIType("sltiu", dest, reg, new ASMImmediate(1)));
            }
            case ne -> {
                currentBlock.addInstruction(new ASMRType("xor", reg, lhs, rhs));
                currentBlock.addInstruction(new ASMRType("sltu", dest, new ASMVirtualImmediate(0), reg));
            }
            case sgt -> {
                currentBlock.addInstruction(new ASMRType("slt", dest, rhs, lhs));
            }
            case sge -> {
                currentBlock.addInstruction(new ASMRType("slt", reg, lhs, rhs));
                currentBlock.addInstruction(new ASMIType("xori", dest, reg, new ASMImmediate(1)));
            }
            case slt -> {
                currentBlock.addInstruction(new ASMRType("slt", dest, lhs, rhs));
            }
            case sle -> {
                currentBlock.addInstruction(new ASMRType("slt", reg, rhs, lhs));
                currentBlock.addInstruction(new ASMIType("xori", dest, reg, new ASMImmediate(1)));
            }
        }
    }

    @Override
    public void visit(IRBranch it) {
        currentBlock.addInstruction(new ASMBranch(ASMBranch.Type.bnez, getASMRegister(it.condition), blockMap.get(it.thenBlock)));
        currentBlock.addInstruction(new ASMJump(blockMap.get(it.elseBlock)));
//        currentBlock.suc.add(blockMap.get(it.thenBlock));
//        currentBlock.suc.add(blockMap.get(it.elseBlock));
    }

    @Override
    public void visit(IRCalc it) {
        // add, sub, mul, sdiv, srem, shl, ashr, and, or, xor
        ASMRegister lhs = getASMRegister(it.lhs);
        ASMRegister rhs = getASMRegister(it.rhs);
        currentBlock.addInstruction(new ASMRType(it.opType.toString(), getASMRegister(it.reg), lhs, rhs));
    }

    @Override
    public void visit(IRCall it) {
        // 存东西
        for (int i = 0; i < it.args.size(); ++i) {
            if (i < 8) {
                currentBlock.addInstruction(new ASMMove(ASMPhysicalRegister.pRegMap.get("a" + i), getASMRegister(it.args.get(i))));
            }
            else {
                addStore(it.args.get(i).type.getBytes(), getASMRegister(it.args.get(i)), regSp, (i - 8) << 2);
            }
        }

        currentBlock.addInstruction(new ASMCall(it.name));

        // 返回值
        if (it.returnType != null && !(it.returnType instanceof IRVoid) && !(it.returnType instanceof IRNull)) {
            currentBlock.addInstruction(new ASMMove(getASMRegister(it.addr), regA0));
        }
    }

    @Override
    public void visit(IRGetElementPtr it) {
        // 算地址，存到 dest 里面
        if (it.type instanceof IRStruct) {
            // 是类的解引用，要取第二个参数
            ASMRegister id = getASMRegister(it.idxs.get(1));
            ASMVirtualRegister reg = new ASMVirtualRegister(4);
            currentBlock.addInstruction(new ASMRType("mul", reg, id, new ASMVirtualImmediate(4))); // 2?????????????
            currentBlock.addInstruction(new ASMRType("add", getASMRegister(it.dest), reg, getASMRegister(it.ptr)));
        }
        else if (it.type instanceof IRInt && ((IRInt) it.type).getBytes() == 1) {
            // 免 * 4
            currentBlock.addInstruction(new ASMRType("add", getASMRegister(it.dest), getASMRegister(it.ptr), getASMRegister(it.idxs.get(0))));
        }
        else {
            ASMRegister id = getASMRegister(it.idxs.get(0));
            ASMVirtualRegister reg = new ASMVirtualRegister(4);
            currentBlock.addInstruction(new ASMRType("mul", reg, id, new ASMVirtualImmediate(4)));
            currentBlock.addInstruction(new ASMRType("add", getASMRegister(it.dest), reg, getASMRegister(it.ptr)));
        }
    }

    @Override
    public void visit(IRJump it) {
        currentBlock.addInstruction(new ASMJump(blockMap.get(it.dest)));
        // currentBlock.suc.add(blockMap.get(it.dest));
    }

    @Override
    public void visit(IRLoad it) {
        addLoad(it.type.getBytes(), getASMRegister(it.dest), getASMRegister(it.entity), 0);
    }

    @Override
    public void visit(IRStore it) {
        addStore(it.val.type.getBytes(), getASMRegister(it.val), getASMRegister(it.addr), 0);
    }

    @Override
    public void visit(IRRet it) {
        // 如果返回值不是 void，那么需要把返回值存回 a0
        // ra 存的是返回地址！！
        addLoad(4, regRa, regSp, currentFunction.exceedingParamBytes);
        // 从栈上 alloca 出的第一个位置取返回值（约定好的）
        if (it.value == null || it.value instanceof IRNullConst || it.value instanceof IRVoidConst) {
            // 直接return
            return;
        }
        currentBlock.addInstruction(new ASMMove(regA0, getASMRegister(it.value)));
    }
}