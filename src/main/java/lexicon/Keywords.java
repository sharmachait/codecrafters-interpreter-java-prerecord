package lexicon;

import java.util.HashMap;
import java.util.Map;
import static lexicon.TokenType.*;

public class Keywords {
    public static final Map<String, TokenType> keywords = new HashMap<>() {{
        put("and", AND);
        put("class", CLASS);
        put("else",      ELSE);
        put("false",     FALSE);
        put("for",       FOR);
        put("break",     BREAK);
        put("continue",  CONTINUE);
        put("fun",       FUN);
        put("if",        IF);
        put("nil",       NIL);
        put("or",        OR);
        put("print",     PRINT);
        put("return",    RETURN);
        put("super",     SUPER);
        put("this",      THIS);
        put("true",      TRUE);
        put("var",       VAR);
        put("while",     WHILE);

    }};
}
