package syntax.AST.expressions;

public class Literal extends Expression{
    public final Object value;

    public Literal(Object value) {
        this.value = value;
    }
}
