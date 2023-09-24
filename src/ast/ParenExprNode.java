package ast;
import util.Position;
public class ParenExprNode extends PrimaryNode {
    public ExprNode expr;

    public ParenExprNode(Position pos, ExprNode expr) {
        super(pos, false);
        this.expr = expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
