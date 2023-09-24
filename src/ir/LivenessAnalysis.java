package ir;

import ir.instruction.*;
import ir.entity.*;

import java.util.HashSet;
import java.util.LinkedList;

public class LivenessAnalysis {
    public static void getPreSuc(IRFunction func) {
        for (IRBasicBlock block: func.blocks) {
            block.pre.clear();
            block.suc.clear();
        }

        for (IRBasicBlock block: func.blocks) {
            if (block.terminal instanceof IRJump jump) {
                block.suc.add(jump.dest);
                jump.dest.pre.add(block);
            } else if (block.terminal instanceof IRBranch branch) {
                block.suc.add(branch.thenBlock);
                block.suc.add(branch.elseBlock);
                branch.thenBlock.pre.add(block);
                branch.elseBlock.pre.add(block);
            } else if (block.terminal instanceof IRRet) {
                // do nothing
            } else {
                System.out.println("fuck you!!!!!!!!!!!!!!!1");
                throw new RuntimeException("IRBasicBlock: getPreSuc: unknown terminal instruction");
            }
        }
    }
    public static void getUseDef(IRFunction func) {
        // 注意 terminal inst.
        for (IRBasicBlock block: func.blocks) {
            block.def.clear();
            block.use.clear();
            block.liveIn.clear();
            block.liveOut.clear();
        }

        for (IRBasicBlock block: func.blocks) {
            // Prework: 处理单个指令
            for (IRInstruction ins: block.instructions) {
                ins.getUseDef();
            }
            block.terminal.getUseDef();

            // 开始正文
            for (IRInstruction ins: block.instructions) {
                for (var use: ins.use) {
                    // ??????????????????????if (!ins.def.contains(use)) {
                    if (!block.def.contains(use)) {
                        block.use.add(use);
                    }
                }
                block.def.addAll(ins.def);
            }
            for (var use: block.terminal.use) {
                if (!block.terminal.def.contains(use)) {
                    block.use.add(use);
                }
            }
            block.def.addAll(block.terminal.def);
        }
    }

    public static void analyze(IRFunction func) {
        getPreSuc(func);
        getUseDef(func);
        LinkedList<IRBasicBlock> w = new LinkedList<>();
        // Initialize w
        w.add(func.blocks.get(func.blocks.size() - 1));
        // Mem2Reg 之后最后一个块不一定是 exitBlock
        // 从出口开始往前迭代啊，according to 手册 4.3.2
        HashSet<IRBasicBlock> isinW = new HashSet<>();
        isinW.add(func.blocks.get(func.blocks.size() - 1));

        while (!w.isEmpty()) {
            // System.out.println("fuck ");
            IRBasicBlock blk = w.removeFirst();
            isinW.remove(blk);

            HashSet<IRRegister> curLiveIn = new HashSet<>(blk.use), curLiveOut = new HashSet<>();

            // Equation 4.2；在 InsSel.java 里面维护了 suc
            for (var succ: blk.suc) {
                // System.out.println("soufiy");
                curLiveOut.addAll(succ.liveIn);
            }

            // 先求出 out - def
            for (var out: curLiveOut) {
                if (!blk.def.contains(out)) {
                    curLiveIn.add(out);
                }
            }

            // 向上走；如果这一个块没变，相应地前驱也不需要进来
            if (!curLiveIn.equals(blk.liveIn) || !curLiveOut.equals(blk.liveOut)) {
                blk.liveIn = curLiveIn; blk.liveOut = curLiveOut;
                for (var pred: blk.pre) {
                    if (isinW.contains(pred)) continue;
                    w.add(pred);
                    isinW.add(pred);
                }
            }
        }
    }
}