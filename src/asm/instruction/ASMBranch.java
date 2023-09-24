package asm.instruction;

import asm.ASMBlock;
import asm.operand.ASMRegister;

public class ASMBranch extends ASMInstruction {
    public enum Type {
        beqz, bnez
    }
    public Type type;
    ASMBlock destBlock;

    public ASMBranch(Type type, ASMRegister rs1, ASMBlock destBlock) {
        this.type = type;
        this.rs1 = rs1;
        this.destBlock = destBlock;
    }

    @Override
    public String toString() {
        return "\t" + type.toString() + " " + rs1.toString() + ", " + destBlock.label;
    }
}
