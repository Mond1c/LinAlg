package org.linalg.expression.operations;

import org.linalg.expression.PartOfExpression;
import org.linalg.math.BigDecimalMath;

import java.math.BigDecimal;

public class Cos extends Function {
    public Cos(PartOfExpression part) {
        super(part, "cos", BigDecimalMath::cos);
    }

    @Override
    public PartOfExpression diff() {
        return new Negate(new Sin(part));
    }
}
