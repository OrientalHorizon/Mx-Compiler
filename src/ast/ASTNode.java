package ast;

import util.Position;

abstract public class ASTNode {
    public Position pos;

    public ASTNode(Position pos) {
        this.pos = pos;
    }

    abstract public void accept(ASTVisitor visitor);
}