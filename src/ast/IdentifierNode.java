package ast;
import util.Position;
public class IdentifierNode extends PrimaryNode {
    public String name;

    public IdentifierNode(Position pos, String name, boolean isVariableName) {
        super(pos, isVariableName);
        this.name = name;
    }
    public static IdentifierNode getVariableIdentifier(Position pos, String name) {
        return new IdentifierNode(pos, name, true);
    }
    public static IdentifierNode getTypeIdentifier(Position pos, String name) {
        return new IdentifierNode(pos, name, false);
    }
    public static IdentifierNode getFuncIdentifier(Position pos, String name) {
        return new IdentifierNode(pos, name, false);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IdentifierNode) {
            return name.equals(((IdentifierNode) obj).name);
        } else {
            return false;
        }
    }
}
