package ast;
import util.Type;
import util.Position;

import java.util.ArrayList;

public class NewExprNode extends ExprNode {
    public Type basicType;
    public ArrayList<ExprNode> exprNodes;
    public int dimension;
    public boolean isInvalid;

    public NewExprNode(Position pos, Type basicType, ArrayList<ExprNode> exprNodes, int dimension, boolean isInvalid) {
        super(pos, false);
        this.basicType = basicType;
        this.exprNodes = exprNodes;
        this.dimension = dimension;
        this.isInvalid = isInvalid;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
