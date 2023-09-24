package util.error;
import util.Position;

public class MyError extends RuntimeException {
    private Position pos;
    private String msg;

    public MyError(Position pos, String msg) {
        this.pos = pos;
        this.msg = msg;
    }

    public String toString() {
        return msg + " at " + (pos == null ? "" : pos.toString());
    }
}
