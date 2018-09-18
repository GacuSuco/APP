package BalancedSymbolChecker;

import ListsStacksQueues.LinkedListStack;

import java.io.Reader;

public class Balance {
    private Tokenizer tok;
    private int errors;

    public Balance(Reader inStream) {
        errors = 0;
        tok = new Tokenizer(inStream);
    }

    public int checkBalance() {
        char ch;
        Symbol match = null;
        LinkedListStack<Symbol> pendingTokens = new LinkedListStack<Symbol>();

        while ((ch = tok.getNextOpenClose()) != '\0') {
            Symbol lastSymbol = new Symbol(ch, tok.getLineNumber());

            switch (ch) {
                case '(':
                case '[':
                case '{':
                    pendingTokens.push(lastSymbol);
                    break;

                case ')':
                case ']':
                case '}':
                    if (pendingTokens.isEmpty()) {
                        errors++;
                        System.out.println("Extraneous " + ch +
                                " at line " + tok.getLineNumber());
                    } else {
                        match = pendingTokens.pop();
                        checkMatch(match, lastSymbol);
                    }
                    break;

                default: // Cannot happen
                    break;
            }
        }

        while (!pendingTokens.isEmpty()) {
            match = pendingTokens.pop();
            System.out.println("Unmatched " + match.token +
                    " at line " + match.theLine);
            errors++;
        }
        return errors + tok.getErrorCount();
    }

    private void checkMatch( Symbol opSym, Symbol clSym )
    {
        if( opSym.token == '(' && clSym.token != ')' ||
                opSym.token == '[' && clSym.token != ']' ||
                opSym.token == '{' && clSym.token != '}' )
        {
            System.out.println( "Found " + clSym.token + " on line " +
                    tok.getLineNumber( ) + "; does not match " + opSym.token
                    + " at line " + opSym.theLine );
            errors++;
        }
    }
}
