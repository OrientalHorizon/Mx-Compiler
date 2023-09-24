package ast;

import util.Position;

public class ReturnStmtNode extends StmtNode {
    public ExprNode expr;

    public ReturnStmtNode(Position pos, ExprNode expr) {
        super(pos);
        this.expr = expr;
    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
