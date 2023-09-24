package asm.instruction;

import asm.operand.*;

public class ASMIType extends ASMInstruction {
    String opt;

    public ASMIType(String opt, ASMRegister rd, ASMRegister rs1) {
        this.opt = opt;
        this.rd = rd;
        this.rs1 = rs1;
    }
    public ASMIType(String opt, ASMRegister rd, ASMRegister rs1, ASMImmediate imm) {
        this.opt = opt;
        this.rd = rd;
        this.rs1 = rs1;
        this.imm = imm;
    }

    @Override
    public String toString() {
        if (imm == null) return "\t" + opt + " " + rd + ", " + rs1;
        else return "\t" + opt + " " + rd + ", " + rs1 + ", " + imm;
    }
}