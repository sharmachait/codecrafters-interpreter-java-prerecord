package syntax.AST.expressions;

import lexicon.Token;

public class Binary extends Expression{
    public final Expression left;
    public final Expression right;
    public final Token operator;

    public Binary(Expression left, Expression right, Token operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }
}
