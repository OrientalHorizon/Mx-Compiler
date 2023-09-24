package ir.entity;
import ir.type.*;
public class IRGlobalVar extends IRRegister {
    public Entity init;

    public String varName;

    public IRGlobalVar(IRType type, String name) {
        super(new IRPointer(type, 1), "global");
        this.varName = name;
    }

    @Override
    public String toString() {
        return "@" + varName;
    }

    @Override
    public String toStringWithType() {
        return type.toString() + " " + this;
    }

}