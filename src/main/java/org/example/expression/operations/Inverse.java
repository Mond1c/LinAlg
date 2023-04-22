package org.example.expression.operations;


import org.example.expression.PartOfExpression;
import org.example.expression.parts.Matrix;
import org.example.expression.parts.Type;

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
