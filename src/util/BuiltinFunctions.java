package util;

import ast.*;

import java.util.ArrayList;

public class BuiltinFunctions {
    private final Type myVoid = new Type("void");
    private final Type myInt = new Type("int");
    private final Type myBool = new Type("bool");
    private final Type myString = new Type("string");
    private final Type myNull = new Type("null");

    private ParamListNode emptyParamList = new ParamListNode(null, new ArrayList<>());
    private ParamListNode intParamList = new ParamListNode(null, new ArrayList<>() {{
        add(new VariableNode(null, myInt, IdentifierNode.getVariableIdentifier(null, "n"), null));
    }});
    private ParamListNode intParamListI = new ParamListNode(null, new ArrayList<>() {{
        add(new VariableNode(null, myInt, IdentifierNode.getVariableIdentifier(null, "i"), null));
    }});
    private ParamListNode stringParamList = new ParamListNode(null, new ArrayList<>() {{
        add(new VariableNode(null, myString, IdentifierNode.getVariableIdentifier(null, "str"), null));
    }});

    public FuncDefNode getPrint() {
        return new FuncDefNode(null, IdentifierNode.getFuncIdentifier(null, "print"), myVoid, stringParamList, null);
    }
    public FuncDefNode getPrintln() {
        return new FuncDefNode(null, IdentifierNode.getFuncIdentifier(null, "println"), myVoid, stringParamList, null);
    }
    public FuncDefNode getPrintInt() {
        return new FuncDefNode(null, IdentifierNode.getFuncIdentifier(null, "printInt"), myVoid, intParamList, null);
    }
    public FuncDefNode getPrintlnInt() {
        return new FuncDefNode(null, IdentifierNode.getFuncIdentifier(null, "printlnInt"), myVoid, intParamList, null);
    }

    public FuncDefNode getGetString() {
        return new FuncDefNode(null, IdentifierNode.getFuncIdentifier(null, "getString"), myString, emptyParamList, null);
    }
    public FuncDefNode getGetInt() {
        return new FuncDefNode(null, IdentifierNode.getFuncIdentifier(null, "getInt"), myInt, emptyParamList, null);
    }
    public FuncDefNode getToString() {
        return new FuncDefNode(null, IdentifierNode.getFuncIdentifier(null, "toString"), myString, intParamListI, null);
    }

    // 内建方法
    // 数组的 size
    public FuncDefNode getSize() {
        return new FuncDefNode(null, IdentifierNode.getFuncIdentifier(null, "size"), myInt, emptyParamList, null);
    }

    // 字符串的 length
    public FuncDefNode getLength() {
        return new FuncDefNode(null, IdentifierNode.getFuncIdentifier(null, "length"), myInt, emptyParamList, null);
    }
    // 字符串的 substring
    private final ParamListNode substringParamList = new ParamListNode(null, new ArrayList<>() {{
        add(new VariableNode(null, myInt, IdentifierNode.getVariableIdentifier(null, "left"), null));
        add(new VariableNode(null, myInt, IdentifierNode.getVariableIdentifier(null, "right"), null));
    }});
    public FuncDefNode getSubstring() {
        return new FuncDefNode(null, IdentifierNode.getFuncIdentifier(null, "substring"), myString, substringParamList, null);
    }

    public FuncDefNode getParseInt() {
        return new FuncDefNode(null, IdentifierNode.getFuncIdentifier(null, "parseInt"), myInt, emptyParamList, null);
    }

    private ParamListNode ordParamList = new ParamListNode(null, new ArrayList<>() {{
        add(new VariableNode(null, myInt, IdentifierNode.getVariableIdentifier(null, "pos"), null));
    }});
    public FuncDefNode getOrd() {
        return new FuncDefNode(null, IdentifierNode.getFuncIdentifier(null, "ord"), myInt, ordParamList, null);
    }

    public ClassDefNode getIntClass() {
        return new ClassDefNode(null, IdentifierNode.getTypeIdentifier(null, "int"), null, null, null);
    }
    public ClassDefNode getBoolClass() {
        return new ClassDefNode(null, IdentifierNode.getTypeIdentifier(null, "bool"), null, null, null);
    }
    public ClassDefNode getStringClass() {
        // funcmap 是在 symbolcollector上添加的，所以这里把它加上去
        ClassDefNode ret = new ClassDefNode(null, IdentifierNode.getTypeIdentifier(null, "string"), null, new ArrayList<>() {{
            add(getLength());
            add(getSubstring());
            add(getParseInt());
            add(getOrd());
        }}, null);
        ret.funcMap.put("length", getLength());
        ret.funcMap.put("substring", getSubstring());
        ret.funcMap.put("parseInt", getParseInt());
        ret.funcMap.put("ord", getOrd());
        return ret;
    }

}