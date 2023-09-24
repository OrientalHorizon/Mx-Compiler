package ast;

import util.Position;

import java.util.ArrayList;
import java.util.LinkedHashMap;

// classDef: Class className = Identifiers
// LeftBrace (varDef | funcDef | constructor)* RightBrace Semicolon;
public class ClassDefNode extends ASTNode {

    public IdentifierNode className;
    public ArrayList<VarDefNode> varDefs;
    public ArrayList<FuncDefNode> funcDefs;
    public ConstructorNode constructor;
    public LinkedHashMap<String, VariableNode> varMap = new LinkedHashMap<>();
    public LinkedHashMap<String, FuncDefNode> funcMap = new LinkedHashMap<>();

    public ClassDefNode(Position pos, IdentifierNode className, ArrayList<VarDefNode> varDefs, ArrayList<FuncDefNode> funcDefs, ConstructorNode constructor) {
        super(pos);
        this.className = className;
        this.varDefs = varDefs;
        this.funcDefs = funcDefs;
        this.constructor = constructor;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
