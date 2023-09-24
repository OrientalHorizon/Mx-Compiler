package ast;
import util.Position;
import java.util.ArrayList;

public class SuiteStmtNode extends StmtNode {
    public ArrayList<StmtNode> stmts;

    public SuiteStmtNode(Position pos, ArrayList<StmtNode> stmts) {
        super(pos);
        this.stmts = stmts;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
