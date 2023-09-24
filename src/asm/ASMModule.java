package asm;

import asm.operand.*;
import java.util.ArrayList;

public class ASMModule {
    public ArrayList<ASMGlobalValue> globalValues = new ArrayList<>();
    public ArrayList<ASMFunction> functions = new ArrayList<>();
    public ArrayList<ASMGlobalString> strings = new ArrayList<>();

    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (globalValues.size() > 0) {
            sb.append(" .section .data\n");
        }
        for (ASMGlobalValue globalValue : globalValues) {
            sb.append(globalValue.toString()).append("\n");
        }

        if (strings.size() > 0) {
            sb.append(" .section .rodata\n");
        }
        for (ASMGlobalString string : strings) {
            sb.append(string.toString()).append("\n");
        }

        for (ASMFunction function : functions) {
            sb.append(function.toString()).append("\n");
        }
        return sb.toString();
    }
}