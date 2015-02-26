# TopDownParser

This is a Java project that will parse a program (input as a string in the console after running TinyPL) to 
see if it is valid for an imaginary language's grammar.

Lexer, Buffer, and Token classes were all provided completely (this was for a school assignment). 
TinyPL.java was just stubbed out (everything in there was written by me except for the class headers).

#### Grammar (Chomsky notation with EBNF extensions):
    program --> 'begin' decls stmts 'end'
    
    decls --> [ 'int' idlist ';' ]
              [ 'real' idlist ';' ]
              [ 'bool' idlist ';' ]
    
    idlist --> id {',' id}
    
    stmts --> [ stmt [ stmts ] ]
    
    stmt --> assign | cond | loop | cmpd
    
    assign --> var '=' expr ';'
    
    var --> id
    
    loop --> 'while' '(' expr ')' stmt
    
    cond --> 'if' '(' expr ')' stmt [ 'else' stmt ]
    
    cmpd --> '{' stmts '}'
    
    expr --> term [ ('+' | '-' | '||') expr ]
    
    term --> factor [ ('*' | '/' | '&&') term ]
    
    factor --> literal
             | '!' factor
             | '(' expr ')'
             | '(' expr ('<=' | '>=' | '==' | '!=' | '<' | '>') expr ')'
    
    literal --> int_lit | real_lit | 'true' | 'false' | id
