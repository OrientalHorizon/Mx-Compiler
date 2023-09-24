package asm.operand;

public class ASMRelocation extends ASMImmediate {
    public enum Type { hi, lo };
    public Type type;
    public String symbol;

    public ASMRelocation(Type type, String symbol) {
        super(0);
        this.type = type;
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return type == Type.hi ? "%hi(" + symbol + ")" : "%lo(" + symbol + ")";
    }
}