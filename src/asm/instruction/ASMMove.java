package asm.instruction;

import asm.operand.*;

public class ASMMove extends ASMInstruction {
    // Format: mv rd, rs1
    // 作用：将 reg[rs1] 的值赋给 reg[rd]

    public ASMMove(ASMRegister rd, ASMRegister rs1) {
        this.rd = rd;
        this.rs1 = rs1;
    }

    @Override
    public String toString() {
        return "\tmv " + rd.toString() + ", " + rs1.toString();
    }
}