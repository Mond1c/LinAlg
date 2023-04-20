package expression.operations;

import expression.PartOfExpression;
import expression.parts.Matrix;
import expression.parts.Type;

public class Solve extends BinaryOperation {
    public Solve(PartOfExpression left, PartOfExpression right) {
        super(left, right, "solve");
    }

    @Override
    protected PartOfExpression calculate(Type x, Type y) {
        return null;
    }
}
