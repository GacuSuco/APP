grammar ICSS;

//Parsers
stylesheet
    : ruleSet+
      EOF
    ;

// eerst wordt er gekeken naar de selector, dit kunnen meerdere zijn geschijde van een comma;
ruleSet
    : selector (Comma selector)*
        LBrace
           declarationList
        RBrace
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

// TODO
// Values types toevoegen (colors, resolutie, namen, getalen)
value
    : Identifier
    ;


// selectors need: element name, id , class
selector
    : (elementSubsequent)+
    ;

elementSubsequent
    : className
    | elementName
    ;


className
    : Dot Identifier
    ;

elementName
    : Identifier
    ;

//Lexers
// Fragments
fragment HEX    : [0-9a-fA-F] ;
fragment CHAR   : 'a'..'z'
                | 'A'..'Z'
                | '_' ;
fragment NMCHAR : 'a'..'z'
                | 'A'..'Z'
                | '0'..'9'
                | '_'
                | Minus ;

fragment NAME   : NMCHAR+ ;

// Basic special char
Hash            : '#' ;
Greater         : '>' ;
LBrace          : '{' ;
RBrace          : '}' ;
LBracket        : '[' ;
RBracket        : ']' ;
Equal           : '=' ;
Semi            : ';' ;
Colon           : ':' ;
Solidus         : '/' ;
Minus           : '-' ;
Plus            : '+' ;
Star            : '*' ;
LParen          : '(' ;
RParen          : ')' ;
Comma           : ',' ;
Dot             : '.' ;

// tja, spreekt voor zichzelf he!
Number          : [0-9]+
                | [0-9]* '.' [0-9]+;

// identifier (kan properties names en values opvangen)
Identifier      : Minus? CHAR NMCHAR* ;
