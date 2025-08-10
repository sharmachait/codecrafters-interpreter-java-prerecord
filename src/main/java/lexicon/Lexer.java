package lexicon;

import java.util.ArrayList;
import java.util.List;
import static lexicon.TokenType.*;

public class Lexer {
    private final String source;
    private char getCurrMoveNext(){
        return source.charAt(curr++);
    }
    private int line = 1;
    private int curr = 0;
    private int start = 0; // (()
                           // s
                           //  c
    private final List<Token> tokens = new ArrayList<>();
    private void addToken(TokenType type, Object literal) {
        String lexeme = source.substring(start,curr);
        tokens.add(new Token(type, lexeme, literal, line));
    }
    public Lexer(String fileContents) {
        source = fileContents;
    }
// for
//    s
//    c
    public List<Token> scan() throws ScanException{

        while(curr < source.length()) {
            char current = getCurrMoveNext();
            handleToken(current);
            start = curr;
        }

        addToken(EOF, null);
        return tokens;
    }

    private void handleToken(char current) throws ScanException {
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

            case '/':
                // not really a single character token may be 2
                addToken(SLASH, null);
                break;
            default:
                throw new ScanException("[line "+line+"] Error: Unexpected character: " + current);
        }
    }
}
