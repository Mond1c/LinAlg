package expression.operations;

import expression.PartOfExpression;
import expression.parts.Matrix;
import expression.parts.Type;

public class Inverse extends UnaryOperation {
    public Inverse(PartOfExpression part) {
        super(part, "inverse");
    }

    @Override
    protected PartOfExpression calculate(Type x) {
        if (!(x instanceof Matrix matrix)) {
            throw new IllegalArgumentException("This operation only for matrices");
        }
        matrix.inverse();
        return matrix;
    }
}
