package ir.entity;
import asm.operand.ASMRegister;
import ir.type.*;
public abstract class Entity {
    public IRType type;
    public ASMRegister asmRegister;

    public Entity(IRType type) {
        this.type = type;
    }

    public abstract String toString();

    public abstract String toStringWithType();
}