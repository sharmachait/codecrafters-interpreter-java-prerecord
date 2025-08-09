package lexicon;

import java.util.ArrayList;
import java.util.List;

public class LoxScanner {
    private final String source;

    public LoxScanner(String fileContents) {
        this.source = fileContents;
    }

    private int curr = 0;
    public int tokenStart = 0; // usefull for mutli character tokens
    private int line = 1;

    private final List<Token> tokens = new ArrayList<>();
    private void addToken(TokenType type, Object literal){
        String text = source.substring(tokenStart, curr);
        tokens.add(new Token(type, text, literal, line));
    }


    public List<Token> scan() throws ScanException {

        while(curr < source.length()){
            tokenStart = curr;
            char c = getCurrMoveNext();
            scan(c);
        }
        tokens.add(new Token(TokenType.EOF, "", null, line));
        return tokens;
    }

    private char getCurrMoveNext() {
        return source.charAt(curr++);
    }

    private void scan(char c) {
        switch (c){
            case '(':
                addToken(TokenType.LEFT_PAREN, null);
                break;
            case ')':
                addToken(TokenType.RIGHT_PAREN, null);
                break;
        }
    }
}
