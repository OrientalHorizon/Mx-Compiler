package ast;
import util.Position;
public class StringConstNode extends LiteralNode {
    public String value;
    // WITHOUT QUOTES!!!!!!!!!!!!!
    public StringConstNode(Position pos, String value) {
        super(pos);
        this.value = value;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
