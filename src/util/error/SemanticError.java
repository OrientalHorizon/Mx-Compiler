package util.error;
import util.Position;

public class SemanticError extends MyError {
    public SemanticError(Position pos, String msg) {
        super(pos, "Semantic error: " + msg);
    }
}
