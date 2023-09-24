package ast;

import util.Position;

public class AssignExprNode extends ExprNode {
    public ExprNode lhs, rhs;
    public AssignExprNode(Position pos, ExprNode lhs, ExprNode rhs) {
        super(pos, false);
        this.lhs = lhs;
        this.rhs = rhs;
    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
