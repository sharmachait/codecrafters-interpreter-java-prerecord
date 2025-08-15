package lexicon;

import java.util.ArrayList;
import java.util.List;
import static lexicon.TokenType.*;

public class Lexer {
    public Lexer(String fileContents) {
        source = fileContents;
    }
    private final String source;
    private char getCurrMoveNext(){
        return source.charAt(curr++);
    }
    private Character getNext() {
        if(curr >= source.length()) return null;
        return source.charAt(curr);
    }
    private Character getNextNext() {
        int next = curr+1;
        if(next >= source.length()) return null;
        return source.charAt(next);
    }

    private int line = 1;
    private int curr = 0;
    private int start = 0;

    private final List<Token> tokens = new ArrayList<>();
    private void addToken(TokenType type, Object literal) {
        String lexeme = source.substring(start,curr);
        tokens.add(new Token(type, lexeme, literal, line));
    }

    public static class Result {
        public final List<Token> tokens;
        public final Exception exception;

        public Result(List<Token> tokens, Exception exception) {
            this.tokens = tokens;
            this.exception = exception;
        }
    }

    public Result scan() {
        ScanException isException = null;
        while(curr < source.length()) {
            char current = getCurrMoveNext();
            ScanException currentException = handleToken(current);
            if(currentException != null){
                isException = currentException;
            }
            start = curr;
        }

        addToken(EOF, null);
        return new Result(tokens, isException);
    }

    private ScanException handleToken(char current) {
        switch (current){
            case '(':
                addToken(LEFT_PAREN, null);
                break;
            case ')':
                addToken(RIGHT_PAREN, null);
                break;
            case '{':
                addToken(LEFT_BRACE, null);
                break;
            case '}':
                addToken(RIGHT_BRACE, null);
                break;
            case ';':
                addToken(SEMICOLON, null);
                break;
            case ':':
                addToken(COLON, null);
                break;
            case '?':
                addToken(QUESTION, null);
                break;
            case ',':
                addToken(COMMA, null);
                break;
            case '.':
                addToken(DOT, null);
                break;
            case '-':
                addToken(MINUS, null);
                break;
            case '+':
                addToken(PLUS, null);
                break;
            case '*':
                addToken(STAR, null);
                break;
            case ' ','\r','\t':
                break;
            case '\n':
                line++;
                break;
            case '=', '!', '<', '>', '/':
                ScanException dualCharError = handleDualCharacterTokens(current);
                if(dualCharError!=null) {
                    System.err.println(dualCharError.getMessage());
                    return dualCharError;
                }
                break;
            case '"':
                ScanException stringException = string();
                if(stringException!=null) {
                    System.err.println(stringException.getMessage());
                    return stringException;
                }
                break;
            default:
                ScanException e = new ScanException("[line "+line+"] Error: Unexpected character: "+current);
                System.err.println(e.getMessage());
                return e;
        }
        return null;
    }

    private ScanException string() {
        while(curr<source.length() && getNext() != '"'){
            if(getNext() == '\n') line++; // we support multi line strings
            getCurrMoveNext();
        }
        if(curr >= source.length()){
            ScanException e = new ScanException("[line "+line+ "] Error: Unterminated string.");
            return e;
        }
        // curr must necessarily be at "
        getCurrMoveNext();
        String value = source.substring(start+1, curr-1);
        addToken(STRING, value);
        return null;
    }

    private ScanException handleDualCharacterTokens(char current) {
        ScanException e = null;
        Character next = getNext();
        switch (current){
            case '=':
                if(next == null || next!='='){
                    addToken(EQUAL, null);
                }else{
                    curr++;
                    addToken(EQUAL_EQUAL, null);
                }
                break;
            case '!':
                if(next == null || next!='='){
                    addToken(BANG, null);
                }else{
                    curr++;
                    addToken(BANG_EQUAL, null);
                }
                break;
            case '<':
                if(next == null || next!='='){
                    addToken(LESS, null);
                }else{
                    curr++;
                    addToken(LESS_EQUAL, null);
                }
                break;
            case '>':
                if(next == null || next!='='){
                    addToken(GREATER, null);
                }else{
                    curr++;
                    addToken(GREATER_EQUAL, null);
                }
                break;
            case '/':
                if(next == null || (next != '/' && next != '*')){
                    addToken(SLASH,null);
                }else{
                    curr++;
                    if(next == '/') discardLine();
                    else e = discardMultipleLines();
                }
                break;
            default:
                throw new RuntimeException("Invalid character passed in to be checked for dual character token");
        }
        return e;
    }
    private void discardLine() {
        while(curr < source.length() && getNext() != '\n'){
            curr++;
        }
    }
    private ScanException discardMultipleLines() {
        while(curr < source.length() &&
                curr+1 < source.length() &&
                !(getNext() == '*' && getNextNext() == '/')
        ){
            if(getNext() == '\n') line++;
            curr++;
        }
        if(curr >= source.length()){
            ScanException e = new ScanException("[line "+line+"] Unterminated multi line comment.");
            System.err.println(e.getMessage());
            return e;
        }
        if(curr+1 >= source.length()){
            ScanException e = new ScanException("[line "+line+"] Unterminated multi line comment.");
            System.err.println(e.getMessage());
            return e;
        }
        curr++; // *
        curr++; // /
        return null;
    }
}
