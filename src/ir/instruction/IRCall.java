package ir.instruction;

import ir.*;
import ir.type.*;
import ir.entity.*;

import java.util.ArrayList;

public class IRCall extends IRInstruction {
    public String name;
    public IRRegister addr;
    public IRType returnType;

    public ArrayList<Entity> args = new ArrayList<>();

    public IRCall(IRBasicBlock parentBlock, String name, IRRegister addr, IRType returnType) {
        super(parentBlock);
        this.name = name;
        this.addr = addr;
        this.returnType = returnType;
    }

    @Override
    public String toString() {
        String ret = "";
        if (returnType == null || returnType.equals(new IRVoid())) {
            ret = "call void ";
        } else {
            ret = addr + " = call " + returnType.toString() + " ";
        }
        ret += "@" + name + "(";

        for (int i = 0; i < args.size(); ++i) {
            if (i != 0) ret += ", ";
            if (args.get(i) == null) {
                ret += "null: fuck you!";
                continue;
            }
            ret += args.get(i).toStringWithType();
        }
        ret += ")";
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
        if (returnType != null && !returnType.equals(new IRVoid()))
            def.add(addr);
        for (var arg: args) {
            if (arg instanceof IRRegister && !(arg instanceof IRGlobalVar)) {
                use.add((IRRegister) arg);
            }
        }
    }
}