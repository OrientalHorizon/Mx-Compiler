package ir;

import ast.*;
import util.*;
import ir.instruction.*;
import ir.type.*;
import ir.entity.*;
import util.error.CodegenError;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class IRBuilder implements ASTVisitor {
    // TODO: Rework Scope，变量名、class、loop、loop几层
    private final Scope globalScope;
    private Scope currentScope;
    private IRFunction currentFunction;
    private IRBasicBlock currentBlock;
    private IRStruct currentClass;
    private IRInt irInt32 = new IRInt(32);
    private IRVoid irVoid = new IRVoid();
    private IRBool irBool = new IRBool();
    private IRNull irNull = new IRNull();
    private IRBoolConst irTrue = new IRBoolConst(true);
    private IRBoolConst irFalse = new IRBoolConst(false);
    private IRInt irChar = new IRInt(8);
    private IRPointer irString = new IRPointer(irChar, 1);
    private Type astInt = new Type("int");
    private Type astBool = new Type("bool");
    private Type astVoid = new Type("void");
    private Type astString = new Type("string");

    LinkedHashMap<String, IRStruct> structMap = new LinkedHashMap<>();

    private IRProgram program;

    private IRType transferType(Type type) {
        IRType ret;
        switch (type.basicTypeName) {
            case "int" -> {
                ret = irInt32;
                break;
            }
            case "bool" -> {
                ret = irBool;
                break;
            }
            case "string" -> {
                ret = irString;
                break;
            }
            case "void" -> {
                ret = irVoid;
                break;
            }
            default -> {
                // TODO
                ret = new IRPointer(structMap.get(type.basicTypeName), 1);
            }
        }
        if (type.dimension >= 1) {
            ret = new IRPointer(ret, type.dimension);
        }
        return ret;
    }
    private IRType getPtrType(Type type) {
        IRType irType = transferType(type);
        if (irType instanceof IRPointer) {
            return new IRPointer(((IRPointer) irType).baseType, ((IRPointer) irType).dimension + 1);
        }
        else {
            return new IRPointer(irType, 1);
        }
    }
    private Entity getEntity(ExprNode it) {
        // 解引用，把包起来的值解出来

        if (it.entity != null) {
            return it.entity;
        }
        else {
//            if (it.reg == null) {
//                throw new CodegenError(it.pos, "IRBuilder: getEntity: entity & reg are null");
//            }
            IRRegister tmp = new IRRegister(((IRPointer) it.reg.type).Subscript(), "tmp");
            currentBlock.addInstruction(new IRLoad(currentBlock, tmp, it.reg));
            it.entity = tmp;
            return it.entity;
        }
    }
    private void storeExpr(IRRegister dest, ExprNode src) {
        if (src.entity instanceof IRNullConst) {
            src.entity = new IRNullConst(((IRPointer) dest.type).Subscript());
        }
        currentBlock.addInstruction(new IRStore(currentBlock, src.entity, dest));
    }

    private Entity getDefaultValue(IRType type) {
        if (type instanceof IRBool) {
            return new IRBoolConst(false);
        }
        if (type instanceof IRInt) {
            return new IRIntConst(0);
        }
        return new IRNullConst(type);
    }
    private void getBuiltinFunction() {
        program.builtinFunction.add(
                new IRFuncDecl(program.initBlock, "print", irVoid, irString));
        program.builtinFunction.add(
                new IRFuncDecl(program.initBlock, "println", irVoid, irString));
        program.builtinFunction.add(
                new IRFuncDecl(program.initBlock, "printInt", irVoid, irInt32));
        program.builtinFunction.add(
                new IRFuncDecl(program.initBlock, "printlnInt", irVoid, irInt32));
        program.builtinFunction.add(
                new IRFuncDecl(program.initBlock, "getString", irString));
        program.builtinFunction.add(
                new IRFuncDecl(program.initBlock, "getInt", irInt32));
        program.builtinFunction.add(
                new IRFuncDecl(program.initBlock, "toString", irString, irInt32));

        // String
        program.builtinFunction.add(
                new IRFuncDecl(program.initBlock, "_string_length", irInt32, irString));
        program.builtinFunction.add(
                new IRFuncDecl(program.initBlock, "_string_substring", irString, irString, irInt32, irInt32));
        program.builtinFunction.add(
                new IRFuncDecl(program.initBlock, "_string_parseInt", irInt32, irString));
        program.builtinFunction.add(
                new IRFuncDecl(program.initBlock, "_string_ord", irInt32, irString, irInt32));
        program.builtinFunction.add(
                new IRFuncDecl(program.initBlock, "_string_add", irString, irString, irString));
        program.builtinFunction.add(
                new IRFuncDecl(program.initBlock, "_string_eq", irBool, irString, irString));
        program.builtinFunction.add(
                new IRFuncDecl(program.initBlock, "_string_ne", irBool, irString, irString));
        program.builtinFunction.add(
                new IRFuncDecl(program.initBlock, "_string_lt", irBool, irString, irString));
        program.builtinFunction.add(
                new IRFuncDecl(program.initBlock, "_string_le", irBool, irString, irString));
        program.builtinFunction.add(
                new IRFuncDecl(program.initBlock, "_string_gt", irBool, irString, irString));
        program.builtinFunction.add(
                new IRFuncDecl(program.initBlock, "_string_ge", irBool, irString, irString));
        program.builtinFunction.add(
                new IRFuncDecl(program.initBlock, "_array_size", irInt32, irString));
        program.builtinFunction.add(
                new IRFuncDecl(program.initBlock, "_new_array", irString, irInt32, irInt32));
        program.builtinFunction.add(
                new IRFuncDecl(program.initBlock, "malloc", irString, irInt32));
    }

    public IRBuilder(Scope _globalScope, IRProgram _program) {
        this.currentScope = this.globalScope = _globalScope;
        this.program = _program;
        getBuiltinFunction();
    }

    // program
    @Override
    public void visit(ProgramNode it) {
        // 相当于 collector
        // 大方向：先处理类，再处理变量和类的成员变量，再处理函数，最后写一个 init 函数的底子出来。

        for (var def: it.defNodes) {
            if (def instanceof ClassDefNode) {
                globalScope.classMembers.put(((ClassDefNode) def).className.name, (ClassDefNode) def);
                IRStruct curStruct = new IRStruct(((ClassDefNode) def).className.name, ((ClassDefNode) def).varDefs.size());
                program.structs.add(curStruct);
                structMap.put(((ClassDefNode) def).className.name, curStruct);
                for (var varDef: ((ClassDefNode) def).varDefs) {
                    for (var var: varDef.variables) {
                        curStruct.memberType.put(var.name.name, transferType(var.type));
                        curStruct.memberIndex.put(var.name.name, curStruct.memberIndex.size());
                        curStruct.typeArray.add(transferType(var.type));
                    }
                }
            }
        }

        for (var def: it.defNodes) {
            if (def instanceof VarDefNode) {
                // collect global variables
                for (var var: ((VarDefNode) def).variables) {
                    globalScope.variableMembers.put(var.name.name, var);
                    IRGlobalVar globalVar = new IRGlobalVar(transferType(var.type), var.name.name);
                    globalScope.irVariableMembers.put(var.name.name, globalVar);
                    program.globalVars.add(globalVar);
                }
            }
        }
        for (var def: it.defNodes) {
            if (def instanceof FuncDefNode) {
                // 处理全局函数
                IRFunction curFunc = new IRFunction(((FuncDefNode) def).funcName.name, transferType(((FuncDefNode) def).returnType));
                program.functions.add(curFunc);
                globalScope.functionMembers.put(((FuncDefNode) def).funcName.name, (FuncDefNode) def);
                globalScope.irFunctions.put(((FuncDefNode) def).funcName.name, curFunc);
                if (((FuncDefNode) def).parameters != null && ((FuncDefNode) def).parameters.paramDef.size() != 0) {
                    for (var param: ((FuncDefNode) def).parameters.paramDef) {
                        curFunc.paramList.add(new IRRegister(transferType(param.type), param.name.name));
                    }
                }
            }
            else if (def instanceof ClassDefNode) {
                // 处理方法函数！！！
                currentClass = structMap.get(((ClassDefNode) def).className.name);
                for (var method: ((ClassDefNode) def).funcDefs) {
                    currentScope = new Scope(Scope.ScopeType.Function, currentScope);
                    IRFunction curFunc = new IRFunction(((ClassDefNode) def).className.name + "." + method.funcName.name, transferType(method.returnType));

                    currentScope.functionMembers.put(curFunc.name, method);
                    globalScope.irFunctions.put(curFunc.name, curFunc);

                    // 为了 this 指针，把 class 打包
                    IRPointer classPtr = new IRPointer(currentClass, 1);
                    IRRegister thisType = new IRRegister(classPtr, "this");
                    IRRegister thisAddr = new IRRegister(new IRPointer(classPtr, 1), "this_addr");
                    curFunc.paramList.add(thisType);
                    if (method.parameters != null && method.parameters.paramDef.size() != 0) {
                        for (var param: method.parameters.paramDef) {
                            curFunc.paramList.add(new IRRegister(transferType(param.type), param.name.name));
                        }
                    }
                    curFunc.thisAddr = thisAddr;
                    currentFunction = curFunc;
                    currentBlock = currentFunction.entryBlock;

                    // alloca
                    curFunc.allocas.add(new IRAlloca(currentBlock, new IRPointer(classPtr, 1), thisAddr));
                    // store
                    curFunc.entryBlock.addInstruction(new IRStore(curFunc.entryBlock, thisType, thisAddr));
                    program.functions.add(curFunc);
                    currentScope = currentScope.parentScope;

                    currentFunction = null;
                    currentBlock = null;
                }
                if (((ClassDefNode) def).constructor != null) {
                    String irFunctionName = ((ClassDefNode) def).className.name + "." + ((ClassDefNode) def).className.name;
                    IRFunction curFunc = new IRFunction(irFunctionName, irVoid);
                    currentFunction = curFunc;
                    currentScope = new Scope(Scope.ScopeType.Function, currentScope);
                    // System.out.println(irFunctionName);
                    globalScope.irFunctions.put(irFunctionName, curFunc);

                    currentBlock = currentFunction.entryBlock;
                    IRPointer classPtr = new IRPointer(currentClass, 1);
                    IRRegister thisType = new IRRegister(classPtr, "this");
                    IRRegister thisAddr = new IRRegister(new IRPointer(classPtr, 1), "this_addr");
                    curFunc.paramList.add(thisType);
                    curFunc.thisAddr = thisAddr;
                    currentFunction = curFunc;
                    currentBlock = currentFunction.entryBlock;

                    // alloca
                    curFunc.allocas.add(new IRAlloca(currentBlock, new IRPointer(classPtr, 1), thisAddr));
                    // store
                    curFunc.entryBlock.addInstruction(new IRStore(curFunc.entryBlock, thisType, thisAddr));
                    program.functions.add(curFunc);
                    currentScope = currentScope.parentScope;

                    currentFunction = null;
                    currentBlock = null;
                }
                currentClass = null;
            }
        }

        // 造一个 init function
        program.initFunction = new IRFunction("_global_var_init", irVoid);
        currentFunction = program.initFunction;
        program.functions.add(currentFunction);
        program.initBlock = program.initFunction.entryBlock;
        currentBlock = program.initFunction.entryBlock;
        currentBlock.addTerminal(new IRJump(currentBlock, program.initFunction.exitBlock));
        currentBlock = program.initFunction.exitBlock;
        currentBlock.addTerminal(new IRRet(currentBlock, new IRVoidConst()));
        // TODO: 处理各种 accept 的顺序
        currentBlock = currentFunction.entryBlock;
        // 跑全局变量
        for (var def: it.defNodes) {
            if (def instanceof VarDefNode) {
                for (var var: ((VarDefNode) def).variables) {
                    IRGlobalVar globalVar = (IRGlobalVar) globalScope.irVariableMembers.get(var.name.name);
                    IRType varType = ((IRPointer) globalVar.type).Subscript();
                    globalVar.init = getDefaultValue(varType);

                    if (var.init != null) {
                        var.init.accept(this);

                        if (var.init.entity instanceof IRConst) { // 没办法了  && !(var.init.entity instanceof IRStringConst)
                            // 没必要进 init 函数
                            globalVar.init = var.init.entity;
                        } else {
                            currentBlock.addInstruction(new IRStore(currentBlock, getEntity(var.init), globalVar));
                        }
                    }
                }
            }
        }
        currentBlock.finished = true;
//        currentBlock = currentFunction.exitBlock;
//        currentBlock.addInstruction(new IRRet(currentBlock, new IRVoidConst()));
//        program.functions.add(currentFunction);
        currentFunction = null;
        currentBlock = null;

        // 访问类定义
        for (var def: it.defNodes) {
            if (def instanceof ClassDefNode) {
                def.accept(this);
            }
        }

        // 访问函数定义
        for (var def: it.defNodes) {
            if (def instanceof FuncDefNode) {
                def.accept(this);
            }
        }

        // init 函数
//        if (program.initBlock.instructions.size() != 0) {
//            IRBasicBlock entry = program.mainFunction.entryBlock;
//            entry.instructions.addFirst(new IRCall(entry, "_global_var_init", null, irVoid));
//        }
    }

    // def
    @Override
    public void visit(ClassDefNode it) {
        currentScope = new Scope(Scope.ScopeType.Class, currentScope);
        currentScope.classNode = it;
        currentClass = structMap.get(it.className.name);

        // TODO: it.varDefs.forEach(varDef -> varDef.accept(this)); 有没有必要？

        // 构造函数
        if (it.constructor != null) {
            it.constructor.accept(this);
            currentClass.hasConstructor = true;
        }

        it.funcDefs.forEach(funcDef -> funcDef.accept(this));
        currentScope = currentScope.parentScope;
        currentClass = null;
    }
    @Override
    public void visit(FuncDefNode it) {
        // 记得方法函数的参数列表要后移一位
        if (currentClass != null) {
            currentFunction = globalScope.irFunctions.get(currentScope.classNode.className.name + "." + it.funcName.name);
        }
        else {
            // System.out.println(it.funcName.name);
            currentFunction = globalScope.irFunctions.get(it.funcName.name);
        }
        currentScope = new Scope(Scope.ScopeType.Function, currentScope);
        currentBlock = currentFunction.entryBlock;

        // 是不是方法函数？
        if (currentClass != null) {
            for (int i = 0; i < it.parameters.paramDef.size(); ++i) {
                // 处理参数 init
                IRRegister param = currentFunction.paramList.get(i + 1);
                IRRegister addr = new IRRegister(new IRPointer(param.type, 1), it.parameters.paramDef.get(i).name.name); // 一定要写 param.type？
                // System.out.println(param.type.toString());
                IRAlloca alloca = new IRAlloca(currentBlock, ((IRPointer) addr.type).Subscript(), addr);
                currentFunction.allocas.add(alloca);
                currentBlock.addInstruction(new IRStore(currentBlock, param, addr));
                currentScope.irVariableMembers.put(param.name, addr);
            }
        }
        else {
            for (int i = 0; i < it.parameters.paramDef.size(); ++i) {
                // 处理参数 init
                IRRegister param = currentFunction.paramList.get(i);
                IRRegister addr = new IRRegister(new IRPointer(param.type, 1), it.parameters.paramDef.get(i).name.name);
                IRAlloca alloca = new IRAlloca(currentBlock, ((IRPointer) addr.type).Subscript(), addr);
                currentFunction.allocas.add(alloca);
                currentBlock.addInstruction(new IRStore(currentBlock, param, addr));
                currentScope.irVariableMembers.put(param.name, addr);
            }
        }

        // return
        if (currentFunction.returnType == null || currentFunction.returnType.equals(irVoid)) {
            currentFunction.exitBlock.addTerminal(new IRRet(currentFunction.exitBlock, new IRVoidConst()));
        }
        else {
            currentFunction.allocas.add(new IRAlloca(currentBlock, transferType(it.returnType), currentFunction.dest));
            IRRegister ret = new IRRegister(transferType(it.returnType), "ret");
            currentFunction.exitBlock.addInstruction(new IRLoad(currentFunction.exitBlock, ret, currentFunction.dest));
            currentFunction.exitBlock.addTerminal(new IRRet(currentFunction.exitBlock, ret));
        }
        for (var stmt: it.body.stmts) {
            stmt.accept(this);
        }
        if (!currentBlock.finished) {
            currentBlock.addTerminal(new IRJump(currentBlock, currentFunction.exitBlock));
        }

        // main
        if (it.funcName.name.equals("main")) {
            program.mainFunction = currentFunction;
            currentFunction.entryBlock.instructions.addFirst(new IRCall(currentFunction.entryBlock, "_global_var_init", null, irVoid));
            currentFunction.entryBlock.instructions.addFirst(new IRStore(currentFunction.entryBlock, new IRIntConst(0), currentFunction.dest));
        }

        currentScope = currentScope.parentScope;
        currentFunction = null;
        currentBlock = null;
    }
    @Override
    public void visit(VarDefNode it) {
        for (int i = 0; i < it.variables.size(); ++i) {
            it.variables.get(i).accept(this);
        }
    }

    @Override
    public void visit(VariableNode it) {
        IRType varType = transferType(it.type);

        // scope 炸了，谁说一定要是 globalScope
        // 是个全局变量！！
        /*
            for (var var: ((VarDefNode) def).variables) {
                    globalScope.variableMembers.put(var.name.name, var);
                    IRGlobalVar globalVar = new IRGlobalVar(transferType(var.type), "@" + var.name.name);
                    globalScope.irVariableMembers.put(var.name.name, globalVar);
                    program.globalVars.add(globalVar);
                }


             */
            IRRegister addr = new IRRegister(getPtrType(it.type), it.name.name);
            currentScope.irVariableMembers.put(it.name.name, addr);
            IRAlloca alloca = new IRAlloca(currentBlock, transferType(it.type), addr);
            currentFunction.allocas.add(alloca);
            if (it.init != null) {
                it.init.accept(this);
                currentBlock.addInstruction(new IRStore(currentBlock, getEntity(it.init), addr));
            }
    }

    @Override
    public void visit(ConstructorNode it) {
        // 注意所有 irFunction 都存在 globalScope 里面，否则容易找不到
        String irFunctionName = currentClass.name + "." + currentClass.name;
        IRFunction curFunc = globalScope.irFunctions.get(irFunctionName);
        assert(curFunc != null);
        currentFunction = curFunc;
        currentScope = new Scope(Scope.ScopeType.Function, currentScope);
        currentBlock = currentFunction.entryBlock;

        currentFunction.exitBlock.addTerminal(new IRRet(currentFunction.exitBlock, new IRVoidConst()));

        for (var stmt: it.suite.stmts) {
            stmt.accept(this);
        }
        if (!currentBlock.finished || currentBlock.terminal == null) {
            currentBlock.addTerminal(new IRJump(currentBlock, currentFunction.exitBlock));
        }
    }

    @Override
    public void visit(ParamListNode it) {

    }

    // statement
    @Override
    public void visit(SuiteStmtNode it) {
        currentScope = new Scope(Scope.ScopeType.Block, currentScope);
        it.stmts.forEach(stmt -> stmt.accept(this));
        currentScope = currentScope.parentScope;
    }
    @Override
    public void visit(IfStmtNode it) {
        it.condition.accept(this);
        IRBasicBlock thenBlock = new IRBasicBlock(currentFunction, "if_then");
        IRBasicBlock endBlock = new IRBasicBlock(currentFunction, "if_end");
        endBlock.terminal = currentBlock.terminal;
        IRBasicBlock elseBlock = new IRBasicBlock(currentFunction, "if_else");

        // 别掉了！！！！！！！！！！！！！！！！1
        thenBlock.addTerminal(new IRJump(thenBlock, endBlock));
        elseBlock.addTerminal(new IRJump(elseBlock, endBlock));
        Entity condition = getEntity(it.condition);

        if (it.elseStmt != null) {
            // 有 else
            currentBlock.terminal = new IRBranch(currentBlock, condition, thenBlock, elseBlock);
        }
        else {
            // 没有 else
            currentBlock.terminal = new IRBranch(currentBlock, condition, thenBlock, endBlock);
        }

        // currentBlock.addInstruction(new IRBranch(currentBlock, condition, thenBlock, endBlock));
        // currentFunction.addBlock(currentBlock); 这nm？
        currentBlock.finished = true;

        currentBlock = thenBlock;
        currentFunction.addBlock(thenBlock);
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
        currentBlock.finished = true;

        if (it.elseStmt != null) {
            currentBlock = elseBlock;
            currentFunction.addBlock(elseBlock);
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
            currentBlock.finished = true;
        }
        currentFunction.addBlock(endBlock);
        currentBlock = endBlock;
    }
    @Override
    public void visit(WhileStmtNode it) {
        it.condBlock = new IRBasicBlock(currentFunction, "while_cond");
        it.bodyBlock = new IRBasicBlock(currentFunction, "while_body");
        it.endBlock = new IRBasicBlock(currentFunction, "while_end");
        it.endBlock.terminal = currentBlock.terminal;

        currentBlock.terminal = new IRJump(currentBlock, it.condBlock);
        currentBlock.finished = true;

        currentFunction.addBlock(it.condBlock);
        currentBlock = it.condBlock;
        // TODO: 注意 terminal 顺序
        it.conditionExpr.accept(this);
        currentBlock.addTerminal(new IRBranch(currentBlock, getEntity(it.conditionExpr), it.bodyBlock, it.endBlock));
        currentBlock.finished = true;

        currentFunction.addBlock(it.bodyBlock);
        currentBlock = it.bodyBlock;
        currentScope = Scope.getWhileScope(currentScope, it);
        if (it.body instanceof SuiteStmtNode) {
            for (var stmt: ((SuiteStmtNode) it.body).stmts) {
                stmt.accept(this);
            }
        }
        else {
            it.body.accept(this);
        }
        currentScope = currentScope.parentScope;
        currentBlock.addTerminal(new IRJump(currentBlock, it.condBlock));
        currentBlock.finished = true;

        currentFunction.addBlock(it.endBlock);
        currentBlock = it.endBlock;
    }
    @Override
    public void visit(ForStmtNode it) {
        currentScope = Scope.getForScope(currentScope, it);
        if (it.initStmt != null) {
            it.initStmt.accept(this);
        }
        it.condBlock = new IRBasicBlock(currentFunction, "for_cond");
        it.bodyBlock = new IRBasicBlock(currentFunction, "for_body");
        it.stepBlock = new IRBasicBlock(currentFunction, "for_step");
        it.endBlock = new IRBasicBlock(currentFunction, "for_end");
        it.endBlock.addTerminal(currentBlock.terminal);
        currentBlock.terminal = new IRJump(currentBlock, it.condBlock);
        currentBlock.finished = true;

        currentFunction.addBlock(it.condBlock);
        currentBlock = it.condBlock;
        if (it.conditionExpr != null) {
            // 要先加 terminal 再 accept，不然 visit 的时候还没有 terminal，会出错
            it.conditionExpr.accept(this);
            currentBlock.addTerminal(new IRBranch(it.condBlock, getEntity(it.conditionExpr), it.bodyBlock, it.endBlock));
        }
        else {
            it.condBlock.addTerminal(new IRJump(it.condBlock, it.bodyBlock));
        }
        currentBlock.finished = true;

        currentScope = new Scope(Scope.ScopeType.Block, currentScope);
        currentFunction.addBlock(it.bodyBlock);
        currentBlock = it.bodyBlock;
        if (it.body instanceof SuiteStmtNode) {
            for (var stmt: ((SuiteStmtNode) it.body).stmts) {
                stmt.accept(this);
            }
        }
        else {
            it.body.accept(this);
        }
        currentBlock.addTerminal(new IRJump(it.bodyBlock, it.stepBlock)); // 这里不能是 it.bodyBlock.addTerminal，因为可能 currentBlock 就不再是 bodyBlock 了
        currentBlock.finished = true;

        currentScope = currentScope.parentScope;
        currentFunction.addBlock(it.stepBlock);
        currentBlock = it.stepBlock;
        if (it.incExpr != null) {
            it.incExpr.accept(this);
        }
        currentBlock.addTerminal(new IRJump(currentBlock, it.condBlock));
        currentBlock.finished = true;
        currentScope = currentScope.parentScope;

        currentFunction.addBlock(it.endBlock);
        currentBlock = it.endBlock;
    }
    @Override
    public void visit(ReturnStmtNode it) {
        if (it.expr != null) {
            it.expr.accept(this);
            it.expr.entity = getEntity(it.expr);
            storeExpr(currentFunction.dest, it.expr);
        }
        currentBlock.addTerminal(new IRJump(currentBlock, currentFunction.exitBlock));
        currentBlock.finished = true;
    }
    @Override
    public void visit(BreakStmtNode it) {
        if (currentScope.nearestLoop == 1) {
            currentBlock.addTerminal(new IRJump(currentBlock, currentScope.whileStmtNode.endBlock));
        }
        else if (currentScope.nearestLoop == 2) {
            currentBlock.addTerminal(new IRJump(currentBlock, currentScope.forStmtNode.endBlock));
        }
        else {
            throw new CodegenError(it.pos, "Break statement not in loop");
        }
        currentBlock.finished = true;
    }
    @Override
    public void visit(ContinueStmtNode it) {
        if (currentScope.nearestLoop == 1) {
            currentBlock.addTerminal(new IRJump(currentBlock, currentScope.whileStmtNode.condBlock));
        }
        else if (currentScope.nearestLoop == 2) {
            currentBlock.addTerminal(new IRJump(currentBlock, currentScope.forStmtNode.stepBlock));
        }
        else {
            throw new CodegenError(it.pos, "Continue statement not in loop");
        }
        currentBlock.finished = true;
    }
    @Override
    public void visit(ExprStmtNode it) {
        if (it.expr != null) {
            it.expr.accept(this);
        }
    }
    @Override
    public void visit(EmptyStmtNode it) {
        // Do nothing
    }

    // expression
    @Override
    public void visit(SuffixExprNode it) {
        it.expr.accept(this);
        it.entity = getEntity(it.expr);
        IRRegister result = new IRRegister(irInt32, "tmp_suffix");
        IRInstruction calc = null;
        switch (it.opt) {
            case SELF_INC -> {
                calc = new IRCalc(currentBlock, result, IRCalc.OpType.add, irInt32, it.entity, new IRIntConst(1));
            }
            case SELF_DEC -> {
                calc = new IRCalc(currentBlock, result, IRCalc.OpType.sub, irInt32, it.entity, new IRIntConst(1));
            }
        }
        currentBlock.addInstruction(calc);
        currentBlock.addInstruction(new IRStore(currentBlock, result, it.expr.reg));
        // 往变量的 reg 里面存，不要往 ++a 里面存东西
    }
    @Override
    public void visit(MemberExprNode it) {
        it.expr.accept(this);
        IRType type = ((IRPointer) transferType(it.expr.type)).Subscript(); // struct
        IRStruct structType = (IRStruct) type;
        var memberType = structType.memberType.get(it.member.name);
        var memberIndex = structType.memberIndex.get(it.member.name);
        it.reg = new IRRegister(new IRPointer(memberType, 1), "");
        IRGetElementPtr gp = new IRGetElementPtr(currentBlock, transferType(it.expr.type), it.reg, (IRRegister) getEntity(it.expr), new IRIntConst(0));
        gp.idxs.add(new IRIntConst(memberIndex));
        currentBlock.addInstruction(gp);
    }
    @Override
    public void visit(MethodExprNode it) {
        // 解决 method 调用问题
        // 还有内建函数也要这么处理
        it.expr.accept(this);
        // System.out.println(it.expr.type.basicTypeName);
        if (it.expr.type.equals(astString)) { // 这里不能用等号！！要 equals！
            // 判断是不是对应的内建函数
            switch (it.method.name) {
                case "length" -> {
                    IRRegister result = new IRRegister(irInt32, "tmp_func_call");
                    IRCall callInst = new IRCall(currentBlock, "_string_length", result, irInt32);
                    // 已经去掉引号了
                    callInst.args.add(getEntity(it.expr));
                    currentBlock.addInstruction(callInst);
                    it.entity = callInst.addr;
                    return;
                }
                case "substring" -> {
                    IRRegister result = new IRRegister(irString, "tmp_func_call");
                    IRCall callInst = new IRCall(currentBlock, "_string_substring", result, irString);
                    callInst.args.add(getEntity(it.expr));
                    it.args.arguments.get(0).accept(this);
                    callInst.args.add(getEntity(it.args.arguments.get(0)));
                    it.args.arguments.get(1).accept(this);
                    callInst.args.add(getEntity(it.args.arguments.get(1)));
                    currentBlock.addInstruction(callInst);
                    it.entity = callInst.addr;
                    return;
                }
                case "ord" -> {
                    IRRegister result = new IRRegister(irInt32, "tmp_func_call");
                    IRCall callInst = new IRCall(currentBlock, "_string_ord", result, irInt32);
                    callInst.args.add(getEntity(it.expr));
                    it.args.arguments.get(0).accept(this);
                    callInst.args.add(getEntity(it.args.arguments.get(0)));
                    currentBlock.addInstruction(callInst);
                    it.entity = callInst.addr;
                    return;
                }
                case "parseInt" -> {
                    IRRegister result = new IRRegister(irInt32, "tmp_func_call");
                    IRCall callInst = new IRCall(currentBlock, "_string_parseInt", result, irInt32);
                    callInst.args.add(getEntity(it.expr));
                    currentBlock.addInstruction(callInst);
                    it.entity = callInst.addr;
                    return;
                }
                default -> {
                    throw new CodegenError(it.pos, "String: No such method");
                }
            }
        }
        if (it.expr.type.dimension > 0 && it.method.name.equals("size")) {
            // 数组的 size
            IRRegister result = new IRRegister(irInt32, "tmp_func_call");
            IRCall callInst = new IRCall(currentBlock, "_array_size", result, irInt32);
            callInst.args.add(getEntity(it.expr));
            currentBlock.addInstruction(callInst);
            it.entity = callInst.addr;
            return;
        }
        IRType type = ((IRPointer) transferType(it.expr.type)).Subscript(); // struct
        IRStruct structType = (IRStruct) type;

        // 找函数！
        IRFunction irFunc = globalScope.irFunctions.get(structType.name + "." + it.method.name);
        assert(irFunc != null);

        // 找到函数，写调用，加 this 指针
        // returnType 是打包了一层的（？？？）
        IRRegister result = new IRRegister(irFunc.returnType, "tmp_func_call");
        IRType returnType = irFunc.returnType;
        IRCall callInst = new IRCall(currentBlock, irFunc.name, result, returnType);
        callInst.args.add(getEntity(it.expr)); // 不能是 reg，要来一层 getEntity
        if (it.args != null) {
            for (var arg: it.args.arguments) {
                arg.accept(this);
                callInst.args.add(getEntity(arg));
            }
        }
        currentBlock.addInstruction(callInst);

        it.entity = callInst.addr;
    }
    @Override
    public void visit(FuncCallExprNode it) {
        it.func.accept(this);
        FuncDefNode func = it.func.funcDef;
        // 两种可能：全局函数 和 当前类的方法函数（不写this）
        // 无论哪种可能都可以被 funcDef 覆盖到

        func.irType = transferType(func.returnType);
        IRRegister result = new IRRegister(func.irType, "tmp_func_call");
        IRCall callInst = new IRCall(currentBlock, "", result, func.irType);

        // 如果调用的是当前类的方法函数，那么需要加 this 指针，并且改名字
        if (it.func.isMemberFunction) {
            IRRegister thisAddr = currentFunction.thisAddr;
            IRRegister dereferenced = new IRRegister(((IRPointer)(thisAddr.type)).Subscript(), "this");
            // currentFunction.allocas.add(new IRAlloca(currentBlock, ((IRPointer)(thisAddr.type)).Subscript(), dereferenced));
            currentBlock.addInstruction(new IRLoad(currentBlock, dereferenced, thisAddr));
            callInst.args.add(dereferenced);
            callInst.name = currentClass.name + "." + func.funcName.name;
        }
        else {
            callInst.name = func.funcName.name;
        }

        if (it.args != null) {
            for (var arg: it.args.arguments) {
                arg.accept(this);
                callInst.args.add(getEntity(arg));
            }
        }
        currentBlock.addInstruction(callInst);

        it.entity = callInst.addr;
    }
    @Override
    public void visit(SubscriptExprNode it) {
        it.array.accept(this);
        it.subscript.accept(this);
        IRType type = getEntity(it.array).type; // 没取下标的 type
        IRType baseType = ((IRPointer) type).Subscript();
        IRRegister addr = new IRRegister(type, "tmp_subscript");
        currentBlock.addInstruction(new IRGetElementPtr(currentBlock, type, addr, (IRRegister)it.array.entity, getEntity(it.subscript)));

        it.reg = addr;
    }
    @Override
    public void visit(PrefixExprNode it) {
        // 前缀++ --，这个可以做左值，记得给 it.reg 赋值
        it.expr.accept(this);
        IRRegister result = new IRRegister(irInt32, "tmp_prefix");
        IRInstruction calc = null;
        switch (it.opt) {
            case SELF_INC -> {
                calc = new IRCalc(currentBlock, result, IRCalc.OpType.add, irInt32, getEntity(it.expr), new IRIntConst(1));
            }
            case SELF_DEC -> {
                calc = new IRCalc(currentBlock, result, IRCalc.OpType.sub, irInt32, getEntity(it.expr), new IRIntConst(1));
            }
        }
        currentBlock.addInstruction(calc);
        currentBlock.addInstruction(new IRStore(currentBlock, result, it.expr.reg));
        it.entity = result;
        it.reg = it.expr.reg;
    }
    @Override
    public void visit(UnaryExprNode it) {
        // 这里只有 +, -, !, ~ 四种
        it.expr.accept(this);
        switch (it.opt) {
            case LOGIC_NOT -> {
                // bool 取反
                IRRegister result = new IRRegister(irBool, "tmp_logic_not");
                currentBlock.addInstruction(new IRCalc(currentBlock, result, IRCalc.OpType.xor, irBool, getEntity(it.expr), new IRIntConst(1)));
                it.entity = result;
            }
            case BITWISE_NOT -> {
                // int 取反，异或 -1
                IRRegister result = new IRRegister(irInt32, "tmp_bitwise_not");
                currentBlock.addInstruction(new IRCalc(currentBlock, result, IRCalc.OpType.xor, irInt32, getEntity(it.expr), new IRIntConst(-1)));
                it.entity = result;
            }
            case POS -> {
                // +a = a
                it.entity = getEntity(it.expr);
            }
            case NEG -> {
                // -a = 0 - a
                IRRegister result = new IRRegister(irInt32, "tmp_neg");
                currentBlock.addInstruction(new IRCalc(currentBlock, result, IRCalc.OpType.sub, irInt32, new IRIntConst(0), getEntity(it.expr)));
                it.entity = result;
            }
        }
    }

    private Entity getArray(IRType type, ArrayList<ExprNode> sizes, int step) {

        sizes.get(step).accept(this);
        Entity sizeofCur = getEntity(sizes.get(step));
        int bytes = ((IRPointer) type).Subscript().getBytes();
        Entity mallocSize = new IRIntConst(0);
        if (sizeofCur instanceof IRConst) {
            // 算完了
            mallocSize = new IRIntConst(((IRIntConst) sizeofCur).value * bytes + 4);
        }
        else {
            // 开一个 calc
            IRRegister result = new IRRegister(irInt32, "tmp_array_size");
            currentBlock.addInstruction(new IRCalc(currentBlock, result, IRCalc.OpType.mul, irInt32, sizeofCur, new IRIntConst(bytes)));
            // TODO: 还要存 size，加个 4
            IRRegister tmp = new IRRegister(irInt32, "tmp_array_size");
            currentBlock.addInstruction(new IRCalc(currentBlock, tmp, IRCalc.OpType.add, irInt32, result, new IRIntConst(4)));
            mallocSize = tmp;
        }
        IRRegister tmpArray = new IRRegister(type, "tmp_array");
        IRCall irCall = new IRCall(currentBlock, "_new_array", tmpArray, new IRPointer(type, 1));
        irCall.args.add(mallocSize);
        irCall.args.add(sizeofCur);
        currentBlock.addInstruction(irCall);

        if (step + 1 < sizes.size()) {
            IRRegister index = new IRRegister(new IRPointer(irInt32, 1), "tmp_array_index"); // for 循环中的 i
            currentScope = new Scope(Scope.ScopeType.Block, currentScope);
            IRBasicBlock condBlock = new IRBasicBlock(currentFunction, "array_cond");
            IRBasicBlock bodyBlock = new IRBasicBlock(currentFunction, "array_body");
            IRBasicBlock stepBlock = new IRBasicBlock(currentFunction, "array_step");
            IRBasicBlock endBlock = new IRBasicBlock(currentFunction, "array_end");
            endBlock.terminal = currentBlock.terminal;

            currentFunction.allocas.add(new IRAlloca(currentBlock, new IRPointer(irInt32, 1), index));
            currentBlock.addInstruction(new IRStore(currentBlock, new IRIntConst(0), index));
            // Init: index = 0
            currentBlock.terminal = new IRJump(currentBlock, condBlock);
            currentBlock.finished = true;

            currentFunction.addBlock(condBlock);
            currentBlock = condBlock;
            IRRegister indexforCmp = new IRRegister(irInt32, "");
            IRRegister cmpResult = new IRRegister(irBool, "");
            currentBlock.addInstruction(new IRLoad(currentBlock, indexforCmp, index));
            currentBlock.addInstruction(new IRBinaryOpt(currentBlock, cmpResult, IRBinaryOpt.OpType.slt, irInt32, indexforCmp, sizeofCur));
            currentBlock.addTerminal(new IRBranch(currentBlock, cmpResult, bodyBlock, endBlock));
            currentBlock.finished = true;

            currentFunction.addBlock(bodyBlock);
            currentBlock = bodyBlock;
            Entity nextArray = getArray(((IRPointer) type).Subscript(), sizes, step + 1);
            IRRegister ptr = new IRRegister(type, "");
            IRRegister index2 = new IRRegister(irInt32, "");
            currentBlock.addInstruction(new IRLoad(currentBlock, index2, index));
            currentBlock.addInstruction(new IRGetElementPtr(currentBlock, type, ptr, tmpArray, index2));
            currentBlock.addInstruction(new IRStore(currentBlock, nextArray, ptr));
            currentBlock.addTerminal(new IRJump(currentBlock, stepBlock));
            currentBlock.finished = true;

            currentFunction.addBlock(stepBlock);
            currentBlock = stepBlock;
            IRRegister addedIndex = new IRRegister(irInt32, "");
            IRRegister index3 = new IRRegister(irInt32, "");
            currentBlock.addInstruction(new IRLoad(currentBlock, index3, index));
            currentBlock.addInstruction(new IRCalc(currentBlock, addedIndex, IRCalc.OpType.add, irInt32, index3, new IRIntConst(1)));
            currentBlock.addInstruction(new IRStore(currentBlock, addedIndex, index));
            currentBlock.addTerminal(new IRJump(currentBlock, condBlock));

            currentFunction.addBlock(endBlock);
            currentBlock = endBlock;
        }
        return tmpArray;
    }
    @Override
    public void visit(NewExprNode it) {
        IRType type = transferType(it.type);
        if (it.dimension > 0) {
            // 数组
            if (it.exprNodes.size() > 0) {
                // 有东西
                it.entity = getArray(type, it.exprNodes, 0);
            }
            else {
                // 没东西
                it.entity = new IRNullConst(type);
            }
        }
        else {
            IRStruct structType = (IRStruct) ((IRPointer) type).Subscript();
            it.entity = new IRRegister(type, "tmp_new");
            IRCall call = new IRCall(currentBlock, "malloc", (IRRegister) it.entity, type);
            call.args.add(new IRIntConst(structType.getBytes())); // 这里忘记 *4 了
            currentBlock.addInstruction(call);
            if (structType.hasConstructor) {
                IRCall callConstructor = new IRCall(currentBlock, structType.name + "." + structType.name, null, irVoid);
                callConstructor.args.add(it.entity);
                currentBlock.addInstruction(callConstructor);
            }
        }
    }
    @Override
    public void visit(BinaryExprNode it) {
        // 注意短路求值问题：&& ||
        if (it.opt == BinaryExprNode.BinaryOpt.LOG_AND || it.opt == BinaryExprNode.BinaryOpt.LOG_OR) {
            // 变量用指针存
            IRRegister result = new IRRegister(new IRPointer(irBool, 1), "tmp");
            // alloca
            currentFunction.allocas.add(new IRAlloca(currentBlock, irBool, result)); // alloca 必须加到函数的最前面！！！
            IRBasicBlock rightSideBlock = new IRBasicBlock(currentFunction, "logic_right_side");
            // 用于 visit 右侧表达式
            IRBasicBlock trueBlock = new IRBasicBlock(currentFunction, "logic_true");
            IRBasicBlock falseBlock = new IRBasicBlock(currentFunction, "logic_false");
            IRBasicBlock endBlock = new IRBasicBlock(currentFunction, "logic_end");
            // IRBasicBlock curBlock = currentBlock;
            endBlock.terminal = currentBlock.terminal;

            it.lhs.accept(this);
            Entity lValue = getEntity(it.lhs);
            if (it.opt == BinaryExprNode.BinaryOpt.LOG_AND) {
                currentBlock.addTerminal(new IRBranch(currentBlock, lValue, rightSideBlock, falseBlock));
            }
            else {
                currentBlock.addTerminal(new IRBranch(currentBlock, lValue, trueBlock, rightSideBlock));
            }
            currentBlock.finished = true;

            currentFunction.addBlock(rightSideBlock);
            currentBlock = rightSideBlock;
            it.rhs.accept(this);
            Entity rValue = getEntity(it.rhs);
            currentBlock.addTerminal(new IRBranch(currentBlock, rValue, trueBlock, falseBlock));
            currentBlock.finished = true;

            currentFunction.addBlock(trueBlock);
            currentBlock = trueBlock;
            currentBlock.addInstruction(new IRStore(currentBlock, irTrue, result));
            currentBlock.addTerminal(new IRJump(currentBlock, endBlock));
            currentBlock.finished = true;

            currentFunction.addBlock(falseBlock);
            currentBlock = falseBlock;
            currentBlock.addInstruction(new IRStore(currentBlock, irFalse, result));
            currentBlock.addTerminal(new IRJump(currentBlock, endBlock));
            currentBlock.finished = true;

            currentFunction.addBlock(endBlock);
            currentBlock = endBlock;

            // 取值出来
            it.entity = new IRRegister(irBool, "");
            currentBlock.addInstruction(new IRLoad(currentBlock, (IRRegister) it.entity, result));
        }
        else {
            it.lhs.accept(this);
            it.rhs.accept(this);
            Entity lValue = getEntity(it.lhs), rValue = getEntity(it.rhs);
            // TODO: String 的处理
            if (it.lhs.type.equals(astString) || it.rhs.type.equals(astString)) {
                // String 的处理
                switch (it.opt) {
                    case ADD -> {
                        it.entity = new IRRegister(irString, "tmp_string");
                        IRCall irCall = new IRCall(currentBlock, "_string_add", (IRRegister) it.entity, irString);
                        irCall.args.add(lValue);
                        irCall.args.add(rValue);
                        currentBlock.addInstruction(irCall);
                    }
                    case LT -> {
                        IRRegister result = new IRRegister(irBool, "tmp_string");
                        IRCall irCall = new IRCall(currentBlock, "_string_lt", result, irBool);
                        irCall.args.add(lValue);
                        irCall.args.add(rValue);
                        currentBlock.addInstruction(irCall);
                        it.entity = result; // 非常生草，没有设 it.entity 居然能过三目之前的所有点
                    }
                    case LE -> {
                        IRRegister result = new IRRegister(irBool, "tmp_string");
                        IRCall irCall = new IRCall(currentBlock, "_string_le", result, irBool);
                        irCall.args.add(lValue);
                        irCall.args.add(rValue);
                        currentBlock.addInstruction(irCall);
                        it.entity = result;
                    }
                    case GT -> {
                        IRRegister result = new IRRegister(irBool, "tmp_string");
                        IRCall irCall = new IRCall(currentBlock, "_string_gt", result, irBool);
                        irCall.args.add(lValue);
                        irCall.args.add(rValue);
                        currentBlock.addInstruction(irCall);
                        it.entity = result;
                    }
                    case GE -> {
                        IRRegister result = new IRRegister(irBool, "tmp_string");
                        IRCall irCall = new IRCall(currentBlock, "_string_ge", result, irBool);
                        irCall.args.add(lValue);
                        irCall.args.add(rValue);
                        currentBlock.addInstruction(irCall);
                        it.entity = result;
                    }
                    case EQ -> {
                        IRRegister result = new IRRegister(irBool, "tmp_string");
                        IRCall irCall = new IRCall(currentBlock, "_string_eq", result, irBool);
                        irCall.args.add(lValue);
                        irCall.args.add(rValue);
                        currentBlock.addInstruction(irCall);
                        it.entity = result;
                    }
                    case NE -> {
                        IRRegister result = new IRRegister(irBool, "tmp_string");
                        IRCall irCall = new IRCall(currentBlock, "_string_ne", result, irBool);
                        irCall.args.add(lValue);
                        irCall.args.add(rValue);
                        currentBlock.addInstruction(irCall);
                        it.entity = result;
                    }
                }
                return; // 这？string搞完还要到后面去干嘛？？？？？？？？
            }


            IRBinaryOpt.OpType binaryOpType = null;
            IRCalc.OpType calcOpType = null;

            switch (it.opt) {
                case EQ -> {
                    binaryOpType = IRBinaryOpt.OpType.eq;
                }
                case NE -> {
                    binaryOpType = IRBinaryOpt.OpType.ne;
                }
                case LT -> {
                    binaryOpType = IRBinaryOpt.OpType.slt;
                }
                case GT -> {
                    binaryOpType = IRBinaryOpt.OpType.sgt;
                }
                case LE -> {
                    binaryOpType = IRBinaryOpt.OpType.sle;
                }
                case GE -> {
                    binaryOpType = IRBinaryOpt.OpType.sge;
                }
                case ADD -> {
                    calcOpType = IRCalc.OpType.add;
                }
                case SUB -> {
                    calcOpType = IRCalc.OpType.sub;
                }
                case MUL -> {
                    calcOpType = IRCalc.OpType.mul;
                }
                case DIV -> {
                    calcOpType = IRCalc.OpType.sdiv;
                }
                case MOD -> {
                    calcOpType = IRCalc.OpType.srem;
                }
                case SHL -> {
                    calcOpType = IRCalc.OpType.shl;
                }
                case SHR -> {
                    calcOpType = IRCalc.OpType.ashr;
                }
                case BIT_AND -> {
                    calcOpType = IRCalc.OpType.and;
                }
                case BIT_OR -> {
                    calcOpType = IRCalc.OpType.or;
                }
                case BIT_XOR -> {
                    calcOpType = IRCalc.OpType.xor;
                }
            }
            if (it.opt == BinaryExprNode.BinaryOpt.EQ || it.opt == BinaryExprNode.BinaryOpt.NE) {
                // 判是否相等；可能涉及类型转换
                IRType lType = transferType(it.lhs.type), rType = transferType(it.rhs.type);
                IRType retType = null;
                if (lType.isNull()) {
                    retType = rType;
                }
                else {
                    retType = lType;
                }
                IRRegister result = new IRRegister(irBool, "tmp"); // 这个寄存器的类型设错了，md
                it.entity = result;
                currentBlock.addInstruction(new IRBinaryOpt(currentBlock, result, binaryOpType, retType, lValue, rValue));
            } else if (binaryOpType != null) {
                // 是 binary opt
                IRRegister result = new IRRegister(irBool, "tmp_binary");
                it.entity = result;
                currentBlock.addInstruction(new IRBinaryOpt(currentBlock, result, binaryOpType, irInt32, lValue, rValue));
            }
            else if (calcOpType != null) {
                // 是 calc
                IRRegister result = new IRRegister(irInt32, "tmp_calc");
                it.entity = result;
                currentBlock.addInstruction(new IRCalc(currentBlock, result, calcOpType, irInt32, lValue, rValue));
            }
        }
    }
    @Override
    public void visit(TernaryExprNode it) {
        // 两种可能：求值 or 调用不一样的函数 etc.
        // 总之就是返回值是 void 还是一个具体的值
        it.cond.accept(this);
        IRBasicBlock trueBlock = new IRBasicBlock(currentFunction, "ternary_true");
        IRBasicBlock falseBlock = new IRBasicBlock(currentFunction, "ternary_false");
        IRBasicBlock endBlock = new IRBasicBlock(currentFunction, "ternary_end");

        IRType type = transferType(it.type);
        IRType typePtr = getPtrType(it.type);
        Entity condition = getEntity(it.cond);


        if (type.equals(irVoid) || type.equals(irNull)) {
            // 执行语句，没有返回值
            endBlock.addTerminal(currentBlock.terminal);
            currentBlock.terminal = new IRBranch(currentBlock, condition, trueBlock, falseBlock);
            currentBlock.finished = true;

            currentFunction.addBlock(trueBlock);
            currentBlock = trueBlock;
            it.thenExpr.accept(this);
            currentBlock.addTerminal(new IRJump(currentBlock, endBlock));
            currentBlock.finished = true;

            currentFunction.addBlock(falseBlock);
            currentBlock = falseBlock;
            it.elseExpr.accept(this);
            currentBlock.addTerminal(new IRJump(currentBlock, endBlock));
            currentBlock.finished = true;

            currentFunction.addBlock(endBlock);
            currentBlock = endBlock;
            it.entity = new IRVoidConst();
            it.reg = null;
        }
        else {
            // 有返回值
            it.reg = new IRRegister(typePtr, "tmp_ternary");
            currentFunction.allocas.add(new IRAlloca(currentBlock, type, it.reg));
            endBlock.addTerminal(currentBlock.terminal);
            currentBlock.terminal = new IRBranch(currentBlock, condition, trueBlock, falseBlock);
            currentBlock.finished = true;

            currentFunction.addBlock(trueBlock);
            currentBlock = trueBlock;
            it.thenExpr.accept(this);
            storeExpr(it.reg, it.thenExpr);
            currentBlock.addTerminal(new IRJump(currentBlock, endBlock));
            currentBlock.finished = true;

            currentFunction.addBlock(falseBlock);
            currentBlock = falseBlock;
            it.elseExpr.accept(this);
            storeExpr(it.reg, it.elseExpr);
            currentBlock.addTerminal(new IRJump(currentBlock, endBlock));
            currentBlock.finished = true;

            currentFunction.addBlock(endBlock);
            currentBlock = endBlock;

            IRRegister tmp = new IRRegister(((IRPointer) it.reg.type).Subscript(), "tmp");
            currentBlock.addInstruction(new IRLoad(currentBlock, tmp, it.reg));
            it.entity = tmp;
        }
    }
    @Override
    public void visit(AssignExprNode it) {
        it.lhs.accept(this);
        it.rhs.accept(this);
        it.reg = it.lhs.reg;
        it.entity = getEntity(it.rhs);
        storeExpr(it.reg, it.rhs);
    }

    @Override
    public void visit(ArgumentListNode it) {

    }

    // primary
    @Override
    public void visit(ParenExprNode it) {
        it.expr.accept(this);
        it.entity = getEntity(it.expr);
    }
    @Override
    public void visit(ThisExprNode it) {
        // this 是这样的
        it.reg = currentFunction.thisAddr;
    }

    // literal
    @Override
    public void visit(IntConstNode it) {
        it.entity = new IRIntConst(it.value);
    }

    // string 字面量要全扫一遍先在 LLVM 的 init 函数里面写进去
    private String antiReplace(String str) {
        return str.replace("\\n", "\n").replace("\\\"", "\"").replace("\\\\", "\\");
    }
    @Override
    public void visit(StringConstNode it) {
        if (program.stringConsts.containsKey(it.value)) {
            it.entity = program.stringConsts.get(it.value);
            return;
        }
        String tmp = it.value.substring(1, it.value.length() - 1); // 去掉两端的引号
        String add = antiReplace(tmp);
        IRStringConst str = new IRStringConst(add);
        it.entity = str;
        program.stringConsts.put(it.value, str);
        program.stringArray.add(str);
    }
    @Override
    public void visit(BoolConstNode it) {
        it.entity = it.value ? irTrue : irFalse;
    }
    @Override
    public void visit(NullConstNode it) {
        it.entity = new IRNullConst();
    }
    @Override
    public void visit(IdentifierNode it) {
        it.reg = currentScope.getIRVariable(it.name, true);
        if (it.reg == null && it.funcDef == null) {
            // 没找到
            IRRegister thisPtr = currentFunction.paramList.get(0);
            assert(thisPtr != null);
            IRRegister thisAddr = currentFunction.thisAddr;
            // System.out.println("fuck" + ((IRPointer)(thisPtr.type)).Subscript().toString());
            IRType classType = ((IRPointer)(thisPtr.type)).Subscript();
            IRRegister thisValue = new IRRegister(thisPtr.type, "this");
            currentBlock.addInstruction(new IRLoad(currentBlock, thisValue, thisAddr));
            var memberType = ((IRStruct)classType).memberType.get(it.name);
            var memberId = ((IRStruct)classType).memberIndex.get(it.name);
            it.reg = new IRRegister(new IRPointer(memberType, 1), "this_" + it.name);
            IRGetElementPtr gp = new IRGetElementPtr(currentBlock, thisPtr.type, it.reg, thisValue, new IRIntConst(0));
            gp.idxs.add(new IRIntConst(memberId));
            currentBlock.addInstruction(gp);
        }
    }
}
