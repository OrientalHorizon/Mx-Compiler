package ast;
import util.Position;
public class NullConstNode extends LiteralNode {
    public NullConstNode(Position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
