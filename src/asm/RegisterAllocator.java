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
import java.util.LinkedList;

public class RegisterAllocator {
    ASMModule module;
    int totalStackBytes, virtualRegPos;

    ASMPhysicalRegister regT0 = new ASMPhysicalRegister("t0");
    ASMPhysicalRegister regT1 = new ASMPhysicalRegister("t1");
    ASMPhysicalRegister regT2 = new ASMPhysicalRegister("t2");
    ASMPhysicalRegister regSp = new ASMPhysicalRegister("sp");

    public RegisterAllocator(ASMModule module) {
        this.module = module;
    }

    public void visitFunction() {
        for (ASMFunction function : module.functions) {
            totalStackBytes = function.totalStackSize;
            virtualRegPos = function.exceedingParamBytes + function.allocaBytes;
            for (ASMBlock block : function.blocks)
                visitBlock(block);
        }
    }

    public void visitBlock(ASMBlock block) {
        LinkedList<ASMInstruction> extendedIns = new LinkedList<>();
        for (var instruction: block.instructions) {
            // 添一些该有的分配指令
            if (instruction.rs1 != null && !(instruction.rs1 instanceof ASMPhysicalRegister)) {
                allocateSrc(extendedIns, regT1, instruction.rs1);
                instruction.rs1 = regT1;
            }
            if (instruction.rs2 != null && !(instruction.rs2 instanceof ASMPhysicalRegister)) {
                allocateSrc(extendedIns, regT0, instruction.rs2);
                instruction.rs2 = regT0;
            }
            extendedIns.add(instruction);
            // 为什么在这里 add？没执行完不要管 rd 的分配状况！！！！！！！！！！！！！！！！11
            if (instruction.rd != null && !(instruction.rd instanceof ASMPhysicalRegister)) {
                allocateDest(extendedIns, regT0, instruction.rd);
                instruction.rd = regT0;
            }
        }
        block.instructions = extendedIns;
    }

    private void allocateSrc(LinkedList<ASMInstruction> insList, ASMPhysicalRegister reg, ASMRegister src) {
        // 从 src 里面 load 到 reg
        if (src instanceof ASMVirtualRegister) {
            int addrOffset = ((ASMVirtualRegister) src).isExceedingParam ?
                    totalStackBytes + ((ASMVirtualRegister) src).parameterId * 4 :
                    virtualRegPos + ((ASMVirtualRegister) src).id * 4;
            // 实际地址 - sp 的值

            if (addrOffset < 2048) {
                insList.add(new ASMLoad(((ASMVirtualRegister) src).size, reg, new ASMImmediate(addrOffset), regSp));
            } else {
                insList.add(new ASMLi(regT2, new ASMVirtualImmediate(addrOffset)));
                insList.add(new ASMRType("add", regT2, regT2, regSp));
                insList.add(new ASMLoad(((ASMVirtualRegister) src).size, reg, regT2));
            }
        }
        else if (src instanceof ASMVirtualImmediate) {
            insList.add(new ASMLi(reg, (ASMVirtualImmediate) src));
        }
        else if (src instanceof ASMGlobal) {
            insList.add(new ASMLa(reg, ((ASMGlobal) src).label));
        }
    }

    private void allocateDest(LinkedList<ASMInstruction> insList, ASMPhysicalRegister reg, ASMRegister dest) {
        // reg 存到 dest
        if (dest instanceof ASMVirtualRegister) {
            int addrOffset = ((ASMVirtualRegister) dest).isExceedingParam ?
                    totalStackBytes + ((ASMVirtualRegister) dest).parameterId * 4 :
                    virtualRegPos + ((ASMVirtualRegister) dest).id * 4;
            // 实际地址 - sp 的值

            if (addrOffset < 2048) {
                insList.add(new ASMStore(((ASMVirtualRegister) dest).size, reg, new ASMImmediate(addrOffset), regSp));
            } else {
                insList.add(new ASMLi(regT2, new ASMVirtualImmediate(addrOffset)));
                insList.add(new ASMRType("add", regT2, regT2, regSp));
                insList.add(new ASMStore(((ASMVirtualRegister) dest).size, reg, regT2));
            }
        }
    }
}