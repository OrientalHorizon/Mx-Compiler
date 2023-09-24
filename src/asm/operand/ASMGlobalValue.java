package asm.operand;

import ir.entity.*;
import util.error.CodegenError;

public class ASMGlobalValue extends ASMGlobal {
    public int value;
    public String strConstName = null;

    public ASMGlobalValue(String label, int bytes, int value) {
        super(label, bytes);
        this.bytes = bytes;
        this.value = value;
    }
    public ASMGlobalValue(IRGlobalVar var) {
        super(var.varName, 4);
        if (var.init instanceof IRBoolConst) {
            bytes = 1; // TODO: 4 or 1?
            value = ((IRBoolConst) var.init).value ? 1 : 0;
        }
        else if (var.init instanceof IRIntConst) {
            bytes = 4;
            value = ((IRIntConst) var.init).value;
        }
        else if (var.init instanceof IRNullConst) {
            bytes = 4;
            value = 0;
        }
        else if (var.init instanceof IRStringConst) { // 放全局会输出乱码，，，，
            bytes = 4;
            strConstName = "." + var.init.toString().substring(1);
        }
        else {
            throw new CodegenError(null, "ASMGlobalValue: " + var.init);
        }
    }

    @Override
    public String toString() {
        String ret = label + ":\n";
        if (strConstName != null)
            ret += "  .word " + strConstName + "\n";
        else
            ret += (bytes == 4 ? "  .word " : "  .byte ") + value + "\n";
        return ret;
    }
}