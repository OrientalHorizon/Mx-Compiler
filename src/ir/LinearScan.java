package ir;

import java.util.*;

import asm.IRRegisterMap;
import asm.RegisterAllocator;
import asm.operand.*;
import ir.instruction.*;
import ir.entity.*;

public class LinearScan {
    // 先搞一个寄存器 HashSet，把所有能用的寄存器存下来
    final LinkedHashMap<String, ASMPhysicalRegister> callerSave = new LinkedHashMap<>() {
        { // t0 和 t1 倒出来
            put("t2", new ASMPhysicalRegister("t2"));
            put("t3", new ASMPhysicalRegister("t3"));
            put("t4", new ASMPhysicalRegister("t4"));
            put("t5", new ASMPhysicalRegister("t5"));
            put("t6", new ASMPhysicalRegister("t6"));
            put("a0", new ASMPhysicalRegister("a0"));
            put("a1", new ASMPhysicalRegister("a1"));
            put("a2", new ASMPhysicalRegister("a2"));
            put("a3", new ASMPhysicalRegister("a3"));
            put("a4", new ASMPhysicalRegister("a4"));
            put("a5", new ASMPhysicalRegister("a5"));
            put("a6", new ASMPhysicalRegister("a6"));
            put("a7", new ASMPhysicalRegister("a7"));
        }
    };
    final LinkedHashMap<String, ASMPhysicalRegister> calleeSave = new LinkedHashMap<>() {
        // s0 through s11
        {
            put("s0", new ASMPhysicalRegister("s0"));
            put("s1", new ASMPhysicalRegister("s1"));
            put("s2", new ASMPhysicalRegister("s2"));
            put("s3", new ASMPhysicalRegister("s3"));
            put("s4", new ASMPhysicalRegister("s4"));
            put("s5", new ASMPhysicalRegister("s5"));
            put("s6", new ASMPhysicalRegister("s6"));
            put("s7", new ASMPhysicalRegister("s7"));
            put("s8", new ASMPhysicalRegister("s8"));
            put("s9", new ASMPhysicalRegister("s9"));
            put("s10", new ASMPhysicalRegister("s10"));
            put("s11", new ASMPhysicalRegister("s11"));
        }
    };

    IRFunction curFunc;
    private TreeSet<VariableLiveness> activeCaller = new TreeSet<>(new CompareEnd());
    private TreeSet<VariableLiveness> activeCallee = new TreeSet<>(new CompareEnd());
    HashSet<ASMPhysicalRegister> remainingCaller = new HashSet<>(), remainingCallee = new HashSet<>();
    ArrayList<IRBasicBlock> reversePostOrder = new ArrayList<>();
    HashSet<IRBasicBlock> inRPO = new HashSet<>();
    LinkedHashMap<IRRegister, VariableLiveness> livenessMap = new LinkedHashMap<>();
    private ArrayList<Integer> callIndex = new ArrayList<>();
    private TreeSet<VariableLiveness> livenessSet = new TreeSet<>(new CompareBegin());

    public class VariableLiveness {
        public int begin = 2147483647, end = -1;
        public IRRegister original;
        public ASMRegister reg;
        public boolean throughCallInst = false;
        // 如果经过 call，那么 caller saved registers 可能被占，不能分配
        public VariableLiveness(int begin, int end, IRRegister reg) {
            this.begin = begin;
            this.end = end;
            this.original = reg;
        }
    }

    public class CompareBegin implements Comparator<VariableLiveness> {
        @Override
        public int compare(VariableLiveness a, VariableLiveness b) {
            return a.begin - b.begin;
        }
    }

    public class CompareEnd implements Comparator<VariableLiveness> {
        @Override
        public int compare(VariableLiveness a, VariableLiveness b) {
            return a.end - b.end;
        }
    }

    public LinearScan(IRFunction func) {
        this.curFunc = func;
        LinearScanRegisterAllocation();
        IRRegisterMap.irRegisterMap = new LinkedHashMap<>();
        LinearScanRegisterAllocation();
        for (var interval: livenessSet) {
            // fuck s5
//            if (Objects.equals(((ASMPhysicalRegister) interval.reg).label, "s5")) {
//                System.err.println(interval.begin + " " + interval.end);
//            }
            interval.original.asmRegister = interval.reg;
        }
    }
    public void dfs(IRBasicBlock curBlk) {
        inRPO.add(curBlk);
        for (var succ: curBlk.suc) {
            if (!inRPO.contains(succ)) {
//                System.err.println(curBlk.label + " " + succ.label);
                // inRPO.add(succ); // succ 可能连了一通连到自己，导致无限递归
                dfs(succ);
            }
        }
        reversePostOrder.add(curBlk);
    }
    public void getRPO() {
        // TODO
        dfs(curFunc.blocks.getFirst());
        Collections.reverse(reversePostOrder);
//        System.err.println("fuck that shit");
    }

    public void setEnd(VariableLiveness liveness, int idx) {
        liveness.end = Math.max(liveness.end, idx);
    }

    public void init() {
        LivenessAnalysis.analyze(curFunc);
        // 先扫一遍，获得生命周期信息
        // Reverse post order
        getRPO();
        // 从前往后扫一遍，查生命周期
        int idx = 0; // 指令编号，用于表示记录生命周期起终点
        for (var arg: curFunc.paramList) {
            assert(arg != null);
            VariableLiveness liveness = new VariableLiveness(idx, -1, arg);
            livenessMap.put(arg, liveness);
        }
        for (IRBasicBlock blk: reversePostOrder) {
            for (IRInstruction ins: blk.instructions) {
                // System.out.println(ins.toString());
                if (ins instanceof IRCall) {
                    // System.out.println(ins.toString());
                    // System.out.println(idx);
                    callIndex.add(idx);
                }
                // 第一次出现的变量一定是 def
                if (!ins.def.isEmpty()) {
                    for (var def: ins.def) {
                        assert(def != null);
                        VariableLiveness liveness = null;
                        if (livenessMap.containsKey(def)) {
                            liveness = livenessMap.get(def);
                            liveness.begin = Math.min(liveness.begin, idx);
                        }
                        else {
                            liveness = new VariableLiveness(idx, -1, def);
                            livenessMap.put(def, liveness);
                        }
                    }
                }
                if (!ins.use.isEmpty()) {
                    for (var use: ins.use) {
                        VariableLiveness liveness = livenessMap.get(use);
                        setEnd(liveness, idx);
                    }
                }
                ++idx;
            }
            HashSet<IRRegister> terminalUse = blk.terminal.use;
            for (var use: terminalUse) {
                VariableLiveness liveness = livenessMap.get(use);
                setEnd(liveness, idx);
            }
            ++idx;

            // 别把 liveout 忘了！！要不然分析生命周期干啥
            for (var liveOut: blk.liveOut) {
                // System.err.println(liveOut.name + "_" + liveOut.id);
                VariableLiveness liveness = livenessMap.get(liveOut);
                // 找不到怎么办！稳啦！都稳啦！
                if (liveness != null)
                    setEnd(liveness, idx);
            }
        }

        // TODO: 穿过 call 指令怎么判断？
        livenessSet.addAll(livenessMap.values());
        for (var liveness: livenessSet) {
            // boolean flag = false;
            assert(liveness.original != null);
//            if (Objects.equals(liveness.original.name, "tmp_subscript") && liveness.original.id == 10) {
//                System.err.println(liveness.begin + " " + liveness.end);
//                flag = true;
//            }
            // ????????????????????????????????????????????????for (int i = 0; i < callIndex.size() - 1; ++i) { 我tm在干什么？
            for (int i = 0; i < callIndex.size(); ++i) {
                if (liveness.begin < callIndex.get(i) && liveness.end >= callIndex.get(i)) {
                    liveness.throughCallInst = true;
                    break;
                }
            }
        }
    }

    public void LinearScanRegisterAllocation() {
        // init
        remainingCaller.clear();
        remainingCallee.clear();
        remainingCaller.addAll(callerSave.values());
        remainingCallee.addAll(calleeSave.values());
        activeCaller.clear();
        activeCallee.clear();
        reversePostOrder.clear();
        inRPO.clear();
        livenessMap.clear();
        callIndex.clear();
        livenessSet.clear();
        this.init();

        // 开始分配
        for (var interval: livenessSet) {
            ExpireOldIntervals(interval);
            if (interval.throughCallInst) {
                // 只分配 callee saved registers
                if (remainingCallee.isEmpty()) {
                    CalleeSpill(interval);
                }
                else {
                    interval.reg = remainingCallee.iterator().next();
                    remainingCallee.remove(interval.reg);
                    activeCallee.add(interval);
                }
            }

            else {
                // 先分配 caller saved registers
                if (!remainingCaller.isEmpty()) {
                    interval.reg = remainingCaller.iterator().next();
                    remainingCaller.remove(interval.reg);
                    activeCaller.add(interval);
                }
                else if (!remainingCallee.isEmpty()) {
                    interval.reg = remainingCallee.iterator().next();
                    remainingCallee.remove(interval.reg);
                    activeCallee.add(interval);
                }
                else {
                    // 随便挑谁折磨吧
                    CallerSpill(interval);
                }
            }
        }
    }

    void ExpireOldIntervals(VariableLiveness interval) {
        for (var j = activeCaller.iterator(); j.hasNext();) {
            var it = j.next();
            if (it.end >= interval.begin) {
                return;
            }
            remainingCaller.add((ASMPhysicalRegister) it.reg);
            j.remove();
        }
        for (var j = activeCallee.iterator(); j.hasNext();) {
            var it = j.next();
            if (it.end >= interval.begin) {
                return;
            }
            remainingCallee.add((ASMPhysicalRegister) it.reg);
            j.remove();
        }
    }

    void CalleeSpill(VariableLiveness interval) {
        // TODO
        VariableLiveness spill = activeCallee.last();
        if (spill.end > interval.end) {
            interval.reg = spill.reg;
            spill.reg = null;
            activeCallee.remove(spill);
            activeCallee.add(interval);
            // 栈的分配到时候再说
        }
        else {
            interval.reg = null;
        }
    }
    void CallerSpill(VariableLiveness interval) {
        // TODO
        VariableLiveness spill = activeCaller.last();
        if (spill.end > interval.end) {
            interval.reg = spill.reg;
            spill.reg = null;
            activeCaller.remove(spill);
            activeCaller.add(interval);
            // 栈的分配到时候再说
        }
        else {
            interval.reg = null;
        }
    }
}