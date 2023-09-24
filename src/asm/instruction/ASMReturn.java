package asm.instruction;

import asm.operand.*;

public class ASMReturn extends ASMInstruction {
    // Format: ret
    // 作用：返回

    public ASMReturn() {
    }

    @Override
    public String toString() {
        return "\tret";
    }
}