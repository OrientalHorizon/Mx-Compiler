package ir.entity;

import ir.type.IRVoid;

public class IRVoidConst extends IRConst {
    public IRVoidConst() {
        super(new IRVoid());
    }

    @Override
    public String toString() {
        return "void";
    }

    @Override
    public String toStringWithType() {
        return "void";
    }

    @Override
    public boolean isZero() {
        return false;
    }
    @Override
    public boolean equals(IRConst other) {
        return other instanceof IRVoidConst;
    }
}