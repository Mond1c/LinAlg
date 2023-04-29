package org.linalg.expression.operations;


import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Const;
import org.linalg.expression.parts.Matrix;
import org.linalg.expression.parts.Type;

import java.math.BigDecimal;

public class Rank extends UnaryOperation {
    public Rank(PartOfExpression part) {
        super(part, "rank");
    }

    @Override
    protected PartOfExpression calculate(Type x) {
        if (!(x instanceof Matrix matrix)) {
            throw new IllegalArgumentException("This operation only for matrices");
        }
        return new Const(BigDecimal.valueOf(matrix.rank()));
    }

    @Override
    public PartOfExpression diff() {
        throw new UnsupportedOperationException("You can't diff this operation");
    }
}
