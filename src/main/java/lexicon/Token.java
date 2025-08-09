package lexicon;

public class Token {
    public final TokenType type;
    public final String lexeme;
    public final Object literal;
    public final Integer line;

    public Token(TokenType type, String lexeme, Object literal, Integer line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }
    public String toString(){
        return type+" "+lexeme+" "+literal;
    }
}
