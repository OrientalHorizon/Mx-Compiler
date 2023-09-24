package ast;
import util.Position;

abstract public class PrimaryNode extends ExprNode {
    public PrimaryNode(Position pos, boolean isLeftValue) {
        super(pos, isLeftValue);
    }
}
