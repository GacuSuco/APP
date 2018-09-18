package BalancedSymbolChecker;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

public class Tokenizer {
    public static final int SLASH_SLASH = 0;
    public static final int SLASH_STAR = 1;

    private PushbackReader in;    // The input stream
    private char ch;              // Current character
    private int currentLine;      // Current line
    private int errors;           // Number of errors seen

    public Tokenizer(Reader inStream) {
        errors = 0;
        ch = '\0';
        currentLine = 1;
        in = new PushbackReader(inStream);
    }

    public int getLineNumber() {
        return currentLine;
    }

    public int getErrorCount() {
        return errors;
    }

    public char getNextOpenClose() {
        while (nextChar()) {
            if (ch == '/')
                processSlash();
            else if (ch == '\'' || ch == '"')
                skipQuote(ch);
            else if (ch == '\\')  // Extra case, not in text
                nextChar();
            else if (ch == '(' || ch == '[' || ch == '{' ||
                    ch == ')' || ch == ']' || ch == '}')
                return ch;
        }
        return '\0';       // End of file
    }

    private static final boolean isIdChar(char ch) {
        return Character.isJavaIdentifierPart(ch);
    }

    private String getRemainingString() {
        String result = "" + ch;

        for (; nextChar(); result += ch)
            if (!isIdChar(ch)) {
                putBackChar();
                break;
            }

        return result;
    }

    public String getNextID() {
        while (nextChar()) {
            if (ch == '/')
                processSlash();
            else if (ch == '\\')
                nextChar();
            else if (ch == '\'' || ch == '"')
                skipQuote(ch);
            else if (!Character.isDigit(ch) && isIdChar(ch))
                return getRemainingString();
        }
        return "";       // End of file
    }

    private boolean nextChar() {
        try {
            int readVal = in.read();
            if (readVal == -1)
                return false;
            ch = (char) readVal;
            if (ch == '\n')
                currentLine++;
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void putBackChar() {
        if (ch == '\n')
            currentLine--;
        try {
            in.unread((int) ch);
        } catch (IOException e) {
        }
    }

    private void skipComment(int start) {
        if (start == SLASH_SLASH) {
            while (nextChar() && (ch != '\n'))
                ;
            return;
        }

        // Look for a */ sequence
        boolean state = false;   // True if we have seen *

        while (nextChar()) {
            if (state && ch == '/')
                return;
            state = (ch == '*');
        }
        errors++;
        System.out.println("Unterminated comment!");
    }

    private void skipQuote(char quoteType) {
        while (nextChar()) {
            if (ch == quoteType)
                return;
            if (ch == '\n') {
                errors++;
                System.out.println("Missing closed quote at line " +
                        currentLine);
                return;
            } else if (ch == '\\')
                nextChar();
        }
    }

    private void processSlash() {
        if (nextChar()) {
            if (ch == '*') {
                // Javadoc comment
                if (nextChar() && ch != '*')
                    putBackChar();
                skipComment(SLASH_STAR);
            } else if (ch == '/')
                skipComment(SLASH_SLASH);
            else if (ch != '\n')
                putBackChar();
        }
    }
}
