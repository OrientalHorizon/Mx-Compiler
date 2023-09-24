package ast;
import ir.IRBasicBlock;
import util.Position;
// While LeftParen conditionExpr = expression RightParen statement
public class WhileStmtNode extends StmtNode {
    public ExprNode conditionExpr;
    public StmtNode body;

    public IRBasicBlock condBlock, bodyBlock, endBlock;
    public WhileStmtNode(Position pos, ExprNode conditionExpr, StmtNode body) {
        super(pos);
        this.conditionExpr = conditionExpr;
        this.body = body;
    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
