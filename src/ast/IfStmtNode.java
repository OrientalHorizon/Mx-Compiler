package ast;
import util.Position;

// If LeftParen expression RightParen trueStmt = statement (Else falseStmt = statement)?
public class IfStmtNode extends StmtNode {
    public ExprNode condition;
    public StmtNode thenStmt, elseStmt;

    public IfStmtNode(Position pos, ExprNode condition, StmtNode thenStmt, StmtNode elseStmt) {
        super(pos);
        this.condition = condition;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
