package asm.operand;

public abstract class ASMGlobal extends ASMRegister {
    public String label;
    public int bytes;

    public ASMGlobal(String label, int bytes) {
        this.label = label;
        this.bytes = bytes;
    }
}