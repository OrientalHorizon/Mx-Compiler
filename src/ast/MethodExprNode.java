package ast;
import util.Position;

public class MethodExprNode extends ExprNode {
    public ExprNode expr;
    public IdentifierNode method;
    public ArgumentListNode args;
    // expr.method.(args)

    public MethodExprNode(Position pos, ExprNode expr, IdentifierNode method, ArgumentListNode args) {
        super(pos, false);
        this.expr = expr;
        this.method = method;
        this.args = args;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
