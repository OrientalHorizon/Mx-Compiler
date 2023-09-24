package asm.instruction;

import asm.*;

public class ASMJump extends ASMInstruction {
    ASMBlock destBlock;

    public ASMJump(ASMBlock destBlock) {
        this.destBlock = destBlock;
    }

    @Override
    public String toString() {
        return "\tj " + destBlock.label;
    }
}