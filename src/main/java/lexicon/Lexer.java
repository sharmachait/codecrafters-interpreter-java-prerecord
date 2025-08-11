package lexicon;

import java.util.ArrayList;
import java.util.List;
import static lexicon.TokenType.*;

public class Lexer {
    private final String source;
    public Lexer(String fileContents) {
        source = fileContents;
    }
    private char getCurrMoveNext(){
        return source.charAt(curr++);
    }
    private Character getNext(){
        return source.charAt(curr);
    }
    private Character getNextNext(){
        int next = curr+1;
        if(next >= source.length()) return '\0';
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

    public static class Result{
        public Result(List<Token> tokens, ScanException e) {
            this.tokens = tokens;
            this.e = e;
        }

        public final List<Token> tokens;
        public final ScanException e;
    }

    public Result scan() {
        ScanException e = null;
        while(curr < source.length()) {
            char current = getCurrMoveNext();
            ScanException currentCharError = handleToken(current);
            if(e == null){
                e = currentCharError;
            }
            start = curr;
        }

        addToken(EOF, null);
        return new Result(tokens, e);
    }

    private ScanException handleToken(char current) {
        switch (current) {
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
            case ';':
                addToken(SEMICOLON, null);
                break;
            case '*':
                addToken(STAR, null);
                break;
            case ':':
                addToken(COLON, null);
                break;
            case '?':
                addToken(QUESTION, null);
                break;
            case '=', '!', '<', '>', '/':
                return handleMaybeDualCharacterToken(current); // concept of Maximum Munch, not associativity
            default:
                ScanException e = new ScanException("[line "+line+"] Error: Unexpected character: " + current);
                System.err.println(e.getMessage());
                return e;
        }
        return null;
    }

    private ScanException handleMaybeDualCharacterToken(Character c) throws RuntimeException {
        ScanException e = null;
        Character next = getNext();
        switch (c){
            case '=':
                if(next != '='){
                    addToken(EQUAL, null);
                }else{
                    curr++;
                    addToken(EQUAL_EQUAL, null);
                }
                break;
            case '!':
                if(next != '='){
                    addToken(BANG, null);
                }
                else {
                    curr++;
                    addToken(BANG_EQUAL, null);
                }
                break;
            case '<':
                if(next != '='){
                    addToken(LESS, null);
                }
                else {
                    curr++;
                    addToken(LESS_EQUAL, null);
                }
                break;
            case '>':
                if(next != '='){
                    addToken(GREATER, null);
                }
                else {
                    curr++;
                    addToken(GREATER_EQUAL, null);
                }
                break;
            case '/':
                if(next != '*' && next != '/'){
                    addToken(SLASH, null);
                }
                else {
                    curr++;
                    if(next == '/') {
                        discardLine();
                    }else {
                        e = discardMultiLine();
                    }
                }
                break;
            default:
                throw new RuntimeException("Invalid character passed in to be checked for dual character token");
        }
        return e;
    }

    private void discardLine() {
        while(curr < source.length() && getNext() != '\n')
            getCurrMoveNext();
    }
    private ScanException discardMultiLine() {
        while(curr < source.length() && !(getNext() == '*' && getNextNext() == '/')) {
            if(getNext() == '\n') line++;
            getCurrMoveNext();
        }
        if(curr >= source.length()){
            ScanException e = new ScanException("[line "+line+"] Error: Unterminated multiline comment.");
            System.err.println(e.getMessage());
            return e;
        }
        getCurrMoveNext();//'*'
        getCurrMoveNext();//'/'
        return null;
    }
}
