package ast;
import util.Position;

public class UnaryExprNode extends ExprNode {
    public enum UnaryOpt {
        POS, NEG, BITWISE_NOT, LOGIC_NOT
    }
    public ExprNode expr;
    public UnaryOpt opt;

    public UnaryExprNode(Position pos, ExprNode expr, UnaryOpt opt) {
        super(pos, false);
        this.expr = expr;
        this.opt = opt;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
