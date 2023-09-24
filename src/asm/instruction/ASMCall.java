package asm.instruction;

public class ASMCall extends ASMInstruction {
    String funcName;

    public ASMCall(String funcName) {
        this.funcName = funcName;
    }

    @Override
    public String toString() {
        return "\tcall " + funcName;
    }
}