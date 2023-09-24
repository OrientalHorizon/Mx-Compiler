package ir.type;

public class IRInt extends IRType {
    final int bits;

    public IRInt(int bits) {
        this.bits = bits;
    }

    @Override
    public int getBytes() {
        return (bits + 7) / 8;
    }

    @Override
    public String toString() {
        return "i" + bits;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof IRInt)) return false;
        return bits == ((IRInt) obj).bits;
    }
}
