package org.linalg.expression.operations;

import org.linalg.expression.PartOfExpression;
import org.linalg.math.BigDecimalMath;

import java.math.BigDecimal;

public class Cosh extends Function {
    public Cosh(PartOfExpression part) {
        super(part, "cosh", BigDecimalMath::cosh);
    }

    @Override
    public PartOfExpression diff() {
        return new Sinh(part);
    }
}
