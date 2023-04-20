package expression.operations;

import expression.PartOfExpression;
import expression.parts.Type;

public abstract class UnaryOperation implements PartOfExpression {
    protected final PartOfExpression part;
    private final String operation;

    public UnaryOperation(PartOfExpression part, String operation) {
        this.part = part;
        this.operation = operation;
    }

    protected abstract PartOfExpression calculate(Type x);

    @Override
    public PartOfExpression evaluate() {
        if (!(part instanceof Type p)) {
            throw new IllegalArgumentException("Can't make operation with not Type classes");
        }
        return calculate(p);
    }
}
