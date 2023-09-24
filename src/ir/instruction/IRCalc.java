package ir.instruction;

import ir.*;
import ir.entity.*;
import ir.type.*;

public class IRCalc extends IRInstruction {
    public enum OpType {
        add, sub, mul, sdiv, srem, shl, ashr, and, or, xor
    }

    public OpType opType;
    public Entity lhs, rhs;
    public IRType type;
    public IRRegister reg;

    public IRCalc(IRBasicBlock parent, IRRegister reg, OpType opType, IRType type, Entity lhs, Entity rhs) {
        super(parent);
        this.reg = reg;
        this.opType = opType;
        this.type = type;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public String toString() {
        return reg + " = " + opType + " " + lhs.toStringWithType() + ", " + rhs.toString();
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void getUseDef() {
        use.clear();
        def.clear();
        if (lhs instanceof IRRegister) {
            use.add((IRRegister) lhs);
        }
        if (rhs instanceof IRRegister) {
            use.add((IRRegister) rhs);
        }
        def.add(reg);
    }
}