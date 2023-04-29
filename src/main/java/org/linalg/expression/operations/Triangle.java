package org.linalg.expression.operations;


import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Matrix;
import org.linalg.expression.parts.Type;

public class Triangle extends UnaryOperation {

    public Triangle(PartOfExpression part) {
        super(part, "triangle");
    }

    @Override
    protected PartOfExpression calculate(Type x) {
        if (!(x instanceof Matrix matrix)) {
            throw new IllegalArgumentException("This operation only for matrices");
        }
        matrix.triangle();
        return matrix;
    }

    @Override
    public PartOfExpression diff() {
        throw new UnsupportedOperationException("You can't diff this operation");
    }
}
