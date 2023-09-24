package ast;
import java.util.ArrayList;
import util.Position;

public class ParamListNode extends ASTNode {
    public ArrayList<VariableNode> paramDef;
    public ParamListNode(Position pos, ArrayList<VariableNode> paramDef) {
        super(pos);
        this.paramDef = paramDef;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
