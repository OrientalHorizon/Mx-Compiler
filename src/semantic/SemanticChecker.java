package semantic;
import ast.*;

import util.*;
import util.error.*;

public class SemanticChecker implements ASTVisitor {
    private final Type intType = new Type("int");
    private final Type boolType = new Type("bool");
    private final Type stringType = new Type("string");
    private final Type voidType = new Type("void");
    private final Type nullType = new Type("null");
    private final Type thisType = new Type("this");


    private final Scope globalScope;
    private Scope currentScope;

    public SemanticChecker(Scope _globalScope) {
        this.currentScope = this.globalScope = _globalScope;
    }

    @Override
    public void visit(ProgramNode it) {
        // 记得查main函数
        FuncDefNode mainFunc = globalScope.getFunction("main");
        if (mainFunc == null || mainFunc.parameters.paramDef.size() != 0 || !mainFunc.returnType.equals(intType)) {
            throw new SemanticError(it.pos, "Wrong main function.");
        }
        for (var def: it.defNodes) {
            def.accept(this);
        }
    }

    @Override
    public void visit(ClassDefNode it) {
        currentScope = new Scope(Scope.ScopeType.Class, currentScope);
        currentScope.classNode = it;
        for (var varDef: it.varDefs) {
            varDef.accept(this);
        }
        if (it.constructor != null) {
            if (it.constructor.className.equals(it.className)) {
                it.constructor.accept(this);
            }
            else {
                throw new SemanticError(it.constructor.pos, "Wrong constructor.");
            }
        }
        for (var funcDef: it.funcDefs) {
            funcDef.accept(this);
        }
        currentScope = currentScope.parentScope;
    }

    @Override
    public void visit(ConstructorNode it) {
        currentScope = new Scope(Scope.ScopeType.Function, currentScope);
        currentScope.returnType = voidType;
        for (var stmt: it.suite.stmts) {
            stmt.accept(this);
        }
        currentScope = currentScope.parentScope;
    }

    @Override
    public void visit(FuncDefNode it) {
        // check return type, ignoring dimension
//        System.out.println("funcDefNode: " + it.funcName.name);
//        System.out.println("returnType: " + it.returnType.basicTypeName);
        if (!it.returnType.basicTypeName.equals("int")
        &&  !it.returnType.basicTypeName.equals("bool")
        &&  !it.returnType.basicTypeName.equals("string")
        &&  !it.returnType.equals(voidType)
        &&  !globalScope.checkClass(it.returnType.basicTypeName)) {
            throw new SemanticError(it.pos, "Wrong return type.");
        }
        // .out.println(it.funcName.name);
        currentScope = new Scope(Scope.ScopeType.Function, currentScope);
        currentScope.returnType = it.returnType;
        if (it.parameters != null) it.parameters.accept(this);
        for (var stmt: it.body.stmts) {
            if (stmt != null) {
                stmt.accept(this);
            }
            // System.out.println(currentScope.returned);
        }

        // TODO: check return node finished
        if (!it.returnType.equals(voidType) && !it.funcName.name.equals("main")) {
            // 必须有返回值
            if (!currentScope.returned) {
                throw new SemanticError(it.pos, "Missing return value.");
            }
        }
        currentScope = currentScope.parentScope;
    }

    @Override
    public void visit(ParamListNode it) {
        // TODO finished
        for (var param: it.paramDef) {
            param.accept(this);
        }
    }

    @Override
    public void visit(VarDefNode it) {
        for (var variables: it.variables) {
            variables.accept(this);
            // TODO: 判重复变量 哦后面有
        }
    }

    @Override
    public void visit(VariableNode it) {
        // System.out.println("fuck you");
        // TODO
        // System.out.println(it.type.basicTypeName);
        if (!it.type.basicTypeName.equals("int")
        &&  !it.type.basicTypeName.equals("bool")
        &&  !it.type.basicTypeName.equals("string")
        &&  !globalScope.checkClass(it.type.basicTypeName)) {
            throw new SemanticError(it.pos, "it.type: Wrong variable type.");
        }

        if (it.init != null) {
            it.init.accept(this);
            if (!it.init.type.equals(nullType)) {
                if (!it.init.type.equals(it.type)) {
                    throw new SemanticError(it.pos, "it.init: Wrong variable type.");
                }
            }
        }
        if (currentScope.checkVariable(it.name.name)) {
            throw new SemanticError(it.pos, "Duplicate variable name.");
        }

        currentScope.addVariable(it.pos, it.name.name, it);
//        System.out.println("fuck you");
//        System.out.println(currentScope.variableMembers.size());
    }

    // statement
    @Override
    public void visit(SuiteStmtNode it) {
        currentScope = new Scope(Scope.ScopeType.Block, currentScope);
        if (it.stmts != null) {
            for (var stmt: it.stmts) {
                if (stmt != null) {
                    stmt.accept(this);
                }
            }
        }
        currentScope = currentScope.parentScope;
    }

    @Override
    public void visit(IfStmtNode it) {
        it.condition.accept(this);
        if (!it.condition.type.equals(boolType)) {
            throw new SemanticError(it.pos, "Condition expression in if statement is not bool type.");
        }
        currentScope = new Scope(Scope.ScopeType.Block, currentScope);
        if (it.thenStmt instanceof SuiteStmtNode) {
            for (var stmt: ((SuiteStmtNode) it.thenStmt).stmts) {
                stmt.accept(this);
            }
        }
        else {
            it.thenStmt.accept(this);
        }
        currentScope = currentScope.parentScope;
        if (it.elseStmt != null) {
            currentScope = new Scope(Scope.ScopeType.Block, currentScope);
            if (it.elseStmt instanceof SuiteStmtNode) {
                for (var stmt: ((SuiteStmtNode) it.elseStmt).stmts) {
                    stmt.accept(this);
                }
            }
            else {
                it.elseStmt.accept(this);
            }
            currentScope = currentScope.parentScope;
        }
    }

    @Override
    public void visit(WhileStmtNode it) {
        // 检查是不是 bool 类型
        it.conditionExpr.accept(this);
        if (!it.conditionExpr.type.equals(boolType)) {
            throw new SemanticError(it.pos, "Condition expression in while loop is not bool type.");
        }
        currentScope = new Scope(Scope.ScopeType.Loop, currentScope);
        currentScope.inLoop = true;
        if (it.body instanceof SuiteStmtNode) {
            for (var stmt: ((SuiteStmtNode) it.body).stmts) {
                stmt.accept(this);
            }
        }
        else {
            it.body.accept(this);
        }
        currentScope = currentScope.parentScope;
    }

    @Override
    public void visit(ForStmtNode it) {
        // TODO: 如果有条件表达式，检查是不是 bool 类型
        // 注意 null 问题
        currentScope = new Scope(Scope.ScopeType.Loop, currentScope);
        currentScope.inLoop = true;
        if (it.initStmt != null) {
            it.initStmt.accept(this);
        }
        if (it.conditionExpr != null) {
            it.conditionExpr.accept(this);
            if (!it.conditionExpr.type.equals(boolType)) {
                throw new SemanticError(it.pos, "Condition expression in for loop is not bool type.");
            }
        }
        if (it.incExpr != null) {
            it.incExpr.accept(this);
        }
        if (it.body instanceof SuiteStmtNode) {
            for (var stmt: ((SuiteStmtNode) it.body).stmts) {
                stmt.accept(this);
            }
        }
        else {
            it.body.accept(this);
        }
        currentScope = currentScope.parentScope;
    }

    @Override
    public void visit(ReturnStmtNode it) {
        // 确认它在一个函数中，并且向上递归标记函数的返回类型
        for (var cur = currentScope; cur != null; cur = cur.parentScope) {
            // System.out.println("cur scope type: " + cur.scopeType);
            if (cur.scopeType == Scope.ScopeType.Function) {
                // 是个函数
                // em.out.println("return type: " + cur.returnType.basicTypeName + cur.returnType.dimension);
                if (cur.returnType.equals(voidType)) {
                    // 返回 void，那么 it.expr 必须为 null
                    if (it.expr != null) {
                        throw new SemanticError(it.pos, "Returning a value for void function");
                    }
                }
                else {
                    // 必须有返回值
                    if (it.expr == null) {
                        throw new SemanticError(it.pos, "No return value for non-void function");
                    }
                    it.expr.accept(this);
                    if (!it.expr.type.equals(cur.returnType)) {
                        if (it.expr.type.equals(nullType) && (cur.returnType.isReference)) {
                            // 豁免
                        }
                        else {
                            throw new SemanticError(it.pos, "Return type mismatches");
                        }
                    }
                }
                cur.returned = true;
                return;
            }
        }
        throw new SemanticError(it.pos, "Return statement not in function");
    }

    @Override
    public void visit(BreakStmtNode it) {
        if (!currentScope.inLoop) {
            throw new SemanticError(it.pos, "Break statement not in loop.");
        }
    }

    @Override
    public void visit(ContinueStmtNode it) {
        if (!currentScope.inLoop) {
            throw new SemanticError(it.pos, "Continue statement not in loop.");
        }
    }

    @Override
    public void visit(ExprStmtNode it) {
        it.expr.accept(this);
    }

    @Override
    public void visit(EmptyStmtNode it) {
        // Do nothing
    }

    // expression

    @Override
    public void visit(ArgumentListNode it) {
        // TODO
        for (var expr: it.arguments) {
            expr.accept(this);
        }
    }

    @Override
    public void visit(SuffixExprNode it) {
        // TODO: 检查类型必须是 int
        it.expr.accept(this);
        if (it.expr.type == null) {
            throw new SemanticError(it.pos, "Wrong type: null");
        }
        if (!it.expr.isLeftValue) {
            throw new SemanticError(it.pos, "Wrong type: left value expected.");
        }
        if (!it.expr.type.equals(intType)) {
            throw new SemanticError(it.pos, "Wrong type: int expected.");
        }
        it.type = intType;
    }

    @Override
    public void visit(MemberExprNode it) {
        // 检查它是否能够取点（不是很会，问问别人），后面的identifier对不对得上号
        // 返回值类型赋给自己
        it.expr.accept(this);
        if (it.expr.type == null) {
            throw new SemanticError(it.pos, "Wrong type: null");
        }
        if (it.expr.type.dimension > 0) {
            throw new SemanticError(it.pos, "Wrong type: array expected.");
        }
        if (!globalScope.checkClass(it.expr.type.basicTypeName)) {
            throw new SemanticError(it.pos, "Wrong type: class expected.");
        }
        ClassDefNode curClass = globalScope.getClass(it.expr.type.basicTypeName);
        if (!curClass.varMap.containsKey(it.member.name)) {
            throw new SemanticError(it.pos, "Wrong type: member not found.");
        }
        it.type = curClass.varMap.get(it.member.name).type;
        // TODO size 修锅?
    }

    @Override
    public void visit(MethodExprNode it) {
        // 同样不是善茬，检查这个方法是否存在并且 argument list 是否匹配
        // 返回值类型赋给自己
        it.expr.accept(this);
        if (it.expr.type == null) {
            throw new SemanticError(it.pos, "Wrong type: null");
        }
        if (it.args != null) {
            it.args.accept(this);
        }

        // 数组的 size 函数在这里实现
        if (it.expr.type.dimension > 0 && it.method.name.equals("size")) {
            if (it.args.arguments.size() > 0) {
                // 参数列表不对
                throw new SemanticError(it.pos, "Size method of an array: no argument expected.");
            }
            // 可以
            it.type = intType;
            return;
        }

        if (!globalScope.checkClass(it.expr.type.basicTypeName)) {
            throw new SemanticError(it.pos, "Wrong type: class expected.");
        }
        ClassDefNode curClass = globalScope.getClass(it.expr.type.basicTypeName);
        // System.out.println("curClass name: " + curClass.className.name);
        if (!curClass.funcMap.containsKey(it.method.name)) {
            throw new SemanticError(it.pos, "Wrong type: function not found.");
        }
        FuncDefNode realFunc = curClass.funcMap.get(it.method.name);
        if (it.args == null) {
            if (realFunc.parameters != null && realFunc.parameters.paramDef.size() > 0) {
                throw new SemanticError(it.pos, "Wrong type: argument list mismatch. 1");
            }
        }
        else {
            if (realFunc.parameters == null) {
                throw new SemanticError(it.pos, "Wrong type: argument list mismatch. 2");
            }
            if (realFunc.parameters.paramDef.size() != it.args.arguments.size()) {
                throw new SemanticError(it.pos, "Wrong type: argument list mismatch. 3");
            }


            var realList = realFunc.parameters.paramDef;
            var list = it.args.arguments;

            for (int i = 0; i < realList.size(); ++i) {
                if (!realList.get(i).type.equals(list.get(i).type)) {
                    if ((realList.get(i).type.isReference) && list.get(i).type.equals(nullType)) {
                        // class 和 null 的豁免
                    }
                    else {
//                        System.out.println("realList.get(i).type: " + realList.get(i).type.basicTypeName);
//                        System.out.println("list.get(i).type: " + list.get(i).type);
                        throw new SemanticError(it.pos, "Wrong type: argument list mismatch. 4");
                    }
                }
            }
        }
        it.type = realFunc.returnType;

    }

    @Override
    public void visit(FuncCallExprNode it) {
        // argument list 匹配，函数存在，返回值类型赋给自己
        it.func.accept(this);
        if (it.args != null) {
            it.args.accept(this);
        }
        FuncDefNode realFunc;
        if (currentScope.inClass() && currentScope.classNode.funcMap.containsKey(it.func.name)) {
            // 是个类的成员函数
            realFunc = currentScope.classNode.funcMap.get(it.func.name);
        }
        else if (globalScope.checkFunction(it.func.name)) {
            // 是个全局函数
            realFunc = globalScope.getFunction(it.func.name);
        }
        else {
            throw new SemanticError(it.pos, "Wrong type: function not found.");
        }
        if (it.args == null) {
            if (realFunc.parameters != null && realFunc.parameters.paramDef.size() > 0) {
                throw new SemanticError(it.pos, "Wrong type: argument list mismatch. 5");
            }
        }
        else {
            if (realFunc.parameters == null) {
                throw new SemanticError(it.pos, "Wrong type: argument list mismatch. 6");
            }
            if (realFunc.parameters.paramDef.size() != it.args.arguments.size()) {
                throw new SemanticError(it.pos, "Wrong type: argument list mismatch. 7");
            }

            var realList = realFunc.parameters.paramDef;
            var list = it.args.arguments;

            for (int i = 0; i < realList.size(); ++i) {
                if (!realList.get(i).type.equals(list.get(i).type)) {
                    if ((realList.get(i).type.isReference) && list.get(i).type.equals(nullType)) {
                        // class 和 null 的豁免
                    }
                    else {
                        throw new SemanticError(it.pos, "Wrong type: argument list mismatch. 8");
                    }
                }
            }
        }
        it.type = realFunc.returnType;
    }

    @Override
    public void visit(SubscriptExprNode it) {
        // dimension > 0，dimension -= 1，能匹配得上（有这个数组）
        it.array.accept(this);
        it.subscript.accept(this);
        if (it.subscript.type == null) {
            throw new SemanticError(it.pos, "Wrong type: null");
        }
        if (!it.subscript.type.equals(intType)) {
            throw new SemanticError(it.pos, "Wrong type: int expected.");
        }
        if (it.array.type.dimension <= 0) {
            throw new SemanticError(it.pos, "Wrong type: array expected.");
        }
        it.type = new Type(it.array.type.basicTypeName, it.array.type.dimension - 1);
    }

    @Override
    public void visit(PrefixExprNode it) {
        // 类型必须是 int
        it.expr.accept(this);
        if (it.expr.type == null) {
            throw new SemanticError(it.pos, "Wrong type: null");
        }
        if (!it.expr.isLeftValue) {
            throw new SemanticError(it.pos, "Wrong type: left value expected.");
        }
        if (!it.expr.type.equals(intType)) {
            throw new SemanticError(it.pos, "Wrong type: int expected.");
        }
        it.type = intType;
    }

    @Override
    public void visit(UnaryExprNode it) {
        // 检查类型即可，int / bool
        it.expr.accept(this);
        if (it.expr.type == null) {
            throw new SemanticError(it.pos, "Wrong type: null");
        }

        switch (it.opt) {
            case LOGIC_NOT -> {
                if (!it.expr.type.equals(boolType)) {
                    throw new SemanticError(it.pos, "Wrong type: bool expected.");
                }
                it.type = boolType;
            }
            case NEG, POS, BITWISE_NOT -> {
                if (!it.expr.type.equals(intType)) {
                    throw new SemanticError(it.pos, "Wrong type: int expected.");
                }
                it.type = intType;
            }
        }
    }

    @Override
    public void visit(NewExprNode it) {
        // TODO: identifier 与函数名匹配，dimension 匹配，errorNewExpr，基类存在，
        // 如果有参数，要与构造函数匹配
        if (it.isInvalid) {
            throw new SemanticError(it.pos, "Wrong new expression: invalid.");
        }
        if (it.exprNodes != null) {
            for (var expr: it.exprNodes) {
                // 有expr的一定是前几个维度
                expr.accept(this);
                if (!expr.type.equals(intType)) {
                    throw new SemanticError(it.pos, "Wrong index type");
                }
            }
        }
        if (!it.basicType.equals(intType) && !it.basicType.equals(boolType) && !it.basicType.equals(stringType)
        &&  !globalScope.checkClass(it.basicType.basicTypeName)) {
            throw new SemanticError(it.pos, "No such type");
        }
        it.type = new Type(it.basicType.basicTypeName, it.dimension);
    }

    @Override
    public void visit(BinaryExprNode it) {
        // TODO: 检查类型是否匹配
        it.lhs.accept(this);
        it.rhs.accept(this);
        Type lType = it.lhs.type, rType = it.rhs.type;
        if (lType == null || rType == null || lType.equals(voidType) || rType.equals(voidType)) {
            throw new SemanticError(it.pos, "Type of lhs or rhs is void.");
        }
        var opt = it.opt;

        switch (opt) {
            case MUL, DIV, MOD, SUB, SHL, SHR, BIT_AND, BIT_XOR, BIT_OR -> {
                if (!lType.equals(intType) || !rType.equals(intType)) {
                    throw new SemanticError(it.pos, "Type of lhs or rhs is not int.");
                }
                it.type = intType;
            }
            case ADD -> {
                if (!lType.equals(rType)) {
                    throw new SemanticError(it.pos, "ADD: Type of lhs and rhs is not the same.");
                }
                if (!(lType.equals(intType)) && !(lType.equals(stringType))) {
                    throw new SemanticError(it.pos, "ADD: Type of lhs and rhs is not int or string.");
                }
                it.type = lType;
            }
            case LT, GT, LE, GE -> {
                if (!lType.equals(rType)) {
                    throw new SemanticError(it.pos, "LT: Type of lhs and rhs is not the same.");
                }
                if (!(lType.equals(intType)) && !(lType.equals(stringType))) {
                    throw new SemanticError(it.pos, "LT: Type of lhs and rhs is not int or string.");
                }
                it.type = boolType;
            }
            case EQ, NE -> {
                // TODO
                if (!lType.equals(rType)) {
                    // 判断 reference 和 null
                    if ((lType.isReference) && rType.equals(nullType)) {
                        // no problem
                        it.type = boolType;
                        return;
                    }
                    if ((rType.isReference) && lType.equals(nullType)) {
                        // no problem
                        it.type = boolType;
                        return;
                    }
                    throw new SemanticError(it.pos, "EQ: Type of lhs and rhs is not the same.");
                }
                it.type = boolType;
            }
            case LOG_AND, LOG_OR -> {
                if (!lType.equals(boolType) || !rType.equals(boolType)) {
                    throw new SemanticError(it.pos, "Type of lhs or rhs is not bool.");
                }
                it.type = boolType;
            }
            default -> { assert false; }
        }
    }

    @Override
    public void visit(TernaryExprNode it) {
        // 注意 isReference 的维护
        it.cond.accept(this);
        if (it.cond.type == null || it.cond.type.basicTypeName == null || !it.cond.type.equals(boolType)) {
            throw new SemanticError(it.cond.pos, "Condition of ternary expression is not bool type.");
        }

        it.thenExpr.accept(this);
        it.elseExpr.accept(this);
        
        if (it.thenExpr.type.equals(nullType) && it.elseExpr.type.equals(nullType)) {
            it.type = nullType;
            return;
        }
        if (it.thenExpr.type.equals(nullType)) {
            if (it.elseExpr.type.isReference == false) {
                throw new SemanticError(it.pos, "Type of then expression is null, but type of else expression is not reference.");
            }
            it.type = it.elseExpr.type;
            return;
        }
        else if (it.elseExpr.type.equals(nullType)) {
            if (it.thenExpr.type.isReference == false) {
                throw new SemanticError(it.pos, "Type of else expression is null, but type of then expression is not reference.");
            }
            it.type = it.thenExpr.type;
            return;
        }
        if (!it.thenExpr.type.equals(it.elseExpr.type)) {
            throw new SemanticError(it.pos, "Type of then expression is not equal to type of else expression.");
        }
        it.type = it.thenExpr.type;
    }

    @Override
    public void visit(AssignExprNode it) {
        // TODO: 左边能不能做左值；变量类型是否匹配（e.g. 左边是int类型，那么右边也必须是int类型）
        it.lhs.accept(this);
        it.rhs.accept(this);
        Type lType = it.lhs.type, rType = it.rhs.type;
        if (lType == null || rType == null || lType.equals(voidType) || rType.equals(voidType)) {
            throw new SemanticError(it.pos, "Type of lhs or rhs is void.");
        }

        if (!lType.equals(rType)) {
            // 判断 reference 和 null
            if ((lType.isReference) && rType.equals(nullType)) {
                // no problem
                it.type = lType;
                return;
            }
            throw new SemanticError(it.pos, "Type of lhs and rhs is not the same.");
        }
        if (!it.lhs.isLeftValue) {
            throw new SemanticError(it.pos, "Lhs of assignment expression is not left value.");
        }
        it.type = rType;
    }

    @Override
    public void visit(ParenExprNode it) {
        // TODO: Finished?
        it.expr.accept(this);
    }

    @Override
    public void visit(ThisExprNode it) {
        if (!currentScope.inClass()) {
            throw new SemanticError(it.pos, "This expression is not in class.");
        }
        it.type = new Type(currentScope.classNode.className.name);
    }

    @Override
    public void visit(IntConstNode it) {
        it.type = intType;
    }

    @Override
    public void visit(StringConstNode it) {
        // 检查 match？
        // em.out.println("in string const node" + it.value);
        it.type = stringType;
    }

    @Override
    public void visit(BoolConstNode it) {
        it.type = boolType;
    }

    @Override
    public void visit(NullConstNode it) {
        it.type = nullType;
    }

    @Override
    public void visit(IdentifierNode it) {
        // TODO finished
        // 有得写了
        for (Scope cur = currentScope; cur != null; cur = cur.parentScope) {
            if (cur.checkVariable(it.name)) {
                it.isLeftValue = true;
                it.type = cur.getType(it.name);
                return;
            }
            else if (cur.classNode != null && cur.classNode.funcMap.containsKey(it.name)) {
                // 方法函数
                FuncDefNode func = cur.classNode.funcMap.get(it.name);
                it.type = func.returnType;
                it.funcDef = func;
                it.isMemberFunction = true;
                return;
            }
            else if (cur.scopeType == Scope.ScopeType.Global) {
                // 两种可能：函数和类
                if (cur.checkClass(it.name)) {
                    it.type = new Type(it.name);
                    return;
                }
                else if (cur.checkFunction(it.name)) {
                    FuncDefNode func = cur.getFunction(it.name);
                    it.type = func.returnType;
                    it.funcDef = func;
                    return;
                }
            }
        }
        throw new SemanticError(it.pos, "Identifier " + it.name + " is not defined.");
    }
}
