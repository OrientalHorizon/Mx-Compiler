package ir.instruction;

import ir.*;
import ir.entity.*;
import ir.type.*;

public class IRRet extends IRInstruction {
    public Entity value;

    public IRRet(IRBasicBlock parentBlock, Entity value) {
        super(parentBlock);
        this.value = value;
    }

    @Override
    public String toString() {
        if (value == null) {
            return "ret void";
        } else {
            return "ret " + value.toStringWithType();
        }
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void getUseDef() {
        // 没有 def
        def.clear();
        use.clear();
        if (value instanceof IRRegister && !(value instanceof IRGlobalVar)) {
            use.add((IRRegister) value);
        }
    }
}