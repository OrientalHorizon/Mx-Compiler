package ir.type;

public class IRNull extends IRType {
    @Override
    public String toString() {
        return "null";
    }

    @Override
    public int getBytes() {
        return 0;
    }
}