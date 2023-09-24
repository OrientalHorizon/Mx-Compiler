package ast;
import util.Position;

public class TernaryExprNode extends ExprNode {
    public ExprNode cond, thenExpr, elseExpr;

    public TernaryExprNode(Position pos, ExprNode cond, ExprNode thenExpr, ExprNode elseExpr) {
        super(pos, false);
        this.cond = cond;
        this.thenExpr = thenExpr;
        this.elseExpr = elseExpr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
