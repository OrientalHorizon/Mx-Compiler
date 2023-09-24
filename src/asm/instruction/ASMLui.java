package asm.instruction;

import asm.operand.*;

public class ASMLui extends ASMInstruction {
    // Format: lui rd, imm
    // 作用：将立即数imm的高20位存入寄存器rd中，低12位为0

    public ASMLui(ASMRegister dest, ASMImmediate imm) {
        this.rd = dest;
        this.imm = imm;
    }

    @Override
    public String toString() {
        return "\tlui " + rd.toString() + ", " + imm.toString();
    }
}