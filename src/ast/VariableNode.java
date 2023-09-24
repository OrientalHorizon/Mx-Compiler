package ast;
import util.Position;
import util.Type;
public class VariableNode extends ASTNode {
    public Type type;
    public IdentifierNode name;
    public ExprNode init;

    public VariableNode(Position pos, Type type, IdentifierNode name, ExprNode init) {
        super(pos);
        this.type = type;
        this.name = name;
        this.init = init;
    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
