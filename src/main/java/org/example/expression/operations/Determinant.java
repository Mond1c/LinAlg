package org.example.expression.operations;


import org.example.expression.PartOfExpression;
import org.example.expression.parts.Const;
import org.example.expression.parts.Matrix;
import org.example.expression.parts.Type;

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
