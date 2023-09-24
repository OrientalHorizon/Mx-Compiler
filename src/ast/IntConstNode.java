package ast;
import util.Position;

public class IntConstNode extends LiteralNode {
    public int value;
    public IntConstNode(Position pos, int value) {
        super(pos);
        this.value = value;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
