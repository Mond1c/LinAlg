package expression.operations;

import expression.PartOfExpression;
import expression.parts.Matrix;
import expression.parts.Type;

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
