parser grammar MxParser;

options {
    tokenVocab = MxLexer;
}

@header {
    package grammar;
}

program: (def)* EOF;
def: classDef | funcDef | varDef;

classDef: Class className = Identifiers LeftBrace (varDef | funcDef | constructor)* RightBrace Semicolon;
varDef: type singleVarDef (Comma singleVarDef)* Semicolon;
singleVarDef: varName = Identifiers (Assign expression)?;
funcDef: typeVoid funcName = Identifiers paramList suite;
constructor: className = Identifiers LeftParen RightParen suite;
paramList: LeftParen (paramDef (Comma paramDef)*)* RightParen;
paramDef: type paramName = Identifiers;

// Types
typeVoid: type | Void;
type: typeWithoutArray (LeftBracket RightBracket)*;
typeWithoutArray: internalType | Identifiers;
internalType: Bool | Int | String;

suite: LeftBrace statement* RightBrace;

statement
    : suite                                                                                             #suiteStmt
    | varDef                                                                                            #varDefStmt
    | If LeftParen expression RightParen thenStmt = statement (Else elseStmt = statement)?              #ifStmt
    | While LeftParen conditionExpr = expression RightParen statement                                   #whileStmt
    | For   LeftParen initStmt = forInitStatement // varDef 有分号
            conditionExpr = expression? Semicolon
            incExpr = expression? RightParen
            statement                                                                                   #forStmt
    | Return expression? Semicolon                                                                      #returnStmt
    | Break Semicolon                                                                                   #breakStmt
    | Continue Semicolon                                                                                #continueStmt
    | expression Semicolon                                                                              #exprStmt
    | Semicolon                                                                                         #emptyStmt
    ;

forInitStatement: varDef | (expression? Semicolon);

expression
    : primary                                                                                           #atomExpr

    // precedence == 2
    | expression opt = (SelfInc | SelfDec)                                                              #suffixExpr
    | expression Dot Identifiers                                                                        #memberExpr
    | expression Dot Identifiers LeftParen argumentList? RightParen                                     #methodExpr
    | funcName = Identifiers LeftParen argumentList? RightParen                                         #funcCallExpr
    | expression LeftBracket expression RightBracket                                                    #subscriptExpr

    // precedence == 3
    | <assoc=right> opt = (SelfInc | SelfDec) expression                                                #prefixExpr
    | <assoc=right> opt = (Add | Sub) expression                                                        #unaryExpr
    | <assoc=right> opt = Not expression                                                                #unaryExpr
    | <assoc=right> opt = BitwiseNot expression                                                         #unaryExpr
    | <assoc=right> New newUnit                                                                         #newExpr

    // precedence == 5
    | leftExpr = expression opt = (Mul | Div | Mod) rightExpr = expression                              #binaryExpr

    // precedence == 6
    | leftExpr = expression opt = (Add | Sub) rightExpr = expression                                    #binaryExpr

    // precedence == 7
    | leftExpr = expression opt = (LeftShift | RightShift) rightExpr = expression                       #binaryExpr

    // precedence == 9
    | leftExpr = expression opt = (Greater | Less | GreaterEqual | LessEqual) rightExpr = expression    #binaryExpr

    // precedence == 10
    | leftExpr = expression opt = (Equal | NotEqual) rightExpr = expression                             #binaryExpr

    // precedence == 11
    | leftExpr = expression opt = BitwiseAnd rightExpr = expression                                     #binaryExpr

    // precedence == 12
    | leftExpr = expression opt = BitwiseXor rightExpr = expression                                     #binaryExpr

    // precedence == 13
    | leftExpr = expression opt = BitwiseOr rightExpr = expression                                      #binaryExpr

    // precedence == 14
    | leftExpr = expression opt = And rightExpr = expression                                            #binaryExpr

    // precedence == 15
    | leftExpr = expression opt = Or rightExpr = expression                                             #binaryExpr

    // precedence == 16
    | <assoc=right> conditionExpr = expression QuestionMark
      thenExpr = expression Colon elseExpr = expression                                                 #ternaryExpr
    | <assoc=right> leftExpr = expression opt = Assign rightExpr = expression                           #assignExpr
    ;

primary
    : (LeftParen expression RightParen)
    | This
    | literal
    | Identifiers
    ;
literal: IntConst | StringConst | True | False | Null;

newUnit: errorCreator | arrayCreator | classCreator | unitCreator;
errorCreator: typeWithoutArray (LeftBracket expression RightBracket)+ (LeftBracket RightBracket)+ (LeftBracket expression RightBracket)+;
arrayCreator: typeWithoutArray (LeftBracket expression RightBracket)+ (LeftBracket RightBracket)*;
classCreator: typeWithoutArray LeftParen RightParen;
unitCreator: typeWithoutArray;

argumentList: expression (Comma expression)*;