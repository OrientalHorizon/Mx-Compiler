package util.error;
import util.Position;
public class SyntaxError extends MyError {
    public SyntaxError(Position pos, String msg) {
        super(pos, "Syntax error: " + msg);
    }
}
