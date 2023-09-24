package ir.entity;
import ir.type.*;

public class IRStringConst extends IRConst {
    public String value;
    public int id;
    public static int num = 0;

    public IRStringConst(String value) {
        super(new IRPointer(new IRInt(8), 0));
        this.value = value + "\0";
        this.id = (++num);
    }

    @Override
    public String toString() {
        return "@str." + id;
    }

    @Override
    public String toStringWithType() {
        return "[" + value.length() + " x i8]* " + toString();
    }

    public String print() {
        return "@str." + id + " = private unnamed_addr constant [" + value.length() + " x i8] c\"" + escape() + "\"\n";
    }

    public String escape() {
        return value.replace("\\", "\\\\")
                .replace("\0", "\\00")
                .replace("\n", "\\0A")
                .replace("\t", "\\09")
                .replace("\"", "\\22");
    }

    @Override
    public boolean isZero() {
        return false;
    }
    @Override
    public boolean equals(IRConst other) {
        return other instanceof IRStringConst && value.equals(((IRStringConst)other).value);
    }
}