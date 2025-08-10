package lexicon;

public enum TokenType {

    //single character tokens
    LEFT_PAREN,
    RIGHT_PAREN,
    EOF,
    LEFT_BRACE,
    RIGHT_BRACE,
    COMMA,
    DOT,
    MINUS,
    PLUS,
    SEMICOLON,
    STAR,
    COLON,
    QUESTION,

    // maybe single or double
    SLASH,
    BANG,
    EQUAL,
    GREATER,
    LESS,

    // double character tokens
    BANG_EQUAL,
    EQUAL_EQUAL,
    GREATER_EQUAL,
    LESS_EQUAL,

    // multi character tokens
    IDENTIFIER,
    STRING,
    NUMBER,
    AND,
    CLASS,
    ELSE,
    IF,
    FALSE,
    TRUE,
    FUN,
    FOR,
    WHILE,
    NIL,
    OR,
    PRINT,
    RETURN,
    SUPER,
    THIS,
    VAR,
    BREAK,
    CONTINUE
}
