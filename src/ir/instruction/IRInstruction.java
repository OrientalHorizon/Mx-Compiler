package ir.instruction;

import asm.operand.ASMRegister;
import ir.*;
import ir.entity.IRRegister;

import java.util.HashSet;

public abstract class IRInstruction {
    public IRBasicBlock parentBlock;
    public abstract String toString();

    public IRInstruction(IRBasicBlock parentBlock) {
        this.parentBlock = parentBlock;
    }

    public abstract void accept(IRVisitor visitor);

    public HashSet<IRRegister> use = new HashSet<>(), def = new HashSet<>();
    abstract public void getUseDef();
}