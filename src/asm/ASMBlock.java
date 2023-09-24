package asm;

import asm.operand.*;
import asm.instruction.*;
import java.util.LinkedList;
import java.util.HashSet;

public class ASMBlock {
    public String label;
    public LinkedList<ASMInstruction> instructions = new LinkedList<>();
    public int id;
    public static int cnt = 0;

    public ASMBlock(String label) {
        id = cnt++;
        this.label = label + "." + id;
    }

    public void addInstruction(ASMInstruction instruction) {
        instructions.add(instruction);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (label != null) {
            sb.append(label).append(":\n");
        }
        for (ASMInstruction instruction : instructions) {
            sb.append("\t").append(instruction.toString()).append("\n");
        }
        return sb.toString();
    }
}