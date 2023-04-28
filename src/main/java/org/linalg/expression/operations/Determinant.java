package org.linalg.expression.operations;


import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Const;
import org.linalg.expression.parts.Matrix;
import org.linalg.expression.parts.Type;

public class Determinant extends UnaryOperation {
    public Determinant(PartOfExpression part) {
        super(part, "det");
    }

    @Override
    protected PartOfExpression calculate(Type x) {
        if (!(x instanceof Matrix matrix)) {
            throw new IllegalArgumentException("This operation only for matrices");
        }
        return new Const(matrix.determinant());
    }
}
