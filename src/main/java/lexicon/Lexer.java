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
    private Character getCurr() {
        if(curr >= source.length()) return null;
        return source.charAt(curr);
    }
    private Character getNext() {
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
                return handleDualCharacterTokens(current);
            case '"':
                return string();
            default:
                if(Character.isDigit(current)){
                    number();
                } else if(Character.isAlphabetic(current)){
                    identifier();
                }else{
                    return new ScanException("[line "+line+"] Error: Unexpected character: "+current);
                }
        }
        return null;
    }

    private void identifier() {
        while(curr < source.length() && Character.isAlphabetic(getCurr())){
            getCurrMoveNext();
        }
        String text = source.substring(start,curr);
        addToken(IDENTIFIER, null);
    }

    private void number() {
        while(curr < source.length() && Character.isDigit(getCurr())) getCurrMoveNext();

        if(curr < source.length() && getCurr()=='.' && curr+1 < source.length() && Character.isDigit(getNext())){
            getCurrMoveNext();
            while(curr < source.length() && Character.isDigit(getCurr())) getCurrMoveNext();
        }
        addToken(NUMBER, Double.parseDouble(source.substring(start,curr)));
    }

    private ScanException string() {
        while(curr<source.length() && getCurr() != '"'){
            if(getCurr() == '\n') line++; // we support multi line strings
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
        Character next = getCurr();
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
        while(curr < source.length() && getCurr() != '\n'){
            curr++;
        }
    }
    private ScanException discardMultipleLines() {
        while(curr < source.length() &&
                curr+1 < source.length() &&
                !(getCurr() == '*' && getNext() == '/')
        ){
            if(getCurr() == '\n') line++;
            curr++;
        }
        if(curr >= source.length()){
            ScanException e = new ScanException("[line "+line+"] Unterminated multi line comment.");
            return e;
        }
        if(curr+1 >= source.length()){
            ScanException e = new ScanException("[line "+line+"] Unterminated multi line comment.");
            return e;
        }
        curr++; // *
        curr++; // /
        return null;
    }
}
