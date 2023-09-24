package asm;

import asm.operand.*;
import asm.instruction.*;

import java.util.ArrayList;

public class ASMFunction {
    public String label;
    public ArrayList<ASMBlock> blocks = new ArrayList<>();
    public ASMRegister returnValue;
    public ArrayList<ASMRegister> parameters = new ArrayList<>();

    // 如果参数大于 8 个，需要开栈存参数：
    public int virtualRegCnt = 0, virtualRegBytes = 0;
    public int exceedingParamBytes = 0, allocaBytes = 4, totalStackSize;
    // 虚拟寄存器就从 virtualRegBegin ( = exc. param bytes + allocaBytes) 开始分配

    public ASMFunction(String label) {
        this.label = label;
    }

    public void add(ASMBlock block) {
        blocks.add(block);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" .text\n .globl ").append(label).append("\n");
        sb.append(label).append(":\n");
        for (ASMBlock block : blocks) {
            sb.append(block.toString());
        }
        return sb.toString();
    }
}