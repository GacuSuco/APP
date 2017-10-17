grammar ICSS;

//stylesheet: WS rule_set+ EOF;

stylesheet:rule_set+;

rule_set:selector '{'(ws) properties (ws)'}';
selector:(Selctor_Id | Selctor_Class | (CharNum+))(ws);
properties:property+;
property: (ws)PROPERTY_NAME Colon (ws) PROPERTY_VALUE Semicom;
ws: (Space)*;


PROPERTY_VALUE: Color | Size | String;
PROPERTY_NAME: CharNum+ |CharNum+[-]CharNum+;

Size: [0-9]*('px' | '%');
Color:Hash [a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9];
String:'"'.*?'"';

Selctor_Id:Hash CharNum+;
Selctor_Class:Dot CharNum+;

CharNum: 'a'..'z' | 'A'..'Z' | '0'..'9';
Hash: '#';
Dot:'.';
Colon: ':';
Semicom:';';
Space:[ \t\r\n\f]+;




WS: [ \f\t\r\n]+ -> skip;
//SPACE:' '|'\t'|'\f';
NEWLINE:'\r'? '\n' | '\r';

