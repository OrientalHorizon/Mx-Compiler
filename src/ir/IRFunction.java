// TODO
package ir;
import ir.entity.*;
import ir.instruction.*;
import ir.type.*;

import java.util.ArrayList;
import java.util.LinkedList;

public class IRFunction {
    public String name;
    public IRType returnType;

    public ArrayList<IRRegister> paramList = new ArrayList<>();
    public LinkedList<IRBasicBlock> blocks = new LinkedList<>();
    public ArrayList<IRAlloca> allocas = new ArrayList<>();

    public IRBasicBlock entryBlock, exitBlock;
    public IRRegister dest;

    public IRRegister thisAddr; // 再打一层包！！！

    public IRFunction(String name, IRType returnType) {
        this.name = name;
        this.returnType = returnType;
        entryBlock = new IRBasicBlock(this, name + "_entry");
        exitBlock = new IRBasicBlock(this, name + "_exit");
        blocks.add(entryBlock);
        dest = new IRRegister(new IRPointer(returnType, 0), name + "_ret");
    }

    public void addBlock(IRBasicBlock block) {
        if (!blocks.isEmpty() && blocks.getLast().equals(block)) {
            return;
        }
        blocks.add(block);
    }

    @Override
    public String toString() {
        String ret = "";
        ret += "define " + returnType.toString() + " @" + name + "(";
        for (int i = 0; i < paramList.size(); ++i) {
            if (i != 0) ret += ", ";
            ret += paramList.get(i).toStringWithType();
        }
        ret += ") {\n";
        if (entryBlock.terminal != null || !entryBlock.instructions.isEmpty()) {
            if (!blocks.isEmpty() && !entryBlock.equals(blocks.getFirst())) {
                blocks.addFirst(entryBlock);
            }
        }
        if (exitBlock.terminal != null || !exitBlock.instructions.isEmpty()) {
            if (!blocks.isEmpty() && !exitBlock.equals(blocks.getLast())) {
                blocks.add(exitBlock);
            }
        }

        IRBasicBlock firstBlock = blocks.getFirst();
        for (int i = allocas.size() - 1; i >= 0; --i) {
            firstBlock.instructions.addFirst(allocas.get(i));
        }
        for (var block : blocks) {
            ret += block.toString();
        }
        ret += "}\n";
        return ret;
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}