package ast;
import util.Position;

public class SubscriptExprNode extends ExprNode {
    public ExprNode array, subscript;

    public SubscriptExprNode(Position pos, ExprNode array, ExprNode subscript) {
        super(pos, true);
        this.array = array;
        this.subscript = subscript;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
