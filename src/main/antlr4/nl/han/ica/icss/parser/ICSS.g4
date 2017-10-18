grammar ICSS;

////////////////////////////////
// - - - - - Parser - - - - - //
////////////////////////////////
stylesheet
    : ((Semi)* variableDeclaration (Semi variableDeclaration?)*)
      (ruleSet+)*
      EOF
    ;

ruleSet
    : selector
        LBrace
            declarations
        RBrace
    ;
selector
    : (selectorTag)? LowerCase_Ident
    ;
selectorTag
    : cssClass
    | cssID
    ;
cssClass
    : Hash
    ;
cssID
    : Dot
    ;

declarations
    : (Semi)* styleDeclaration (Semi styleDeclaration?)*
    | (Semi)* variableDeclaration (Semi variableDeclaration?)*
    ;
styleDeclaration
    : attribute Colon expresion
    ;
variableDeclaration
    : variable Assigment expresion
    ;

attribute
    :LowerCase_Ident
    ;
variable
    : UpperCase_Ident
    ;

expresion
    : value (operator? value)*
    ;
operator
    : Plus
    | Minus
    | Star
    | Solidus
    ;

value
    : sizes
    | color
    | variable;

sizes
    : Pixels
    | Percentage
    ;
color
    : HexColor
    ;

///////////////////////////////
// - - - - - Lexer - - - - - //
///////////////////////////////
HexColor            : Hash HEXDEC HEXDEC HEXDEC HEXDEC HEXDEC HEXDEC;
Pixels              : Number ('p'|'P') ('x'|'X');
Percentage          : Number '%';

fragment HEXDEC     : 'a'..'f'
                    | '0'..'9' ;
fragment LCCHAR     : 'a'..'z' ;
fragment LCNMCHAR   : Minus
                    | 'a'..'z'
                    | '0'..'9' ;
fragment UCCHAR     : 'A'..'Z' ;
fragment UCNMCHAR   : UnderScore
                    | 'A'..'Z'
                    | '0'..'9' ;

Assigment           : Colon Equal ;
Hash                : '#' ;
Greater             : '>' ;
LBrace              : '{' ;
RBrace              : '}' ;
LBracket            : '[' ;
RBracket            : ']' ;
Equal               : '=' ;
Semi                : ';' ;
Colon               : ':' ;
Solidus             : '/' ;
UnderScore          : '_' ;
Minus               : '-' ;
Plus                : '+' ;
Star                : '*' ;
LParen              : '(' ;
RParen              : ')' ;
Comma               : ',' ;
Dot                 : '.' ;

Number              : [0-9]+;

LowerCase_Ident     : Minus? LCCHAR LCNMCHAR*;
UpperCase_Ident     : UnderScore? UCCHAR UCNMCHAR*;

WS : [ \t\r\n]+ -> skip ;