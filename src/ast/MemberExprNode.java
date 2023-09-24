package ast;
import util.Position;
public class MemberExprNode extends ExprNode {
    public ExprNode expr;
    public IdentifierNode member;

    public MemberExprNode(Position pos, ExprNode expr, IdentifierNode member) {
        super(pos, true);
        this.expr = expr;
        this.member = member;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
