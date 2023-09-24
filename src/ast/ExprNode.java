package ast;
import util.Position;
import util.Type;
import ir.entity.*;
abstract public class ExprNode extends ASTNode {
    // Type 暂时还不知道
    public Type type;
    public boolean isLeftValue = false;
    public Entity entity = null;
    public IRRegister reg = null; // 对于一个左值，一定有对应的地址能够保留它的值，把它记在这里
    public boolean isMemberFunction = false;
    public FuncDefNode funcDef = null;

    public ExprNode(Position pos, boolean isLeftValue) {
        super(pos);
        this.isLeftValue = isLeftValue;
    }
}
