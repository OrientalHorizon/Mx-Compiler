package ast;
import util.Position;

abstract public class LiteralNode extends PrimaryNode {
    public LiteralNode(Position pos) {
        super(pos, false);
    }
}
