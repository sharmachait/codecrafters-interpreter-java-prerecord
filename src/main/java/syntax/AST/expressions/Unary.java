package syntax.AST.expressions;

import lexicon.Token;

public class Unary extends Expression{
    public final Token operator;
    public final Expression right;

    public Unary(Token operator, Expression right) {
        this.operator = operator;
        this.right = right;
    }
}
