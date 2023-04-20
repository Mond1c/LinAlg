package expression.operations;

import expression.PartOfExpression;
import expression.parts.Const;
import expression.parts.Matrix;
import expression.parts.Type;

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
