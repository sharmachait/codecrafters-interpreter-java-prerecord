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
        assert curr < source.length();
        return source.charAt(curr++);
    }
    private Character getNext(){
        assert curr< source.length();
        return source.charAt(curr);
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
            case '=', '!', '<', '>':
                handleMaybeDualCharacterToken(current); // concept of Maximum Munch, not associativity
                break;
            case '/':
                // not really a single character token may be 2
                addToken(SLASH, null);
                break;
            default:
                ScanException e = new ScanException("[line "+line+"] Error: Unexpected character: " + current);
                System.err.println(e.getMessage());
                return e;
        }
        return null;
    }

    private void handleMaybeDualCharacterToken(Character c) throws RuntimeException {
        Character next;
        if(curr >= source.length()) next = null;
        // i like this way of coding, if somebody tries to get the next character, but next is not there we should crash
        // this forces null check to be written near the business logic and i like that better
        else next = getNext();
        switch (c){
            case '=':
                if(next==null || next != '=') {
                    addToken(EQUAL, null);
                }else{
                    curr++;
                    addToken(EQUAL_EQUAL, null);
                }
                break;
            case '!':
                if(next==null || next != '='){
                    addToken(BANG, null);
                }
                else {
                    curr++;
                    addToken(BANG_EQUAL, null);
                }
                break;
            case '<':
                if(next==null || next != '='){
                    addToken(LESS, null);
                }
                else {
                    curr++;
                    addToken(LESS_EQUAL, null);
                }
                break;
            case '>':
                if(next==null || next != '='){
                    addToken(EQUAL, null);
                }
                else {
                    curr++;
                    addToken(GREATER_EQUAL, null);
                }
                break;
            default:
                throw new RuntimeException("Invalid character passed in to be checked for dual character token");
        }

    }
}
