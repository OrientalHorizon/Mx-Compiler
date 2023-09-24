package ast;

import util.Position;

import java.util.ArrayList;

public class FuncCallExprNode extends ExprNode {
    public IdentifierNode func;
    public ArgumentListNode args;

    public FuncCallExprNode(Position pos, IdentifierNode func, ArgumentListNode args) {
        super(func.pos, false);
        this.func = func;
        this.args = args;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
