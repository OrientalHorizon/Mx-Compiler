package semantic;
import ast.*;
import util.*;
import util.error.*;
public class SymbolCollector implements ASTVisitor {
    public Scope globalScope;

    public SymbolCollector(Scope _globalScope) {
        this.globalScope = _globalScope;
    }

    @Override
    public void visit(ProgramNode it) {
        for (var def: it.defNodes) {
            if (def instanceof ClassDefNode || def instanceof FuncDefNode) {
                def.accept(this);
            }
        }

        // 记得查main函数
    }

    @Override
    public void visit(ClassDefNode it) {
        if (globalScope.checkClass(it.className.name)) {
            throw new SemanticError(it.pos, "Class " + it.className.name + " has been defined.");
        }
        if (globalScope.checkFunction(it.className.name)) {
            throw new SemanticError(it.pos, "Class " + it.className.name + " has been defined as a function.");
        }
        globalScope.addClass(it.pos, it.className.name, it);

        for (var func: it.funcDefs) {
            if (it.funcMap.containsKey(func.funcName.name)) {
                throw new SemanticError(func.pos, "Function " + func.funcName.name + " has been defined in class " + it.className.name + ".");
            }
            it.funcMap.put(func.funcName.name, func);
        }
        for (var varDef: it.varDefs) {
            for (var varNode: varDef.variables) {
                if (it.varMap.containsKey(varNode.name.name)) {
                    throw new SemanticError(varNode.pos, "Variable " + varNode.name + " has been defined in class " + it.className.name + ".");
                }
                it.varMap.put(varNode.name.name, varNode);
            }
        }
    }

    @Override
    public void visit(FuncDefNode it) {
        if (globalScope.checkFunction(it.funcName.name)) {
            throw new SemanticError(it.pos, "Function " + it.funcName.name + " has been defined.");
        }
        if (globalScope.checkClass(it.funcName.name)) {
            throw new SemanticError(it.pos, "Function " + it.funcName.name + " has been defined as a class.");
        }
        globalScope.addFunction(it.pos, it.funcName.name, it);
    }

    @Override
    public void visit(VarDefNode it) {}

    @Override
    public void visit(ConstructorNode it) {}

    // statement
    @Override
    public void visit(SuiteStmtNode it) {}
    @Override
    public void visit(IfStmtNode it) {}
    @Override
    public void visit(WhileStmtNode it) {}
    @Override
    public void visit(ForStmtNode it) {}
    @Override
    public void visit(ReturnStmtNode it) {}
    @Override
    public void visit(BreakStmtNode it) {}
    @Override
    public void visit(ContinueStmtNode it) {}
    @Override
    public void visit(ExprStmtNode it) {}
    @Override
    public void visit(EmptyStmtNode it) {}

    // expression
    @Override
    public void visit(SuffixExprNode it) {}
    @Override
    public void visit(MemberExprNode it) {}
    @Override
    public void visit(MethodExprNode it) {}
    @Override
    public void visit(FuncCallExprNode it) {}
    @Override
    public void visit(SubscriptExprNode it) {}
    @Override
    public void visit(PrefixExprNode it) {}
    @Override
    public void visit(UnaryExprNode it) {}
    @Override
    public void visit(NewExprNode it) {}
    @Override
    public void visit(BinaryExprNode it) {}
    @Override
    public void visit(TernaryExprNode it) {}
    @Override
    public void visit(AssignExprNode it) {}

    // primary
    @Override
    public void visit(ParenExprNode it) {}
    @Override
    public void visit(ThisExprNode it) {}

    // literal
    @Override
    public void visit(IntConstNode it) {}
    @Override
    public void visit(StringConstNode it) {}
    @Override
    public void visit(BoolConstNode it) {}

    @Override
    public void visit(NullConstNode it) {}

    @Override
    public void visit(IdentifierNode it) {}

    // variable
    @Override
    public void visit(VariableNode it) {}

    @Override
    public void visit(ArgumentListNode it) {}

    @Override
    public void visit(ParamListNode it) {}
}
