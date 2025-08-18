package syntax.AST.expressions;

public class Grouping extends Expression{
    public final Expression expression;

    public Grouping(Expression expression) {
        this.expression = expression;
    }
}
