package asm.operand;

import ir.entity.*;

public class ASMImmediate extends ASMOperand {
    int value;

    public ASMImmediate(int value) {
        this.value = value;
    }

    public ASMImmediate(IRConst value) {
        if (value instanceof IRBoolConst) {
            this.value = ((IRBoolConst) value).value ? 1 : 0;
        } else if (value instanceof IRIntConst) {
            this.value = ((IRIntConst) value).value;
        } else {
            this.value = 0;
        }
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}