package ast;

import ir.type.IRType;
import util.Type;
import java.util.ArrayList;
import util.Position;

public class FuncDefNode extends ASTNode {
    public IdentifierNode funcName;
    public Type returnType;
    public ParamListNode parameters;
    public SuiteStmtNode body;
    public IRType irType;

    public FuncDefNode(Position pos, IdentifierNode funcName, Type returnType, ParamListNode parameters, SuiteStmtNode body) {
        super(pos);
        this.funcName = funcName;
        this.returnType = returnType;
        this.parameters = parameters;
        this.body = body;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
