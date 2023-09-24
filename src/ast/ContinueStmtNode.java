package ast;
import util.Position;

public class ContinueStmtNode extends StmtNode {
    public ContinueStmtNode(Position pos) {
        super(pos);
    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
