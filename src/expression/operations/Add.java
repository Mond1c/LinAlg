package expression.operations;

import expression.PartOfExpression;
import expression.parts.Type;

public class Add extends BinaryOperation {
    public Add(PartOfExpression left, PartOfExpression right) {
        super(left, right, "+");
    }

    @Override
    protected PartOfExpression calculate(Type x, Type y) {
        return x.add(y);
    }
}
