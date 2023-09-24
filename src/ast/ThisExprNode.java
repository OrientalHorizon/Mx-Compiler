package ast;
import util.Position;
public class ThisExprNode extends PrimaryNode {
    public ThisExprNode(Position pos) {
        super(pos, false);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
