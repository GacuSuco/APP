package BalancedSymbolChecker;

public class Symbol {
    public char token;
    public int theLine;

    public Symbol(char tok, int line) {
        token = tok;
        theLine = line;
    }
}