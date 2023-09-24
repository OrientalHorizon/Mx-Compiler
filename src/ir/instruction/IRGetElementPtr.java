// TODO: 注意语法，这个指令比较复杂

package ir.instruction;

import ir.*;
import ir.type.*;
import ir.entity.*;

import java.util.ArrayList;

public class IRGetElementPtr extends IRInstruction {
    public IRType type;
    public IRRegister dest;
    public Entity ptr;
    public ArrayList<Entity> idxs = new ArrayList<>();

    public IRGetElementPtr(IRBasicBlock parentBlock, IRType type, IRRegister dest, IRRegister ptr, Entity subscript) {
        super(parentBlock);
        this.type = ((IRPointer) type).Subscript();
        this.dest = dest;
        this.ptr = ptr;
        idxs.add(subscript);
    }

    @Override
    public String toString() {
        String ret = "";
        ret += dest.toString() + " = getelementptr " + type + ", " + ptr.toStringWithType();
        for (int i = 0; i < idxs.size(); ++i) {
            ret += ", " + idxs.get(i).toStringWithType();
        }
        return ret;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void getUseDef() {
        use.clear();
        def.clear();
        if (ptr instanceof IRRegister && !(ptr instanceof IRGlobalVar))
            use.add((IRRegister) ptr);
        for (var idx : idxs) {
            if (idx instanceof IRRegister && !(idx instanceof IRGlobalVar)) {
                use.add((IRRegister) idx);
            }
        }
        if (dest != null && !(dest instanceof IRGlobalVar))
            def.add(dest);
    }
}