package ir.entity;

import ir.type.*;

public abstract class IRConst extends Entity {
    public IRConst(IRType type) {
        super(type);
    }

    public abstract boolean isZero();

    public abstract boolean equals(IRConst other);
}