package ir.entity;

import ir.type.*;

public class IRRegister extends Entity {
    public final String name;
    public int id;
    public boolean isAlloca = false;
    public static int registerCnt = 0;

    public IRRegister(IRType type, String name) {
        super(type);
        this.name = name;
        this.id = registerCnt++;
    }

    @Override
    public String toString() {
        // TODO: Check?
        return "%" + name + "_" + id;
    }
    @Override
    public String toStringWithType() {
        return type.toString() + " " + this.toString();
    }
}