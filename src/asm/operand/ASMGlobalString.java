package asm.operand;

public class ASMGlobalString extends ASMGlobal {
    public String value;
    public int id;
    public static int cnt = 0;

    public ASMGlobalString(String label, String value) {
        super(label + "." + (++cnt), 4);
        this.id = cnt;
        this.value = value;
    }

    public String escape() {
        return value.replace("\\", "\\\\")
                .replace("\n", "\\n")
                .replace("\0", "")
                .replace("\t", "\\t")
                .replace("\"", "\\\"");
    }

    @Override
    public String toString() {
        String ret = label + ":\n";
        ret += "  .string \"" + escape() + "\"\n";

        return ret;
    }
}