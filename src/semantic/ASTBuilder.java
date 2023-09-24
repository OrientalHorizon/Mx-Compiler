package semantic;

import ast.*;
import org.antlr.v4.runtime.tree.ParseTree;
import util.Position;
import util.Type;
import util.error.*;
import grammar.MxParserBaseVisitor;
import grammar.MxParser;

import java.util.ArrayList;

public class ASTBuilder extends MxParserBaseVisitor<ASTNode> {
    public ASTBuilder() {
    }

    @Override
    public ASTNode visitProgram(MxParser.ProgramContext ctx) {
        Position pos = new Position(ctx.getStart());

        ArrayList<ASTNode> defNodes = new ArrayList<ASTNode>();
        for (var def : ctx.def()) {
            ASTNode defNode = (ASTNode) visit(def);
            defNodes.add(defNode);
        }

        return new ProgramNode(pos, defNodes);
    }

    @Override
    public ASTNode visitDef(MxParser.DefContext ctx) {
        if (ctx.classDef() != null) {
            return visit(ctx.classDef());
        } else if (ctx.funcDef() != null) {
            return visit(ctx.funcDef());
        } else if (ctx.varDef() != null) {
            return visit(ctx.varDef());
        } else {
            throw new MyError(new Position(ctx.getStart()), "visitDef");
        }
    }

    @Override
    public ASTNode visitClassDef(MxParser.ClassDefContext ctx) {
        Position pos = new Position(ctx.getStart());

        String className = ctx.Identifiers().getText();
        ArrayList<VarDefNode> members = new ArrayList<VarDefNode>();
        ArrayList<FuncDefNode> methods = new ArrayList<FuncDefNode>();
        for (var varDef : ctx.varDef()) {
            VarDefNode member = (VarDefNode) visit(varDef);
            members.add(member);
        }
        for (var funcDef : ctx.funcDef()) {
            FuncDefNode method = (FuncDefNode) visit(funcDef);
            methods.add(method);
        }
        if (ctx.constructor().size() >= 2) {
            throw new MyError(new Position(ctx.getStart()), "too many constructors");
        }
        ConstructorNode constructorNode = null;
        for (var constructor : ctx.constructor()) {
            constructorNode = (ConstructorNode) visit(constructor);
        }
        IdentifierNode identifier = new IdentifierNode(pos, className, false);

        return new ClassDefNode(pos, identifier, members, methods, constructorNode);
    }

    @Override
    public ASTNode visitFuncDef(MxParser.FuncDefContext ctx) {
        Position pos = new Position(ctx.getStart());

        String funcName = ctx.Identifiers().getText();
        String returnTypeString = ctx.typeVoid().getText();
        Type returnType = new Type(returnTypeString);
        ParamListNode parameters = (ParamListNode) visit(ctx.paramList());
        SuiteStmtNode body = (SuiteStmtNode) visit(ctx.suite());
        // System.out.println(body.stmts.size());
        return new FuncDefNode(pos, new IdentifierNode(pos, funcName, false), returnType, parameters, body);
    }

    @Override
    public ASTNode visitParamList(MxParser.ParamListContext ctx) {
        Position pos = new Position(ctx.getStart());

        ArrayList<VariableNode> paramDef = new ArrayList<VariableNode>();
        for (int i = 0; i < ctx.paramDef().size(); i++) {
            // type, name
            Type type = new Type(ctx.paramDef(i).type().getText());
            IdentifierNode name = new IdentifierNode(new Position(ctx.paramDef(i).getStart()), ctx.paramDef(i).Identifiers().getText(), true);
            VariableNode paramNode = new VariableNode(pos, type, name, null);
            paramDef.add(paramNode);
        }
        return new ParamListNode(pos, paramDef);
    }

    @Override
    public ASTNode visitConstructor(MxParser.ConstructorContext ctx) {
        Position pos = new Position(ctx.getStart());

        String funcName = ctx.Identifiers().getText();
        SuiteStmtNode body = (SuiteStmtNode) visit(ctx.suite());
        return new ConstructorNode(pos, new IdentifierNode(pos, funcName, false), body);
    }

    @Override
    public ASTNode visitVarDef(MxParser.VarDefContext ctx) {
        Position pos = new Position(ctx.getStart());

        Type type = new Type(ctx.type().getText());
        ArrayList<VariableNode> varNodes = new ArrayList<VariableNode>();
        for (int i = 0; i < ctx.singleVarDef().size(); i++) {
            VariableNode varNode = (VariableNode) visit(ctx.singleVarDef(i));
            varNode.type = type;
            varNodes.add(varNode);
        }
        // System.out.println("visitVarDef" + varNodes.size());
        return new VarDefNode(pos, varNodes);
    }

    @Override
    public ASTNode visitSingleVarDef(MxParser.SingleVarDefContext ctx) {
        Position pos = new Position(ctx.getStart());

        IdentifierNode name = new IdentifierNode(pos, ctx.Identifiers().getText(), true);
        ExprNode init = null;
        if (ctx.expression() != null) {
            init = (ExprNode) visit(ctx.expression());
        }
        return new VariableNode(pos, null, name, init);
    }

    @Override
    public ASTNode visitSuiteStmt(MxParser.SuiteStmtContext ctx) {
        Position pos = new Position(ctx.getStart());

        ArrayList<StmtNode> stmts = new ArrayList<StmtNode>();
        for (int i = 0; i < ctx.suite().statement().size(); i++) {
            StmtNode stmtNode = (StmtNode) visit(ctx.suite().statement(i));
            stmts.add(stmtNode);
        }
        return new SuiteStmtNode(pos, stmts);
    }

    @Override
    public ASTNode visitSuite(MxParser.SuiteContext ctx) {
        Position pos = new Position(ctx.getStart());

        ArrayList<StmtNode> stmts = new ArrayList<StmtNode>();
        for (int i = 0; i < ctx.statement().size(); i++) {
            StmtNode stmtNode = (StmtNode) visit(ctx.statement(i));
            stmts.add(stmtNode);
        }
        return new SuiteStmtNode(pos, stmts);
    }

    @Override
    public ASTNode visitVarDefStmt(MxParser.VarDefStmtContext ctx) {
        Position pos = new Position(ctx.getStart());

        Type type = new Type(ctx.varDef().type().getText());
        ArrayList<VariableNode> varNodes = new ArrayList<VariableNode>();
        for (int i = 0; i < ctx.varDef().singleVarDef().size(); i++) {
            VariableNode varNode = (VariableNode) visit(ctx.varDef().singleVarDef(i));
            varNode.type = type;
            varNodes.add(varNode);
        }
        return new VarDefNode(pos, varNodes);
    }

    @Override
    public ASTNode visitIfStmt(MxParser.IfStmtContext ctx) {
        Position pos = new Position(ctx.getStart());

        ExprNode condition = (ExprNode) visit(ctx.expression());
        StmtNode thenStmt = (StmtNode) visit(ctx.statement(0));
        StmtNode elseStmt = null;
        if (ctx.statement().size() == 2) {
            elseStmt = (StmtNode) visit(ctx.statement(1));
        }
        return new IfStmtNode(pos, condition, thenStmt, elseStmt);
    }

    @Override
    public ASTNode visitWhileStmt(MxParser.WhileStmtContext ctx) {
        Position pos = new Position(ctx.getStart());

        ExprNode condition = (ExprNode) visit(ctx.expression());
        StmtNode body = (StmtNode) visit(ctx.statement());
        return new WhileStmtNode(pos, condition, body);
    }

    @Override
    public ASTNode visitForStmt(MxParser.ForStmtContext ctx) {
        Position pos = new Position(ctx.getStart());

        ASTNode initStmt = null;
        if (ctx.initStmt != null) {
            if (ctx.initStmt.varDef() != null) {
                // varDef
                initStmt = visit(ctx.initStmt.varDef());
            }
            else if (ctx.initStmt.expression() != null) {
                // expression
                initStmt = visit(ctx.initStmt.expression());
            }
            else {
                // throw new SemanticError(new Position(ctx.getStart()), "invalid initStmt");
            }
        }
        ExprNode conditionExpr = null;
        if (ctx.conditionExpr != null) {
            conditionExpr = (ExprNode) visit(ctx.conditionExpr);
        }
        ExprNode incExpr = null;
        if (ctx.incExpr != null) {
            incExpr = (ExprNode) visit(ctx.incExpr);
        }
        StmtNode body = (StmtNode) visit(ctx.statement());
        return new ForStmtNode(pos, initStmt, conditionExpr, incExpr, body);
    }

    @Override
    public ASTNode visitReturnStmt(MxParser.ReturnStmtContext ctx) {
        Position pos = new Position(ctx.getStart());

        ExprNode returnValue = null;
        if (ctx.expression() != null) {
            returnValue = (ExprNode) visit(ctx.expression());
        }
        return new ReturnStmtNode(pos, returnValue);
    }

    @Override
    public ASTNode visitBreakStmt(MxParser.BreakStmtContext ctx) {
        Position pos = new Position(ctx.getStart());

        return new BreakStmtNode(pos);
    }

    @Override
    public ASTNode visitContinueStmt(MxParser.ContinueStmtContext ctx) {
        Position pos = new Position(ctx.getStart());

        return new ContinueStmtNode(pos);
    }

    @Override
    public ASTNode visitExprStmt(MxParser.ExprStmtContext ctx) {
        Position pos = new Position(ctx.getStart());

        ExprNode expr = (ExprNode) visit(ctx.expression());
        return new ExprStmtNode(pos, expr);
    }

    @Override
    public ASTNode visitEmptyStmt(MxParser.EmptyStmtContext ctx) {
        Position pos = new Position(ctx.getStart());

        return new EmptyStmtNode(pos);
    }

    // expression
    @Override
    public ASTNode visitAtomExpr(MxParser.AtomExprContext ctx) {
        Position pos = new Position(ctx.getStart());
        if (ctx.primary().Identifiers() != null) {
            // identifier
            // 真的是变量名吗？
            return new IdentifierNode(pos, ctx.primary().Identifiers().getText(), true);
        }
        else if (ctx.primary().This() != null) {
            return new ThisExprNode(pos);
        }
        else if (ctx.primary().literal() != null) {
            return visit(ctx.primary().literal());
        }
        else if (ctx.primary().expression() != null) {
            return visit(ctx.primary().expression());
        }
        else {
            throw new SemanticError(new Position(ctx.getStart()), "invalid atomExpr");
        }
    }

    @Override
    public ASTNode visitSuffixExpr(MxParser.SuffixExprContext ctx) {
        Position pos = new Position(ctx.getStart());

        ExprNode expr = (ExprNode) visit(ctx.expression());
        String op = ctx.opt.getText();
        if (op.equals("++")) {
            return new SuffixExprNode(pos, expr, SuffixExprNode.SuffixOpt.SELF_INC);
        }
        else if (op.equals("--")) {
            return new SuffixExprNode(pos, expr, SuffixExprNode.SuffixOpt.SELF_DEC);
        }
        else {
            throw new SemanticError(new Position(ctx.getStart()), "invalid suffixExpr");
        }
    }

    @Override
    public ASTNode visitMemberExpr(MxParser.MemberExprContext ctx) {
        Position pos = new Position(ctx.getStart());

        ExprNode expr = (ExprNode) visit(ctx.expression());
        String member = ctx.Identifiers().getText();
        return new MemberExprNode(pos, expr, new IdentifierNode(pos, member, true));
    }

    @Override
    public ASTNode visitMethodExpr(MxParser.MethodExprContext ctx) {
        Position pos = new Position(ctx.getStart());

        ExprNode expr = (ExprNode) visit(ctx.expression());
        IdentifierNode method = new IdentifierNode(pos, ctx.Identifiers().getText(), false);
//        ArrayList<ExprNode> args = new ArrayList<ExprNode>();
//        for (int i = 0; i < ctx.argumentList().expression().size(); i++) {
//            ExprNode arg = (ExprNode) visit(ctx.argumentList().expression(i));
//            args.add(arg);
//        }
        if (ctx.argumentList() == null) {
            return new MethodExprNode(pos, expr, method, new ArgumentListNode(pos, new ArrayList<ExprNode>()));
        }
        return new MethodExprNode(pos, expr, method, (ArgumentListNode) visit(ctx.argumentList()));
    }

    @Override
    public ASTNode visitFuncCallExpr(MxParser.FuncCallExprContext ctx) {
        Position pos = new Position(ctx.getStart());

        IdentifierNode func = new IdentifierNode(pos, ctx.Identifiers().getText(), false);
        if (ctx.argumentList() == null) {
            return new FuncCallExprNode(pos, func, new ArgumentListNode(pos, new ArrayList<ExprNode>()));
        }
        return new FuncCallExprNode(pos, func, (ArgumentListNode) visit(ctx.argumentList()));
    }

    @Override
    public ASTNode visitArgumentList(MxParser.ArgumentListContext ctx) {
        Position pos = new Position(ctx.getStart());

        ArrayList<ExprNode> args = new ArrayList<ExprNode>();
        for (int i = 0; i < ctx.expression().size(); i++) {
            ExprNode arg = (ExprNode) visit(ctx.expression(i));
            args.add(arg);
        }
        return new ArgumentListNode(pos, args);
    }

    @Override
    public ASTNode visitSubscriptExpr(MxParser.SubscriptExprContext ctx) {
        Position pos = new Position(ctx.getStart());

        ExprNode array = (ExprNode) visit(ctx.expression(0));
        ExprNode index = (ExprNode) visit(ctx.expression(1));
        return new SubscriptExprNode(pos, array, index);
    }

    @Override
    public ASTNode visitPrefixExpr(MxParser.PrefixExprContext ctx) {
        Position pos = new Position(ctx.getStart());

        ExprNode expr = (ExprNode) visit(ctx.expression());
        String op = ctx.opt.getText();
        if (op.equals("++")) {
            return new PrefixExprNode(pos, expr, PrefixExprNode.PrefixOpt.SELF_INC);
        }
        else if (op.equals("--")) {
            return new PrefixExprNode(pos, expr, PrefixExprNode.PrefixOpt.SELF_DEC);
        }
        else {
            throw new SemanticError(new Position(ctx.getStart()), "invalid prefixExpr");
        }
    }

    @Override
    public ASTNode visitUnaryExpr(MxParser.UnaryExprContext ctx) {
        Position pos = new Position(ctx.getStart());

        ExprNode expr = (ExprNode) visit(ctx.expression());
        String op = ctx.opt.getText();
        if (op.equals("+")) {
            return new UnaryExprNode(pos, expr, UnaryExprNode.UnaryOpt.POS);
        }
        else if (op.equals("-")) {
            return new UnaryExprNode(pos, expr, UnaryExprNode.UnaryOpt.NEG);
        }
        else if (op.equals("~")) {
            return new UnaryExprNode(pos, expr, UnaryExprNode.UnaryOpt.BITWISE_NOT);
        }
        else if (op.equals("!")) {
            return new UnaryExprNode(pos, expr, UnaryExprNode.UnaryOpt.LOGIC_NOT);
        }
        else {
            throw new SemanticError(new Position(ctx.getStart()), "invalid unaryExpr");
        }
    }

    @Override
    public ASTNode visitNewExpr(MxParser.NewExprContext ctx) {
        Position pos = new Position(ctx.getStart());
        // System.out.println("visitNewExpr: fsuygagyis");
        if (ctx.newUnit().errorCreator() != null) {
            throw new SemanticError(new Position(ctx.getStart()), "invalid newExpr");
        }
        else if (ctx.newUnit().arrayCreator() != null) {
            ArrayList<ExprNode> exprNodes = new ArrayList<ExprNode>();
            for (int i = 0; i < ctx.newUnit().arrayCreator().expression().size(); i++) {
                ExprNode expr = (ExprNode) visit(ctx.newUnit().arrayCreator().expression(i));
                exprNodes.add(expr);
            }
            Type basicType = new Type(ctx.newUnit().arrayCreator().typeWithoutArray().getText(), 0);
            // get dimension
            int dimension = ctx.newUnit().arrayCreator().LeftBracket().size();
            return new NewExprNode(pos, basicType, exprNodes, dimension, false);
        }
        else if (ctx.newUnit().classCreator() != null) {
            Type basicType = new Type(ctx.newUnit().classCreator().typeWithoutArray().getText(), 0);
            return new NewExprNode(pos, basicType, null, 0, false);
        }
        else if (ctx.newUnit().unitCreator() != null) {
            Type basicType = new Type(ctx.newUnit().unitCreator().typeWithoutArray().getText(), 0);
            return new NewExprNode(pos, basicType, null, 0, false);
        }
        else {
            throw new SemanticError(new Position(ctx.getStart()), "invalid newExpr");
        }
    }

    @Override
    public ASTNode visitBinaryExpr(MxParser.BinaryExprContext ctx) {
        Position pos = new Position(ctx.getStart());
        ExprNode lhs = (ExprNode) visit(ctx.expression(0));
        ExprNode rhs = (ExprNode) visit(ctx.expression(1));
        String optString = ctx.opt.getText();
        BinaryExprNode.BinaryOpt opt = switch (optString) {
            case "*" -> BinaryExprNode.BinaryOpt.MUL;
            case "/" -> BinaryExprNode.BinaryOpt.DIV;
            case "%" -> BinaryExprNode.BinaryOpt.MOD;
            case "+" -> BinaryExprNode.BinaryOpt.ADD;
            case "-" -> BinaryExprNode.BinaryOpt.SUB;
            case "<<" -> BinaryExprNode.BinaryOpt.SHL;
            case ">>" -> BinaryExprNode.BinaryOpt.SHR;
            case "<" -> BinaryExprNode.BinaryOpt.LT;
            case ">" -> BinaryExprNode.BinaryOpt.GT;
            case "<=" -> BinaryExprNode.BinaryOpt.LE;
            case ">=" -> BinaryExprNode.BinaryOpt.GE;
            case "==" -> BinaryExprNode.BinaryOpt.EQ;
            case "!=" -> BinaryExprNode.BinaryOpt.NE;
            case "&" -> BinaryExprNode.BinaryOpt.BIT_AND;
            case "^" -> BinaryExprNode.BinaryOpt.BIT_XOR;
            case "|" -> BinaryExprNode.BinaryOpt.BIT_OR;
            case "&&" -> BinaryExprNode.BinaryOpt.LOG_AND;
            case "||" -> BinaryExprNode.BinaryOpt.LOG_OR;
            default -> throw new SemanticError(new Position(ctx.getStart()), "invalid binaryExpr");
        };

        return new BinaryExprNode(pos, lhs, rhs, opt);
    }

    @Override
    public ASTNode visitTernaryExpr(MxParser.TernaryExprContext ctx) {
        Position pos = new Position(ctx.getStart());

        ExprNode cond = (ExprNode) visit(ctx.expression(0));
        ExprNode lhs = (ExprNode) visit(ctx.expression(1));
        ExprNode rhs = (ExprNode) visit(ctx.expression(2));
        return new TernaryExprNode(pos, cond, lhs, rhs);
    }

    @Override
    public ASTNode visitAssignExpr(MxParser.AssignExprContext ctx) {
        Position pos = new Position(ctx.getStart());

        ExprNode lhs = (ExprNode) visit(ctx.expression(0));
        ExprNode rhs = (ExprNode) visit(ctx.expression(1));

        return new AssignExprNode(pos, lhs, rhs);
    }

    @Override
    public ASTNode visitLiteral(MxParser.LiteralContext ctx) {
        Position pos = new Position(ctx.getStart());

        if (ctx.True() != null) {
            return new BoolConstNode(pos, true);
        }
        else if (ctx.False() != null) {
            return new BoolConstNode(pos, false);
        }
        else if (ctx.StringConst() != null) {
            return new StringConstNode(pos, ctx.StringConst().getText());
        }
        else if (ctx.Null() != null) {
            return new NullConstNode(pos);
        }
        else if (ctx.IntConst() != null) {
            return new IntConstNode(pos, (int) Long.parseLong(ctx.IntConst().getText()));
        }
        else {
            throw new SemanticError(new Position(ctx.getStart()), "invalid literal");
        }
    }
}