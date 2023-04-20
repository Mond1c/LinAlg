package expression.operations;

import expression.PartOfExpression;
import expression.parts.Matrix;
import expression.parts.Type;

public class Transpose extends UnaryOperation {
    public Transpose(PartOfExpression part) {
        super(part, "transpose");
    }

    @Override
    protected PartOfExpression calculate(Type x) {
        if (!(x instanceof Matrix matrix)) {
            throw new IllegalArgumentException("This operation only for matrices");
        }
        matrix.transpose();
        return matrix;
    }
}
