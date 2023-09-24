
import asm.ASMModule;
import asm.InstructionSelector;
import asm.RegisterAllocator;
import ast.*;
import ir.IRBuilder;
import ir.IRProgram;
import org.antlr.v4.runtime.CharStream;
import test.TestIR;
import util.*;
import semantic.*;
import grammar.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import util.error.MyError;


public class Compiler {
    public static void output(String content, String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(content);
            writer.close();
            System.out.println("Successfully wrote to the file " + fileName + ".\n");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
//        InputStream inputS = new FileInputStream("test.mx");
//        CharStream input = CharStreams.fromStream(inputS);
        CharStream input = CharStreams.fromStream(System.in);

        try {
            ProgramNode root;
            Scope globalScope = new Scope(Scope.ScopeType.Global, null);

            MxLexer lexer = new MxLexer(input);
            lexer.removeErrorListeners();
            lexer.addErrorListener(new MxErrorListener());
            MxParser parser = new MxParser(new CommonTokenStream(lexer));
            parser.removeErrorListeners();
            parser.addErrorListener(new MxErrorListener());
            ParseTree parseTreeRoot = parser.program();

            ASTBuilder astBuilder = new ASTBuilder();
            root = (ProgramNode) astBuilder.visit(parseTreeRoot);
            new SymbolCollector(globalScope).visit(root);
            new SemanticChecker(globalScope).visit(root);

            IRProgram irProgram = new IRProgram();
            new IRBuilder(globalScope, irProgram).visit(root);

            String irOutput = irProgram.toString();
            // write to file test.ll
            // output(irOutput, "test.ll");

            ASMModule module = new ASMModule();
            new InstructionSelector(module).visit(irProgram);
            new RegisterAllocator(module).visitFunction();
            String assembly = module.toString();
            // write to file test.s
            // output(assembly, "test.s");
            System.out.println(assembly);
        }
        catch (MyError error) {
            System.err.println(error.toString());
            throw new RuntimeException();
        }
        catch(Error error) {
            System.err.println(error.getMessage());
            throw new RuntimeException();
        }
    }
}
