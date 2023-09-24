package ir.instruction;

import ir.*;
import ir.type.*;
import ir.entity.*;

public class IRBranch extends IRInstruction {
    public Entity condition;
    public IRBasicBlock thenBlock;
    public IRBasicBlock elseBlock;

    public IRBranch(IRBasicBlock parentBlock, Entity condition, IRBasicBlock thenBlock, IRBasicBlock elseBlock) {
        super(parentBlock);
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }

    @Override
    public String toString() {
        return "br " + condition.toStringWithType() + ", label %" + thenBlock.label + ", label %" + elseBlock.label;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void getUseDef() {
        use.clear();
        def.clear();
        if (condition instanceof IRRegister && !(condition instanceof IRGlobalVar)) {
            use.add((IRRegister)condition);
        }
    }
}