package org.linalg.expression.operations;


import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Const;
import org.linalg.expression.parts.Matrix;
import org.linalg.expression.parts.Type;

public class Determinant extends UnaryOperation {
    public Determinant(PartOfExpression part) {
        super(part, "det", 5);
    }

    @Override
    protected PartOfExpression calculate(Type x) {
        if (!(x instanceof Matrix matrix)) {
            throw new IllegalArgumentException("This operation only for matrices");
        }
        return new Const(matrix.determinant());
    }

    @Override
    public PartOfExpression diff() {
        throw new UnsupportedOperationException("You can't diff this operation");
    }

    @Override
    public String toMiniString() {
        return "\\det" + part.toLatexString();
    }

    @Override
    public PartOfExpression simplify() {
        return new Determinant(super.simplify());
    }
}
