package ir.instruction;

import ir.*;
import ir.type.*;
import ir.entity.*;

import java.util.ArrayList;

public class IRPhi extends IRInstruction {
    public IRRegister dest;
    public ArrayList<Entity> values = new ArrayList<>();
    public ArrayList<IRBasicBlock> blocks = new ArrayList<>();

    public IRPhi(IRBasicBlock parentBlock, IRRegister dest) {
        super(parentBlock);
        this.dest = dest;
    }

    public void add(Entity value, IRBasicBlock block) {
        if (value != null) {
            values.add(value);
        }
        else {
            // TODO
        }
        blocks.add(block);
    }

    @Override
    public String toString() {
        String ret = dest + " = phi " + dest.type + " ";
        for (int i = 0; i < values.size(); ++i) {
            if (i != 0) ret += ", ";
            ret += "[ " + values.get(i).toString() + ", %" + blocks.get(i).label + " ]";
        }
        return ret;
    }

    @Override
    public void accept(IRVisitor visitor) {
        // visitor.visit(this);
    }

    @Override
    public void getUseDef() {
        use.clear();
        def.clear();
        def.add(dest);
        for (var value : values) {
            if (value instanceof IRRegister && !(value instanceof IRGlobalVar)) {
                use.add((IRRegister) value);
            }
        }
    }
}