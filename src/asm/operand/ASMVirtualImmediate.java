package asm.operand;

import ir.entity.*;

public class ASMVirtualImmediate extends ASMRegister {
    // 用于较大的立即数（不方便在一般指令中存下来，因为 RISC-V 32 中很多指令对立即数大小有要求）
    // 相当于开一个虚拟寄存器把它存下来，用的时候读
    public int value;

    public ASMVirtualImmediate(int value) {
        this.value = value;
    }

    public ASMVirtualImmediate(IRConst value) {
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
        return String.valueOf(value);
    }
}