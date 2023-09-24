package ir.instruction;

import ir.*;
import ir.entity.*;
import ir.type.*;

public class IRAlloca extends IRInstruction {
    public IRType type;
    public IRRegister reg;

    public IRAlloca(IRBasicBlock parentBlock, IRType type, IRRegister reg) {
        super(parentBlock);
        this.type = type;
        this.reg = reg;
        reg.isAlloca = true;
    }

    @Override
    public String toString() {
        return reg + " = alloca " + type;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void getUseDef() {
        def.clear();
        use.clear();
        def.add(reg);
    }
}