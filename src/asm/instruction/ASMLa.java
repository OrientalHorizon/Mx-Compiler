package asm.instruction;

import asm.*;
import asm.operand.*;

public class ASMLa extends ASMInstruction {
    // 用于加载全局变量
    public String label;

    public ASMLa(ASMRegister rd, String label) {
        this.rd = rd;
        this.label = label;
    }

    @Override
    public String toString() {
        return "\tla " + rd.toString() + ", " + label;
    }
}