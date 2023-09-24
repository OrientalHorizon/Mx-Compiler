package ast;
import util.Position;

public class PrefixExprNode extends ExprNode {
    public enum PrefixOpt {
        SELF_INC, SELF_DEC,
    }
    public ExprNode expr;
    public PrefixOpt opt;

    public PrefixExprNode(Position pos, ExprNode expr, PrefixOpt opt) {
        super(pos, true);
        this.expr = expr;
        this.opt = opt;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
