package ir.instruction;

import ir.*;
import ir.entity.*;
import ir.type.*;

public class IRLoad extends IRInstruction {
    public IRType type;
    public IRRegister dest;
    public Entity entity;

    public IRLoad(IRBasicBlock parentBlock, IRRegister dest, Entity entity) {
        super(parentBlock);
        this.dest = dest;
        this.entity = entity;
        this.type = dest.type;
    }

    @Override
    public String toString() {
        return dest + " = load " + type + ", " + entity.toStringWithType();
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void getUseDef() {
        def.clear();
        use.clear();
        def.add(dest);
        if (entity instanceof IRRegister && !(entity instanceof IRGlobalVar)) {
            use.add((IRRegister) entity);
        }
    }
}