package ast;
import util.Position;

import java.util.ArrayList;

// program: (def)* EOF;
public class ProgramNode extends ASTNode {
    public ArrayList<ASTNode> defNodes;

    public ProgramNode(Position pos, ArrayList<ASTNode> defNodes) {
        super(pos);
        this.defNodes = defNodes;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
