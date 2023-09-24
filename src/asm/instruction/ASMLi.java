package asm.instruction;

import asm.operand.*;

public class ASMLi extends ASMInstruction {
    // Li = load immediate number
    // Format: li rd, imm
    // 作用：将立即数 imm 存入 reg[rd] 中
    // 立即数大小可以认为不受限制

    public ASMVirtualImmediate imm;

    public ASMLi(ASMRegister rd, ASMVirtualImmediate imm) {
        this.rd = rd;
        this.imm = imm;
    }

    @Override
    public String toString() {
        return "\tli " + rd.toString() + ", " + imm.toString();
    }
}