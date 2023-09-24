package asm;

import java.util.HashMap;

import ir.*;
import ir.instruction.*;
import ir.entity.*;
import ir.type.*;
import asm.*;
import asm.operand.*;
import util.*;
import asm.instruction.*;

public class InstructionSelector implements IRVisitor {
    ASMModule module;
    ASMFunction curFunc;
    ASMBlock curBlock;
    int blockCnt = 0;

    HashMap<IRBasicBlock, ASMBlock> blockMap = new HashMap<>();

    public InstructionSelector(ASMModule module) {
        this.module = module;
    }

    ASMRegister getReg(Entity entity) {
        if (entity.asmRegister == null) {
            assert !(entity instanceof IRGlobalVar);
            assert !(entity instanceof IRStringConst);
            if (entity instanceof IRRegister) {
                entity.asmRegister = new ASMVirtualRegister(entity.type.getBytes());
            } else if (entity instanceof IRConst) {
                entity.asmRegister = new ASMVirtualImmediate((IRConst) entity);
            }
        }
        return entity.asmRegister;
    }
    void storeReg(int size, ASMRegister value, ASMRegister dest, int offset) {
        if (offset < 1 << 11)
            curBlock.addInstruction(new ASMStore(size, value, new ASMImmediate(offset), dest));
        else {
            ASMVirtualRegister tmp = new ASMVirtualRegister(4);
            curBlock.addInstruction(new ASMRType("add", tmp, dest, new ASMVirtualImmediate(offset)));
            curBlock.addInstruction(new ASMStore(size, tmp, value));
        }
    }
    void loadReg(int size, ASMRegister dest, ASMRegister src, int offset) {
        if (offset < 1 << 11)
            curBlock.addInstruction(new ASMLoad(size, dest, new ASMImmediate(offset), src));
        else {
            ASMVirtualRegister tmp = new ASMVirtualRegister(4);
            curBlock.addInstruction(new ASMRType("add", tmp, src, new ASMVirtualImmediate(offset)));
            curBlock.addInstruction(new ASMLoad(size, dest, tmp));
        }
    }

    public void visit(IRProgram node) {
        // add global vars
        node.globalVars.forEach(globalVar -> {
            globalVar.asmRegister = new ASMGlobalValue(globalVar);
            module.globalValues.add((ASMGlobalValue) globalVar.asmRegister);
        });
        node.stringConsts.values().forEach(str -> {
            ASMGlobalString globalStr = new ASMGlobalString(".str." + String.valueOf(str.id), str.value);
            module.strings.add(globalStr);
            str.asmRegister = globalStr;
        });
//        if (node.initFunc != null) {
//            curFunc = new ASMFunction(node.initFunc.name);
//            module.functions.add(curFunc);
//            node.initFunc.accept(this);
//        }
        node.functions.forEach(func -> {
            curFunc = new ASMFunction(func.name);
            module.functions.add(curFunc);
            func.accept(this);
        });
    }

    public void visit(IRFunction node) {
        // add params
        blockMap.clear();
        ASMVirtualRegister.cnt = 0;
        // find max argument cnt
        int maxArgCnt = 0;
        for (IRBasicBlock blk : node.blocks) {
            blockMap.put(blk, new ASMBlock(".L" + blockCnt++));
            for (IRInstruction inst : blk.instructions)
                if (inst instanceof IRCall)
                    maxArgCnt = Math.max(maxArgCnt, ((IRCall) inst).args.size());
        }
        curFunc.exceedingParamBytes = (maxArgCnt > 8 ? maxArgCnt - 8 : 0) << 2;
        // set params
        for (int i = 0; i < node.paramList.size(); ++i)
            if (i < 8)
                node.paramList.get(i).asmRegister = new ASMPhysicalRegister("a" + i);
            else
                node.paramList.get(i).asmRegister = new ASMVirtualRegister(4, i);

        for (int i = 0; i < node.blocks.size(); ++i) {
            curBlock = blockMap.get(node.blocks.get(i));
            if (i == 0)
                storeReg(4, new ASMPhysicalRegister("ra"), new ASMPhysicalRegister("sp"), curFunc.exceedingParamBytes);
            node.blocks.get(i).accept(this);
            curFunc.add(curBlock);
        }
        curFunc.virtualRegCnt = ASMVirtualRegister.cnt;
        // set stack frame
        curFunc.virtualRegBytes = curFunc.virtualRegCnt * 4;
        curFunc.totalStackSize = curFunc.exceedingParamBytes + curFunc.allocaBytes + curFunc.virtualRegCnt * 4;
        ASMBlock entryBlock = curFunc.blocks.get(0), exitBlock = curFunc.blocks.get(curFunc.blocks.size() - 1);
        entryBlock.instructions.addFirst(new ASMRType("add", new ASMPhysicalRegister("sp"), new ASMPhysicalRegister("sp"),
                new ASMVirtualImmediate(-curFunc.totalStackSize)));
        exitBlock.instructions.add(new ASMRType("add", new ASMPhysicalRegister("sp"), new ASMPhysicalRegister("sp"),
                new ASMVirtualImmediate(curFunc.totalStackSize)));
        exitBlock.instructions.add(new ASMReturn());
    }

    public void visit(IRBasicBlock node) {
        node.instructions.forEach(inst -> {
            inst.accept(this);
        });
        node.terminal.accept(this);
    }

    public void visit(IRAlloca node) {
        curBlock.addInstruction(new ASMRType("add", getReg(node.reg), new ASMPhysicalRegister("sp"),
                new ASMVirtualImmediate(curFunc.exceedingParamBytes + curFunc.allocaBytes)));
        curFunc.allocaBytes += 4;
    }

    public void visit(IRBranch node) {
        curBlock.addInstruction(new ASMBranch(ASMBranch.Type.beqz, getReg(node.condition), blockMap.get(node.elseBlock)));
        curBlock.addInstruction(new ASMJump(blockMap.get(node.thenBlock)));
    }

    public void visit(IRCalc node) {
        curBlock.addInstruction(new ASMRType(node.opType.toString(), getReg(node.reg), getReg(node.lhs), getReg(node.rhs)));
    }

    public void visit(IRCall node) {
        for (int i = 0; i < node.args.size(); ++i) {
            Entity arg = node.args.get(i);
            if (i < 8)
                curBlock.addInstruction(new ASMMove(new ASMPhysicalRegister("a" + i), getReg(arg)));
            else
                storeReg(arg.type.getBytes(), getReg(arg), new ASMPhysicalRegister("sp"), i - 8 << 2);
        }
        curBlock.addInstruction(new ASMCall(node.name));
        if (node.addr != null)
            curBlock.addInstruction(new ASMMove(getReg(node.addr), new ASMPhysicalRegister("a0")));
    }

    public void visit(IRGetElementPtr node) {
        if (node.type instanceof IRInt && ((IRInt) node.type).getBytes() == 1) {
            curBlock.addInstruction(new ASMRType("add", getReg(node.dest), getReg(node.ptr), getReg(node.idxs.get(0))));
        } else {
            ASMRegister idx = node.type instanceof IRStruct ? getReg(node.idxs.get(1)) : getReg(node.idxs.get(0));
            ASMVirtualRegister tmp = new ASMVirtualRegister(4);
            curBlock.addInstruction(new ASMIType("slli", tmp, idx, new ASMImmediate(2)));
            curBlock.addInstruction(new ASMRType("add", getReg(node.dest), getReg(node.ptr), tmp));
        }
    }

    public void visit(IRBinaryOpt node) {
        // LLVM_IR: eq, ne, sgt, sge, slt, sle
        // RISCV32_ASM: seqz, snez, slt
        ASMVirtualRegister tmp = new ASMVirtualRegister(4);
        switch (node.opType.toString()) {
            case "eq":
                curBlock.addInstruction(new ASMRType("sub", tmp, getReg(node.lhs), getReg(node.rhs)));
                curBlock.addInstruction(new ASMIType("seqz", getReg(node.reg), tmp));
                break;
            case "ne":
                curBlock.addInstruction(new ASMRType("sub", tmp, getReg(node.lhs), getReg(node.rhs)));
                curBlock.addInstruction(new ASMIType("snez", getReg(node.reg), tmp));
                break;
            case "sgt":
                curBlock.addInstruction(new ASMRType("slt", getReg(node.reg), getReg(node.rhs), getReg(node.lhs)));
                break;
            case "sge":
                curBlock.addInstruction(new ASMRType("slt", tmp, getReg(node.lhs), getReg(node.rhs)));
                curBlock.addInstruction(new ASMIType("xori", getReg(node.reg), tmp, new ASMImmediate(1)));
                break;
            case "slt":
                curBlock.addInstruction(new ASMRType("slt", getReg(node.reg), getReg(node.lhs), getReg(node.rhs)));
                break;
            case "sle":
                curBlock.addInstruction(new ASMRType("slt", tmp, getReg(node.rhs), getReg(node.lhs)));
                curBlock.addInstruction(new ASMIType("xori", getReg(node.reg), tmp, new ASMImmediate(1)));
                break;
        }
    }

    public void visit(IRJump node) {
        curBlock.addInstruction(new ASMJump(blockMap.get(node.dest)));
    }

    public void visit(IRLoad node) {
        loadReg(node.type.getBytes(), getReg(node.dest), getReg(node.entity), 0);
    }

    public void visit(IRRet node) {
        // ret val -> load val to a0 and return
        if (!(node.value instanceof IRVoidConst) && !(node.value instanceof IRNullConst))
            curBlock.addInstruction(new ASMMove(new ASMPhysicalRegister("a0"), getReg(node.value)));
        loadReg(4, new ASMPhysicalRegister("ra"), new ASMPhysicalRegister("sp"), curFunc.exceedingParamBytes);
        // 寄存器分配完再加 ret
    }

    public void visit(IRStore node) {
        // store : rs2 -> (rs1) address
        storeReg(node.val.type.getBytes(), getReg(node.val), getReg(node.addr), 0);
    }
}