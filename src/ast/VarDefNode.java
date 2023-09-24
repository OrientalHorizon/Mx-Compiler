package ast;
import util.Position;

import java.util.ArrayList;

// varDef: type singleVarDef (Comma singleVarDef)* Semicolon;
// singleVarDef: varName = Identifiers (Assign expression)?;
public class VarDefNode extends StmtNode {
    public ArrayList<VariableNode> variables;

    public VarDefNode(Position pos, ArrayList<VariableNode> variables) {
        super(pos);
        this.variables = variables;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
