package org.linalg.expression.operations;


import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Matrix;
import org.linalg.expression.parts.Type;

public class Inverse extends UnaryOperation {
    public Inverse(PartOfExpression part) {
        super(part, "inverse", 5);
    }

    @Override
    protected PartOfExpression calculate(Type x) {
        if (!(x instanceof Matrix matrix)) {
            throw new IllegalArgumentException("This operation only for matrices");
        }
        matrix.inverse();
        return matrix;
    }

    @Override
    public PartOfExpression diff() {
        throw new UnsupportedOperationException("You can't diff this operation");
    }

    @Override
    public String toLatexString() {
        return part.toLatexString() + "^{-1}";
    }
}
