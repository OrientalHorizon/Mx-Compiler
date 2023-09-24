package asm.instruction;

import asm.operand.*;

public class ASMLoad extends ASMInstruction {
    // Format: lw rd, imm(rs1)
    // 作用：将 reg[rs1] + imm 地址的内存中的值存入 reg[rd] 中

    int size;

    public ASMLoad(int size, ASMRegister rd, ASMImmediate imm, ASMRegister rs1) {
        // 你妈传进来的 size 不带存的？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？
        // 似乎是 Copilot 的问题，傻逼 copilot
        this.size = size;
        this.rd = rd;
        this.imm = imm;
        this.rs1 = rs1;
    }
    public ASMLoad(int size, ASMRegister rd, ASMRegister rs1) {
        this.size = size;
        this.rd = rd;
        this.imm = new ASMImmediate(0);
        this.rs1 = rs1;
    }

    @Override
    public String toString() {
        String ret = "\t" + ((size == 1) ? "lb " : "lw ");
        return ret + rd.toString() + ", " + imm.toString() + "(" + rs1.toString() + ")";
    }
}