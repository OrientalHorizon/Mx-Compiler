package util.error;
import util.Position;

public class CodegenError extends MyError {
    public CodegenError(Position pos, String msg) {
        super(pos, "Codegen error: " + msg);
    }
}