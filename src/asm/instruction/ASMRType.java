package asm.instruction;

import asm.operand.*;

public class ASMRType extends ASMInstruction {
    String opt;

    public ASMRType(String opt, ASMRegister rd, ASMRegister rs1, ASMRegister rs2) {
        this.rd = rd;
        this.rs1 = rs1;
        this.rs2 = rs2;
        switch (opt) {
            case "sdiv" -> this.opt = "div";
            case "srem" -> this.opt = "rem";
            case "shl" -> this.opt = "sll";
            case "ashr" -> this.opt = "sra";
            default -> this.opt = opt;
        }
    }

    @Override
    public String toString() {
        return "\t" + opt + " " + rd.toString() + ", " + rs1.toString() + ", " + rs2.toString();
    }
}