package util;
import ast.*;
import ir.entity.*;
import ir.*;
import util.Type;
import util.error.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
// 注意 scope 的性质：跟祖辈的scope有重名无所谓，但是自己内部不能有重复
public class Scope {
    public enum ScopeType {
        Global, Class, Function, Loop, Block
    }

    public ScopeType scopeType;

    public Type returnType; // 仅限 ScopeType: Function
    public ClassDefNode classNode; // 仅限 ScopeType: Class
    // 新建 scope 的时候往下传
    // 记得往下传！！！！！！！！！！

    public Scope parentScope;

    public boolean inLoop = false; // 新建 scope 的时候往下传
    public boolean returned = false; // 往上传
    public int nearestLoop = 0; // While - 1, For - 2
    public int loopDepth = 0; // 开 Loop Scope 的时候记得增加

    public LinkedHashMap<String, VariableNode> variableMembers = new LinkedHashMap<>();
    public LinkedHashMap<String, FuncDefNode> functionMembers = new LinkedHashMap<>();
    public LinkedHashMap<String, ClassDefNode> classMembers = new LinkedHashMap<>();

    public LinkedHashMap<String, IRRegister> irVariableMembers = new LinkedHashMap<>();
    public LinkedHashMap<String, IRFunction> irFunctions = new LinkedHashMap<>();
    public WhileStmtNode whileStmtNode = null;
    public ForStmtNode forStmtNode = null;
    public Scope(ScopeType scopeType, Scope parentScope) {
        this.scopeType = scopeType;
        if (scopeType == ScopeType.Loop) {
            this.inLoop = true;
        }
        this.parentScope = parentScope;
        if (scopeType == ScopeType.Global) {
            addBuiltinFunctions();
            if (parentScope != null) {
                throw new InternalError("Global scope should not have parent scope");
            }
        }
        if (parentScope != null) {
            if (parentScope.classNode != null) {
                this.classNode = parentScope.classNode;
            }
            if (parentScope.returnType != null) {
                this.returnType = parentScope.returnType;
            }
            this.inLoop = parentScope.inLoop;
            this.loopDepth = parentScope.loopDepth;
            this.nearestLoop = parentScope.nearestLoop;
            this.whileStmtNode = parentScope.whileStmtNode;
            this.forStmtNode = parentScope.forStmtNode;
        }
    }
    public static Scope getWhileScope(Scope parentScope, WhileStmtNode whileStmtNode) {
        Scope ret = new Scope(ScopeType.Loop, parentScope);
        ret.loopDepth = parentScope.loopDepth + 1;
        ret.nearestLoop = 1; // While
        ret.whileStmtNode = whileStmtNode;
        return ret;
    }
    public static Scope getForScope(Scope parentScope, ForStmtNode forStmtNode) {
        Scope ret = new Scope(ScopeType.Loop, parentScope);
        ret.loopDepth = parentScope.loopDepth + 1;
        ret.nearestLoop = 2; // For
        ret.forStmtNode = forStmtNode;
        return ret;
    }

    public void addVariable(Position pos, String name, VariableNode var) {
        if (variableMembers.containsKey(name)) {
            throw new SemanticError(pos, "Variable redefinition: " + name);
        }
        variableMembers.put(name, var);
    }
    public void irAddVariable(String name, IRRegister reg) {
        irVariableMembers.put(name, reg);
    }

    public boolean checkVariable(String name) {
        return variableMembers.containsKey(name);
    }
    public boolean checkVariableRecursively(String name) {
        if (variableMembers.containsKey(name)) {
            return true;
        } else if (parentScope != null) {
            return parentScope.checkVariableRecursively(name);
        } else {
            return false;
        }
    }

    public Type getType(String name) {
        if (variableMembers.containsKey(name)) {
            return variableMembers.get(name).type;
        } else {
            return null;
        }
    }
    public Type getTypeRecursively(String name) {
        if (variableMembers.containsKey(name)) {
            return variableMembers.get(name).type;
        } else if (parentScope != null) {
            return parentScope.getTypeRecursively(name);
        } else {
            return null;
        }
    }

    public void addFunction(Position pos, String name, FuncDefNode func) {
        if (functionMembers.containsKey(name) || classMembers.containsKey(name)) {
            throw new SemanticError(pos, "Function redefinition: " + name);
        }
        functionMembers.put(name, func);
    }
    public boolean checkFunction(String name) {
        return functionMembers.containsKey(name);
    }
    public FuncDefNode getFunction(String name) {
        return functionMembers.get(name);
    }

    public void addClass(Position pos, String name, ClassDefNode cls) {
        if (functionMembers.containsKey(name) || classMembers.containsKey(name)) {
            throw new SemanticError(pos, "Class redefinition: " + name);
        }
        classMembers.put(name, cls);
    }
    public boolean checkClass(String name) {
        return classMembers.containsKey(name);
    }
    public ClassDefNode getClass(String name) {
        return classMembers.get(name);
    }


    public void addBuiltinFunctions() {
        BuiltinFunctions builtin = new BuiltinFunctions();
        functionMembers.put("print", builtin.getPrint());
        functionMembers.put("println", builtin.getPrintln());
        functionMembers.put("printInt", builtin.getPrintInt());
        functionMembers.put("printlnInt", builtin.getPrintlnInt());
        functionMembers.put("getString", builtin.getGetString());
        functionMembers.put("getInt", builtin.getGetInt());
        functionMembers.put("toString", builtin.getToString());

        // TODO: 把 int string bool 等基本类型打包成 class 加进去
        // finish
        classMembers.put("int", builtin.getIntClass());
        classMembers.put("string", builtin.getStringClass());
        classMembers.put("bool", builtin.getBoolClass());
    }

    public boolean inClass() {
        return (this.classNode != null);
    }
    public boolean inFunction() {
        if (this.scopeType == ScopeType.Global) {
            return false;
        }
        else if (this.scopeType == ScopeType.Function) {
            return true;
        }
        else {
            return parentScope.inFunction();
        }
    }

    public IRRegister getIRVariable(String name, boolean recursively) {
        if (irVariableMembers.containsKey(name)) {
            return irVariableMembers.get(name);
        } else if (recursively && parentScope != null) {
            return parentScope.getIRVariable(name, recursively);
        } else {
            return null;
        }
    }
}