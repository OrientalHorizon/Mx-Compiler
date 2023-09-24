package ir.entity;

import ir.type.IRInt;

public class IRIntConst extends IRConst {
    public int value;

    public IRIntConst(int value) {
        super(new IRInt(32));
        this.value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public String toStringWithType() {
        return "i32 " + this.toString();
    }

    @Override
    public boolean isZero() {
        return value == 0;
    }

    @Override
    public boolean equals(IRConst other) {
        if (!(other instanceof IRIntConst)) return false;
        return value == ((IRIntConst)other).value;
    }
}