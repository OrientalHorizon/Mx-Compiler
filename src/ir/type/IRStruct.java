package ir.type;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class IRStruct extends IRType {
    public String name;
    public LinkedHashMap<String, IRType> memberType = new LinkedHashMap<>();
    public LinkedHashMap<String, Integer> memberIndex = new LinkedHashMap<>();
    public ArrayList<IRType> typeArray = new ArrayList<>();
    public int memberNum;

    public boolean hasConstructor = false;

    public IRStruct(String name, int memberNum) {
        this.name = name;
        this.memberNum = memberNum;
    }

    @Override
    public int getBytes() {
        return memberType.size() * 4;
    }
    @Override
    public String toString() {
        return "%struct." + this.name;
    }
}