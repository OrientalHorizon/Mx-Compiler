package ast;

import util.Position;

public class ConstructorNode extends ASTNode {
    public IdentifierNode className;
    public SuiteStmtNode suite;
    public ConstructorNode(Position pos, IdentifierNode className, SuiteStmtNode suite) {
        super(pos);
        this.className = className;
        this.suite = suite;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
