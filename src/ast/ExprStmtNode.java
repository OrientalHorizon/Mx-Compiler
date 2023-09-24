package ast;
import util.Position;

public class ExprStmtNode extends StmtNode {
    public ExprNode expr;

    public ExprStmtNode(Position pos, ExprNode expr) {
        super(pos);
        this.expr = expr;
    }

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
