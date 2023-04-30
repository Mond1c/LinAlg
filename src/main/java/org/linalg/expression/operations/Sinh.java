package org.linalg.expression.operations;

import org.linalg.expression.PartOfExpression;
import org.linalg.math.BigDecimalMath;

import java.math.BigDecimal;

public class Sinh extends Function {
    public Sinh(PartOfExpression part) {
        super(part, "sinh", BigDecimalMath::sinh);
    }

    @Override
    public PartOfExpression diff() {
        return new Cosh(part);
    }
}
