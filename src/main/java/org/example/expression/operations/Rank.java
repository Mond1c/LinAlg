package org.example.expression.operations;


import org.example.expression.PartOfExpression;
import org.example.expression.parts.Const;
import org.example.expression.parts.Matrix;
import org.example.expression.parts.Type;

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
}
