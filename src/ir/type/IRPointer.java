package ir.type;
// TODO: Rework，数组和指针二合一，指针干脆就带 dimension
public class IRPointer extends IRType {
    public IRType baseType;
    public int dimension;

    public IRPointer(IRType baseType, int dimension) {
        if (this.baseType instanceof IRPointer) {
            IRType realBaseType = ((IRPointer) baseType).baseType;
            this.baseType = realBaseType;
            this.dimension = ((IRPointer) baseType).dimension + dimension;
        }
        else {
            this.baseType = baseType;
            this.dimension = dimension;
        }
    }
    public IRPointer(IRType baseType) {
        this(baseType, 1);
    }

    @Override
    public int getBytes() {
        return 4; // 32-bits
    }

    @Override
    public String toString() {
        return "ptr";
    }

    @Override
    public boolean equals(Object obj) {
//        if (obj instanceof IRPointer) {
//            IRPointer other = (IRPointer)obj;
//            return baseType.equals(other.baseType) && dimension == other.dimension;
//        }
        return false;
    }

    public IRType Subscript() {
        assert(dimension >= 1);
        if (dimension == 1) {
            return baseType;
        }
        else {
            return new IRPointer(baseType, dimension - 1);
        }
    }
}