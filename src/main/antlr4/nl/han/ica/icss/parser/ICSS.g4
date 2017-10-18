grammar ICSS;

stylesheet
    : (variabelDeclaraties)*
       ruleSet+
       EOF
    ;

variabelDeclaraties
    : (Semi)* variabelDeclaratie (Semi variabelDeclaratie?)*
    ;

variabelDeclaratie
    : variabelNaam Assigment variabelWaarde
    ;

variabelNaam
    : UpperCase_Ident
    ;

variabelWaarde
    : waarde
    | berekening
    ;

ruleSet
    : selector
        LBrace
            declaraties
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

declaraties
    : (Semi)* declaratie (Semi declaratie?)*
    ;
declaratie
    : attribuutnaam Colon waarde
    ;


attribuutnaam
    :LowerCase_Ident;

waarde
    : groottes
    | Kleuren
    //| berekening
    ;

berekening
    : <assoc=right> berekening Plus berekening
    | <assoc=right> berekening Minus berekening
    | waarde;

groottes
    : Pixels
    | Percentage
    ;


Kleuren             : Hash HEXDEC HEXDEC HEXDEC HEXDEC HEXDEC HEXDEC;

Pixels              : Number ('p'|'P') ('x'|'X');
Percentage          : Number '%';


fragment HEXDEC     : [0-9a-f];

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


////Parsers
//stylesheet
//    : ruleSet+
//      EOF
//    ;
//
//ruleSet // eerst wordt er gekeken naar de selector, dit kunnen meerdere zijn geschijde van een comma;
//    : selector (Comma selector)*
//        LBrace
//           declarationList
//        RBrace
//    ;
//
//selector // selectors need: element name, id , class
//    : (elementSubsequent)+
//    ;
//elementSubsequent
//    : className
//    | elementName
//    | Hash
//    ;
//className
//    : Dot Identifier
//    ;
//elementName
//    : Identifier
//    ;
//
//declarationList
//     :(Semi)*                  // eventuele overgebleven (;)
//      declaration              // prop zelf
//      (Semi declaration?)*     // eventuele (;) en volgende prop;
//    ;
//declaration
//    : property Colon value
//    ;
//property
//    : Identifier
//    ;
//
//value
//    : Identifier
//    | color
//    | number
//    | String
//    | size
//    | percentage
//    ;
//color
//    : Hash
//    ;
//number
//    : ( Plus | Minus )? Number;
//size
//    : ( Plus | Minus )? Size
//    | ( Plus | Minus )? Number Identifier ;
//percentage
//    : ( Plus | Minus )? Percentage;
//
////Lexers
//fragment HEX        : [0-9a-fA-F] ;
//fragment CHAR       : 'a'..'z'
//                    | 'A'..'Z'
//                    | '_' ;
//fragment NMCHAR     : 'a'..'z'
//                    | 'A'..'Z'
//                    | '0'..'9'
//                    | '_'
//                    | Minus ;
//fragment NAME       : NMCHAR+ ;
//
//Percentage          : Number '%';
//Size                : Number ('p'|'P') ('x'|'X')    // ..px
//                    | Number ('c'|'C') ('m'|'M')    // ..cm
//                    | Number ('m'|'M') ('m'|'M');   // ..mm
//// Basic special char
//Hash                : '#' NAME ;
//Greater             : '>' ;
//LBrace              : '{' ;
//RBrace              : '}' ;
//LBracket            : '[' ;
//RBracket            : ']' ;
//Equal               : '=' ;
//Semi                : ';' ;
//Colon               : ':' ;
//Solidus             : '/' ;
//Minus               : '-' ;
//Plus                : '+' ;
//Star                : '*' ;
//LParen              : '(' ;
//RParen              : ')' ;
//Comma               : ',' ;
//Dot                 : '.' ;
//// Dirty lazy string
//String              : '"'.*?'"';
//// tja, spreekt voor zichzelf he!
//Number              : [0-9]+
//                    | [0-9]* '.' [0-9]+;
//// identifier (kan properties names en values opvangen)
//Identifier          : Minus? CHAR NMCHAR* ;
//
//WS : [ \t\r\n]+ -> skip ;