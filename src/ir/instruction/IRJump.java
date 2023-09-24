package ir.instruction;

import ir.*;
import ir.type.*;
import ir.entity.*;

public class IRJump extends IRInstruction {
    public IRBasicBlock dest;

    public IRJump(IRBasicBlock parentBlock, IRBasicBlock dest) {
        super(parentBlock);
        this.dest = dest;
    }

    @Override
    public String toString() {
        return "br label %" + dest.label;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void getUseDef() {
        use.clear();
        def.clear();
    }
}