package lexicon;

public class Token {
    private final TokenType type;
    private final String lexeme;
    private final Object literal;
    public final Integer line;

    public Token(TokenType type, String lexeme, Object literal, Integer line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }
    @Override
    public String toString(){
        return type+" "+lexeme+" "+literal;
    }
}
