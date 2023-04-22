package org.example.expression.operations;


import org.example.expression.PartOfExpression;
import org.example.expression.parts.Matrix;
import org.example.expression.parts.Type;

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
}
