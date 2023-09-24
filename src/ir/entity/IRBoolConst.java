package ir.entity;
import ir.type.*;

public class IRBoolConst extends IRConst {
    public boolean value;

    public IRBoolConst(boolean value) {
        super(new IRBool());
        this.value = value;
    }

    @Override
    public String toString() {
        return value ? "true" : "false";
    }

    @Override
    public String toStringWithType() {
        return "i1 " + this.toString();
    }

    @Override
    public boolean isZero() {
        return !value;
    }

    @Override
    public boolean equals(IRConst other) {
        if (!(other instanceof IRBoolConst)) return false;
        return value == ((IRBoolConst)other).value;
    }
}