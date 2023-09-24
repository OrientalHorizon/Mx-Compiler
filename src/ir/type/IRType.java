package ir.type;

import java.util.Objects;

abstract public class IRType {
    abstract public int getBytes();

    abstract public String toString();

    public boolean isBool() {
        if (this instanceof IRInt) {
            return ((IRInt) this).bits == 1;
        } else {
            return false;
        }
    }
    public boolean isChar() {
        if (this instanceof IRInt) {
            return ((IRInt) this).bits == 8;
        } else {
            return false;
        }
    }
    public boolean isInt() {
        if (this instanceof IRInt) {
            return ((IRInt) this).bits == 32;
        } else {
            return false;
        }
    }
//    public boolean isString() {
//        if (this instanceof IRPointer && ((IRPointer) this).baseType instanceof IRArray) {
//            IRArray array = (IRArray) ((IRPointer) this).baseType;
//            return array.baseType.isChar();
//        } else {
//            return false;
//        }
//    }
    public boolean isVoid() {
        return this instanceof IRVoid;
    }
    public boolean isNull() {
        return this instanceof IRNull;
    }
    public boolean isNullAssignable() {
        return this instanceof IRPointer || this instanceof IRStruct;
    }

    @Override
    public boolean equals(Object obj) {
        // 能不能赋值，而不是相不相等
        if (this == obj) return true;
        // TODO
        if (!(obj instanceof IRType)) return false;
        if (isNullAssignable() && ((IRType) obj).isNull()) return true;
        if (((IRType) obj).isNullAssignable() && isNull()) return true;

        if (this instanceof IRPointer) {
            if (!(obj instanceof IRPointer)) return false;
            return Objects.equals(((IRPointer) this).baseType, ((IRPointer) obj).baseType);
        }
        return Objects.equals(toString(), obj.toString());
    }

    public int hashCode() {
        return this.toString().hashCode();
    }
}