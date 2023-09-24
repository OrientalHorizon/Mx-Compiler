package ir.instruction;

import ir.*;
import ir.type.*;

import java.util.ArrayList;

public class IRFuncDecl extends IRInstruction {
    String name;
    IRType returnType;
    ArrayList<IRType> args = new ArrayList<>();

    public IRFuncDecl(IRBasicBlock parentBlock, String name, IRType returnType, IRType... argsType) {
        super(parentBlock);
        this.name = name;
        this.returnType = returnType;
        for (var argType : argsType) {
            args.add(argType);
        }
    }

    @Override
    public String toString() {
        String ret = "";
        ret += "declare ";
        if (returnType == null || returnType.equals(new IRVoid())) {
            ret += "void ";
        } else {
            ret += returnType.toString() + " ";
        }
        ret += "@" + name + "(";

        for (int i = 0; i < args.size(); ++i) {
            if (i != 0) ret += ", ";
            ret += args.get(i).toString();
        }
        ret += ")";
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
    }
}