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
    public List<Token> scan() {

        while(curr < source.length()) {
            char current = getCurrMoveNext();
            handleToken(current);
            start = curr;
        }

        addToken(EOF, null);
        return tokens;
    }

    private void handleToken(char current) {
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
        }
    }
}
