package ast;

import java.util.ArrayList;
import util.Position;

public class ArgumentListNode extends ASTNode {
    public ArrayList<ExprNode> arguments;

    public ArgumentListNode(Position pos, ArrayList<ExprNode> arguments) {
        super(pos);
        this.arguments = arguments;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
