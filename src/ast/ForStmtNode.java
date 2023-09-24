package ast;
import ir.IRBasicBlock;
import util.Position;
/*
For   LeftParen initStmt = forInitStatement // varDef 有分号
            conditionExpr = expression? Semicolon
            incExpr = expression? RightParen
            statement
 */
public class ForStmtNode extends StmtNode {
    public StmtNode body;
    public ExprNode conditionExpr, incExpr;
    public ASTNode initStmt; // 不确定是 expression 还是 statement

    public IRBasicBlock condBlock, bodyBlock, stepBlock, endBlock;
    public ForStmtNode(Position pos, ASTNode initStmt, ExprNode conditionExpr, ExprNode incExpr, StmtNode body) {
        super(pos);
        this.initStmt = initStmt;
        this.conditionExpr = conditionExpr;
        this.incExpr = incExpr;
        this.body = body;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
