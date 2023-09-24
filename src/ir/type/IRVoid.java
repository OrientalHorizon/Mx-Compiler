package ir.type;

public class IRVoid extends IRType {
    @Override
    public String toString() {
        return "void";
    }

    @Override
    public int getBytes() {
        return 0;
    }
}