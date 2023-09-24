package asm.instruction;
import asm.operand.*;
import java.util.HashSet;

public abstract class ASMInstruction {
    public ASMRegister rd, rs1, rs2;
    public ASMImmediate imm;

    public abstract String toString();
}
