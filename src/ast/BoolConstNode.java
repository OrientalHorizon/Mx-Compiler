package ast;
import util.Position;
public class BoolConstNode extends LiteralNode {
    public boolean value;
    public BoolConstNode(Position pos, boolean value) {
        super(pos);
        this.value = value;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
