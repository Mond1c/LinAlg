package expression.operations;

import expression.PartOfExpression;
import expression.parts.Const;
import expression.parts.Matrix;
import expression.parts.Type;

public class Rank extends UnaryOperation {
    public Rank(PartOfExpression part) {
        super(part, "rank");
    }

    @Override
    protected PartOfExpression calculate(Type x) {
        if (!(x instanceof Matrix matrix)) {
            throw new IllegalArgumentException("This operation only for matrices");
        }
        return new Const(matrix.rank());
    }
}
