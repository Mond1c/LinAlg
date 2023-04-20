package expression.operations;

import expression.PartOfExpression;
import expression.parts.Type;

public class Negate extends UnaryOperation {
    public Negate(PartOfExpression part) {
        super(part, "-");
    }

    @Override
    protected PartOfExpression calculate(Type x) {
        return x.negate();
    }
}
