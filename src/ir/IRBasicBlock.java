package ir;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.ArrayList;

import asm.ASMBlock;
import asm.operand.ASMRegister;
import ir.entity.IRRegister;
import ir.instruction.*;
import util.Position;

public class IRBasicBlock {
    public String label;
    public LinkedList<IRInstruction> instructions = new LinkedList<>();
    public IRInstruction terminal = null;
    public IRFunction parentFunction = null;
    public boolean finished = false;
    public static int cnt = 0;
    public int id;

    public HashSet<IRRegister> liveIn = new HashSet<>(), liveOut = new HashSet<>(), def = new HashSet<>(), use = new HashSet<>();
    public LinkedList<IRBasicBlock> pre = new LinkedList<>(), suc = new LinkedList<>();

    public IRBasicBlock(IRFunction parent, String label) {
        id = ++cnt;
        this.label = label + "_" + id;
        this.parentFunction = parent;
    }

    public String toString() {
        String ret = label + ":\n";
        for (var inst : instructions) {
            ret += "\t\t" + inst.toString() + "\n";
        }
        if (terminal != null) {
            ret += "  " + terminal.toString() + "\n";
        }
        return ret;
    }

    public void addInstruction(IRInstruction inst) {
        if (finished) {
            return;
        }
        instructions.add(inst);
    }

    public void addTerminal(IRInstruction terminal) {
        if (finished) {
            return;
        }
        this.terminal = terminal;
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}