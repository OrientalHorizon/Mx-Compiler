package ir.entity;

import ir.type.*;

public class IRNullConst extends IRConst {
    public IRNullConst() {
        super(new IRNull());
    }
    public IRNullConst(IRType type) {
        super(type);
        assert type.isNullAssignable();
    }

    @Override
    public String toString() {
        return "null";
    }

    @Override
    public String toStringWithType() {
        IRNull tmpNull = new IRNull();
        if (this.type.equals(tmpNull)) {
            return "null";
        } else {
            return type.toString() + " null";
        }
    }

    @Override
    public boolean isZero() {
        return true;
    }
    @Override
    public boolean equals(IRConst other) {
        return other instanceof IRNullConst;
    }
}