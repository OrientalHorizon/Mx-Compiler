package ast;

import util.Position;

public class BinaryExprNode extends ExprNode {
    public enum BinaryOpt {
        MUL, DIV, MOD, ADD, SUB, SHL, SHR, LT, GT, LE, GE, EQ, NE, BIT_AND, BIT_XOR, BIT_OR, LOG_AND, LOG_OR
    }
    public ExprNode lhs, rhs;
    public BinaryOpt opt;

    public BinaryExprNode(Position pos, ExprNode lhs, ExprNode rhs, BinaryOpt opt) {
        super(lhs.pos, false);
        this.lhs = lhs;
        this.rhs = rhs;
        this.opt = opt;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
