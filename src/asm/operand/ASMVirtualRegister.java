package asm.operand;
// 函数参数大于 8 个时要用这个，把参数压到栈里传过去

public class ASMVirtualRegister extends ASMRegister {
    public static int cnt = 0;
    public int id, parameterId;
    public int size;
    public boolean isExceedingParam = false;

    public ASMVirtualRegister(int size) {
        this.id = cnt++;
        this.size = size;
    }
    public ASMVirtualRegister(int size, int parameterId) { // 原始的 index
        this.id = ++cnt;
        this.size = size;
        this.parameterId = parameterId - 8;
        this.isExceedingParam = true;
    }

    @Override
    public String toString() {
        return "%" + id;
    }
}