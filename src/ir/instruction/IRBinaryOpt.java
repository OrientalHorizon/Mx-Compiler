package ir.instruction;

import ir.IRBasicBlock;

import ir.IRVisitor;
import ir.entity.*;
import ir.type.*;

public class IRBinaryOpt extends IRInstruction {
    public enum OpType {
        eq, ne, ugt, uge, ult, ule, sgt, sge, slt, sle
    }

    public OpType opType;
    public Entity lhs, rhs;
    public IRType type;
    public IRRegister reg;

    public IRBinaryOpt(IRBasicBlock parent, IRRegister reg, OpType opType, IRType type, Entity lhs, Entity rhs) {
        super(parent);
        this.reg = reg;
        this.opType = opType;
        this.type = type;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public String toString() {
        return reg + " = icmp " + opType + " " + type + " " + lhs + ", " + rhs;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void getUseDef() {
        use.clear();
        def.clear();
        if (lhs instanceof IRRegister && !(lhs instanceof IRGlobalVar)) {
            use.add((IRRegister)lhs);
        }
        if (rhs instanceof IRRegister && !(rhs instanceof IRGlobalVar)) {
            use.add((IRRegister)rhs);
        }
        def.add(reg);
    }
}