package org.linalg.expression.operations;


import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Matrix;
import org.linalg.expression.parts.Type;

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

    @Override
    public PartOfExpression diff() {
        throw new UnsupportedOperationException("You can't diff this operation");
    }
}
