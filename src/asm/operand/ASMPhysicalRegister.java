package asm.operand;

import java.util.LinkedHashMap;

public class ASMPhysicalRegister extends ASMRegister {
    public static LinkedHashMap<String, ASMPhysicalRegister> pRegMap = new LinkedHashMap<>() {
        // registers in RISC-V 32-bits
        {
            put("zero", new ASMPhysicalRegister("zero"));
            put("ra", new ASMPhysicalRegister("ra"));
            put("sp", new ASMPhysicalRegister("sp"));
            put("gp", new ASMPhysicalRegister("gp"));
            put("tp", new ASMPhysicalRegister("tp"));
            put("t0", new ASMPhysicalRegister("t0"));
            put("t1", new ASMPhysicalRegister("t1"));
            put("t2", new ASMPhysicalRegister("t2"));
            put("s0", new ASMPhysicalRegister("s0"));
            put("s1", new ASMPhysicalRegister("s1"));
            put("a0", new ASMPhysicalRegister("a0"));
            put("a1", new ASMPhysicalRegister("a1"));
            put("a2", new ASMPhysicalRegister("a2"));
            put("a3", new ASMPhysicalRegister("a3"));
            put("a4", new ASMPhysicalRegister("a4"));
            put("a5", new ASMPhysicalRegister("a5"));
            put("a6", new ASMPhysicalRegister("a6"));
            put("a7", new ASMPhysicalRegister("a7"));
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
            put("t3", new ASMPhysicalRegister("t3"));
            put("t4", new ASMPhysicalRegister("t4"));
            put("t5", new ASMPhysicalRegister("t5"));
            put("t6", new ASMPhysicalRegister("t6"));
        }
    };

    public String label;

    public ASMPhysicalRegister(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ASMPhysicalRegister) {
            return label.equals(((ASMPhysicalRegister) obj).label);
        }
        return false;
    }
}