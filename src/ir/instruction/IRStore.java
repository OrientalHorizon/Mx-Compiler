package ir.instruction;

import ir.entity.*;
import ir.*;

public class IRStore extends IRInstruction {
    public Entity val;
    public IRRegister addr; // 存到这里

    public IRStore(IRBasicBlock parentBlock, Entity val, IRRegister addr) {
        super(parentBlock);
        this.val = val;
        this.addr = addr;
    }

    @Override
    public String toString() {
        if (val == null) {
            return "shit store null, " + addr.toStringWithType();
        }
        if (this.val instanceof IRNullConst) {
            return "store ptr null, " + addr.toStringWithType();
        }
        return "store " + val.toStringWithType() + ", " + addr.toStringWithType();
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void getUseDef() {
        // rs1 和 rs2，都是 use
        def.clear();
        use.clear();
        if (!(addr instanceof IRGlobalVar)) {
            use.add(addr);
        }

        if (val instanceof IRRegister && !(val instanceof IRGlobalVar)) {
            use.add((IRRegister) val);
        }
    }
}