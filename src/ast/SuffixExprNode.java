package ast;
import util.Position;

public class SuffixExprNode extends ExprNode {
    public enum SuffixOpt {
        SELF_INC, SELF_DEC,
    }
    public ExprNode expr;
    public SuffixOpt opt;

    public SuffixExprNode(Position pos, ExprNode expr, SuffixOpt opt) {
        super(pos, false);
        this.expr = expr;
        this.opt = opt;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
