package ir;

// TODO
import ir.type.*;
import ir.instruction.*;
import ir.entity.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class IRProgram {
    public LinkedList<IRFunction> functions = new LinkedList<>();
    public ArrayList<IRGlobalVar> globalVars = new ArrayList<>();
    public ArrayList<IRStruct> structs = new ArrayList<>();

    public LinkedHashMap<String, IRStringConst> stringConsts = new LinkedHashMap<>();
    public ArrayList<IRStringConst> stringArray = new ArrayList<>();
    public IRVoid irVoid = new IRVoid();

    public IRFunction initFunction = new IRFunction("__mx_global_var_init", irVoid);
    public IRFunction mainFunction;
    public IRBasicBlock initBlock = new IRBasicBlock(initFunction, "__mx_global_var_init_block");
    public ArrayList<IRFuncDecl> builtinFunction = new ArrayList<>();

    public IRProgram() {
        initFunction.addBlock(initBlock);
        initFunction.exitBlock = new IRBasicBlock(initFunction, "_return");
        // TODO
        initBlock.terminal = new IRJump(initBlock, initFunction.exitBlock);
        IRVoidConst irVoidConst = new IRVoidConst();
        initFunction.exitBlock.terminal = new IRRet(initFunction.exitBlock, irVoidConst);
    }

    public String toString() {
        String ret = "";
        for (var struct: structs) {
            ret += struct.toString() + " = type {";
            for (int i = 0; i < struct.memberNum; ++i) {
                if (i != 0) ret += ", ";
                ret += struct.typeArray.get(i).toString();
            }
            ret += "}\n\n";
        }
        for (var str: stringConsts.values()) {
            ret += str.toString() + " = private unnamed_addr constant [" + str.value.length() + " x i8] c\"" + str.escape() + "\"\n";
        }
        ret += "\n";
        for (var func: builtinFunction) {
            ret += func.toString() + "\n";
        }
        ret += "\n";
        for (var gvar: globalVars) {
            if (gvar.init == null) {
                gvar.init = new IRNullConst();
            }
            ret += gvar.toString() + " = dso_local global " + ((IRPointer) gvar.type).Subscript().toString() + " " + gvar.init.toString() + "\n";
        }
        ret += "\n";
        for (var func: functions) {
            ret += func.toString() + "\n\n";
        }
        return ret;
    }
}