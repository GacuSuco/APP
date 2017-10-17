grammar ICSS;

//Parsers
stylesheet
    : ruleSet+
      EOF
    ;

ruleSet // eerst wordt er gekeken naar de selector, dit kunnen meerdere zijn geschijde van een comma;
    : selector (Comma selector)*
        LBrace
           declarationList
        RBrace
    ;

selector // selectors need: element name, id , class
    : (elementSubsequent)+
    ;
elementSubsequent
    : className
    | elementName
    | Hash
    ;
className
    : Dot Identifier
    ;
elementName
    : Identifier
    ;

declarationList
     :(Semi)*                  // eventuele overgebleven (;)
      declaration              // prop zelf
      (Semi declaration?)*     // eventuele (;) en volgende prop;
    ;
declaration
    : property Colon value
    ;
property
    : Identifier
    ;

value
    : Identifier
    | color
    | number
    | String
    | size
    | percentage
    ;
color
    : Hash
    ;
number
    : ( Plus | Minus )? Number;
size
    : ( Plus | Minus )? Size
    | ( Plus | Minus )? Number Identifier ;
percentage
    : ( Plus | Minus )? Percentage;

//Lexers
fragment HEX        : [0-9a-fA-F] ;
fragment CHAR       : 'a'..'z'
                    | 'A'..'Z'
                    | '_' ;
fragment NMCHAR     : 'a'..'z'
                    | 'A'..'Z'
                    | '0'..'9'
                    | '_'
                    | Minus ;
fragment NAME       : NMCHAR+ ;

Percentage          : Number '%';
Size                : Number ('p'|'P') ('x'|'X')    // ..px
                    | Number ('c'|'C') ('m'|'M')    // ..cm
                    | Number ('m'|'M') ('m'|'M');   // ..mm
// Basic special char
Hash                : '#' NAME ;
Greater             : '>' ;
LBrace              : '{' ;
RBrace              : '}' ;
LBracket            : '[' ;
RBracket            : ']' ;
Equal               : '=' ;
Semi                : ';' ;
Colon               : ':' ;
Solidus             : '/' ;
Minus               : '-' ;
Plus                : '+' ;
Star                : '*' ;
LParen              : '(' ;
RParen              : ')' ;
Comma               : ',' ;
Dot                 : '.' ;
// Dirty lazy string
String              : '"'.*?'"';
// tja, spreekt voor zichzelf he!
Number              : [0-9]+
                    | [0-9]* '.' [0-9]+;
// identifier (kan properties names en values opvangen)
Identifier          : Minus? CHAR NMCHAR* ;

WS : [ \t\r\n]+ -> skip ;