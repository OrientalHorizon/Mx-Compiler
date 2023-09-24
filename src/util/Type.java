// Todo: rework type

package util;

import java.util.Objects;

public class Type {
    public String basicTypeName;
    public boolean basicIsClassName = false;
    public boolean isReference = false;
    public int dimension = 0;

    private boolean setClassName(String basicTypeName) {
        return !Objects.equals(basicTypeName, "int")
                && !Objects.equals(basicTypeName, "bool")
                && !Objects.equals(basicTypeName, "string")
                && !Objects.equals(basicTypeName, "void")
                && !Objects.equals(basicTypeName, "null")
                && !Objects.equals(basicTypeName, "this");
    }

    public Type(String typeName) {
        int bracketNum = 0;
        int last = typeName.length();
        for (int i = typeName.length() - 1; i >= 0; --i) {
            if (typeName.charAt(i) == '[') {
                ++bracketNum;
                last = i;
            }
        }
        this.dimension = bracketNum;
        if (this.dimension >= 1) {
            this.isReference = true;
        }
        this.basicTypeName = typeName.substring(0, last); // 不包括
        this.basicIsClassName = setClassName(basicTypeName);
        if (this.basicIsClassName) {
            this.isReference = true;
        }
    }

    public Type(String basicTypeName, int dimension) {
        this.basicTypeName = basicTypeName;
        this.dimension = dimension;
        this.isReference = this.basicIsClassName = setClassName(basicTypeName);
        if (this.dimension >= 1) {
            this.isReference = true;
        }
    }

    public Type(Type others) {
        this.basicTypeName = others.basicTypeName;
        this.basicIsClassName = others.basicIsClassName;
        this.isReference = others.isReference;
        this.dimension = others.dimension;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Type other) {
            return Objects.equals(this.basicTypeName, other.basicTypeName)
                    && this.dimension == other.dimension
                    && this.isReference == other.isReference;
        }
        else {
            return false;
        }
    }
}