package ast;

import grammar.MxParser;

public interface ASTVisitor {
    // program
    void visit(ProgramNode it);

    // def
    void visit(ClassDefNode it);
    void visit(FuncDefNode it);
    void visit(VarDefNode it);
    void visit(ConstructorNode it);

    // statement
    void visit(SuiteStmtNode it);
    void visit(IfStmtNode it);
    void visit(WhileStmtNode it);
    void visit(ForStmtNode it);
    void visit(ReturnStmtNode it);
    void visit(BreakStmtNode it);
    void visit(ContinueStmtNode it);
    void visit(ExprStmtNode it);
    void visit(EmptyStmtNode it);

    // expression
    void visit(SuffixExprNode it);
    void visit(MemberExprNode it);
    void visit(MethodExprNode it);
    void visit(FuncCallExprNode it);
    void visit(SubscriptExprNode it);
    void visit(PrefixExprNode it);
    void visit(UnaryExprNode it);
    void visit(NewExprNode it);
    void visit(BinaryExprNode it);
    void visit(TernaryExprNode it);
    void visit(AssignExprNode it);

    // primary
    void visit(ParenExprNode it);
    void visit(ThisExprNode it);

    // literal
    void visit(IntConstNode it);
    void visit(StringConstNode it);
    void visit(BoolConstNode it);
    void visit(NullConstNode it);
    void visit(IdentifierNode it);

    // variable
    void visit(VariableNode it);

    void visit(ArgumentListNode it);

    void visit(ParamListNode it);
}
