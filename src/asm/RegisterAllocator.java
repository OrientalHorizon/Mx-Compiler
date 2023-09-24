package asm;

import java.util.LinkedList;

import asm.*;
import asm.operand.*;
import asm.instruction.*;

public class RegisterAllocator {
    ASMModule module;
    int totalStack, virtualRegBegin;
    ASMPhysicalRegister RegT0 = new ASMPhysicalRegister("t0");
    ASMPhysicalRegister RegT1 = new ASMPhysicalRegister("t1");
    ASMPhysicalRegister RegT2 = new ASMPhysicalRegister("t2");
    ASMPhysicalRegister RegSp = new ASMPhysicalRegister("sp");
    LinkedList<ASMInstruction> workList;

    public RegisterAllocator(ASMModule module) {
        this.module = module;
    }

    public void work() {
        for (ASMFunction function : module.functions) {
            totalStack = function.totalStackSize;
            virtualRegBegin = function.exceedingParamBytes + function.allocaBytes;
            for (ASMBlock block : function.blocks)
                visitBlock(block);
        }
    }

    public void visitBlock(ASMBlock block) {
        workList = new LinkedList<ASMInstruction>();
        for (ASMInstruction inst : block.instructions) {
            if (inst.rs1 != null && !(inst.rs1 instanceof ASMPhysicalRegister)) {
                allocateSrc(RegT1, inst.rs1);
                inst.rs1 = RegT1;
            }
            if (inst.rs2 != null && !(inst.rs2 instanceof ASMPhysicalRegister)) {
                allocateSrc(RegT0, inst.rs2);
                inst.rs2 = RegT0;
            }
            workList.add(inst);
            if (inst.rd != null && !(inst.rd instanceof ASMPhysicalRegister)) {
                allocaDest(RegT0, inst.rd);
                inst.rd = RegT0;
            }
        }
        block.instructions = workList;
    }

    void allocateSrc(ASMPhysicalRegister reg, ASMRegister src) {
        if (src instanceof ASMVirtualRegister) {
            int offset = ((ASMVirtualRegister) src).id != -1
                    ? virtualRegBegin + ((ASMVirtualRegister) src).id * 4
                    : totalStack + ((ASMVirtualRegister) src).parameterId * 4;
            if (offset < 1 << 11)
                workList.add(new ASMLoad(((ASMVirtualRegister) src).size, reg, new ASMImmediate(offset), RegSp));
            else {
                workList.add(new ASMLi(RegT2, new ASMVirtualImmediate(offset)));
                workList.add(new ASMRType("add", RegT2, RegT2, RegSp));
                workList.add(new ASMLoad(((ASMVirtualRegister) src).size, reg, RegT2));
            }
        } else if (src instanceof ASMVirtualImmediate) {
            workList.add(new ASMLi(reg, (ASMVirtualImmediate) src));
        } else if (src instanceof ASMGlobal) {
            workList.add(new ASMLui(reg, new ASMRelocation(ASMRelocation.Type.hi, ((ASMGlobal) src).label)));
            workList.add(new ASMIType("addi", reg, reg, new ASMRelocation(ASMRelocation.Type.lo, ((ASMGlobal) src).label)));
        }
    }

    void allocaDest(ASMPhysicalRegister reg, ASMRegister dest) {
        if (dest instanceof ASMVirtualRegister) {
            int offset = ((ASMVirtualRegister) dest).id != -1
                    ? virtualRegBegin + ((ASMVirtualRegister) dest).id * 4
                    : totalStack + ((ASMVirtualRegister) dest).parameterId * 4;
            if (offset < 1 << 11)
                workList.add(new ASMStore(((ASMVirtualRegister) dest).size, reg, new ASMImmediate(offset), RegSp));
            else {
                workList.add(new ASMLi(RegT2, new ASMVirtualImmediate(offset)));
                workList.add(new ASMRType("add", RegT2, RegT2, RegSp));
                workList.add(new ASMStore(((ASMVirtualRegister) dest).size, reg, RegT2));
            }
        }
    }
}