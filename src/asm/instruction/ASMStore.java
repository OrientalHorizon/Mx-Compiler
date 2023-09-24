package asm.instruction;

import asm.operand.*;

public class ASMStore extends ASMInstruction {
    // Format: sw rs2, imm(rs1)
    // 作用：将 reg[rs2] 的值存入 reg[rs1] + imm 地址的内存中

    int size;

    public ASMStore(int size, ASMRegister rs2, ASMImmediate imm, ASMRegister rs1) {
        this.size = size;
        this.rs2 = rs2;
        this.imm = imm;
        this.rs1 = rs1;
    }
    public ASMStore(int size, ASMRegister rs2, ASMRegister rs1) {
        this.size = size;
        this.rs2 = rs2;
        this.imm = new ASMImmediate(0);
        this.rs1 = rs1;
    }

    @Override
    public String toString() {
        String ret = "\t" + ((size == 1) ? "sb " : "sw ");
        return ret + rs2.toString() + ", " + imm.toString() + "(" + rs1.toString() + ")";
    }
}