package expression.operations;

import expression.PartOfExpression;
import expression.parts.Type;

public class Subtract extends BinaryOperation {

    public Subtract(PartOfExpression left, PartOfExpression right) {
        super(left, right, "-");
    }

    @Override
    protected PartOfExpression calculate(Type x, Type y) {
        return x.subtract(y);
    }
}
