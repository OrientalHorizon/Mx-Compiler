package ir;
import ir.instruction.*;
import ir.IRFunction;
import ir.type.IRType;

public interface IRVisitor {
    void visit(IRProgram it);
    void visit(IRFunction it);
    void visit(IRBasicBlock it);

    void visit(IRAlloca it);
    void visit(IRBinaryOpt it);
    void visit(IRBranch it);
    void visit(IRCalc it);
    void visit(IRCall it);
    // void visit(IRFuncDecl it); // ?
    void visit(IRGetElementPtr it);
    void visit(IRJump it);
    void visit(IRLoad it);
    void visit(IRRet it);
    void visit(IRStore it);
}