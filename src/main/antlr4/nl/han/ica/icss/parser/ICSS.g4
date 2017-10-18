grammar ICSS;

////////////////////////////////
// - - - - - Parser - - - - - //
////////////////////////////////
stylesheet
    : typeRuleSet*
      EOF
    ;

typeRuleSet
    : styleRuleSet
    | variableDeclaration
    ;
styleRuleSet
    : selector
        (LBracket booleanExpresion RBracket)?
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
    : styleDeclaration*
    | variableDeclaration*
    ;
styleDeclaration
    : attribute Colon expresion Semi
    ;
variableDeclaration
    : variable Assigment expresion Semi
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
logicalOperator
    : AND
    | OR
    ;

comparator
    : Greater
    | Lesser
    | Equal
    ;

booleanExpresion
    : booleanExpresion logicalOperator booleanExpresion
    | value (comparator? value)*
    ;


value
    : sizes
    | color
    | bool
    | variable;

sizes
    : Pixels
    | Percentage
    ;
color
    : HexColor
    ;
bool
    : TRUE
    | FALSE
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
Lesser              : '<' ;
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

AND        : 'AND'
           | '&&' ;
OR         : 'OR'
           | '||' ;
TRUE       : 'true' ;
FALSE      : 'false' ;


LowerCase_Ident     : Minus? LCCHAR LCNMCHAR*;
UpperCase_Ident     : UnderScore? UCCHAR UCNMCHAR*;


WS : [ \t\r\n]+ -> skip ;