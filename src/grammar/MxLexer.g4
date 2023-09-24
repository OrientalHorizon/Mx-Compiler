lexer grammar MxLexer;

@header {
    package grammar;
}

// Arithmetic operators
Add: '+';
Sub: '-';
Mul: '*';
Div: '/';
Mod: '%';

// Relation operators
Greater: '>';
Less: '<';
GreaterEqual: '>=';
LessEqual: '<=';
NotEqual: '!=';
Equal: '==';

// Logic operators
And: '&&';
Or: '||';
Not: '!';

// Bitwise operators
RightShift: '>>';
LeftShift: '<<';
BitwiseAnd: '&';
BitwiseOr: '|';
BitwiseXor: '^';
BitwiseNot: '~';

Assign: '=';

SelfInc: '++';
SelfDec: '--';

Dot: '.';

LeftParen: '(';
RightParen: ')';
LeftBrace: '{';
RightBrace: '}';
LeftBracket: '[';
RightBracket: ']';

Comma: ',';
Semicolon: ';';
Colon: ':';
QuestionMark: '?';

// Keywords
Void: 'void';
Bool: 'bool';
Int: 'int';
String: 'string';
New: 'new';
Class: 'class';
Null: 'null';
True: 'true';
False: 'false';
This: 'this';
If: 'if';
Else: 'else';
For: 'for';
While: 'while';
Break: 'break';
Continue: 'continue';
Return: 'return';

// Identifiers
Identifiers: [a-zA-Z][a-zA-Z_0-9]*;

// Literals
IntConst: [1-9][0-9]* | '0';
StringConst: '"' StringChars* '"';
fragment StringChars
    : ~["\\\r\n]    // not ", \, etc
    | '\\n'         // \n
    | '\\\\'        // \\
    | '\\"'         // \"
    ;

// Ignored fragments
WhiteSpace: [ \t\r\n]+ -> skip;
BlockComment: '/*' .*? '*/' -> skip;
LineComment: '//' ~[\r\n]* -> skip;