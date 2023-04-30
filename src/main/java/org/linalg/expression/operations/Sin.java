package org.linalg.expression.operations;

import org.linalg.expression.PartOfExpression;
import org.linalg.math.BigDecimalMath;

import java.math.BigDecimal;

public class Sin extends Function {
    public Sin(PartOfExpression part) {
        super(part, "sin", BigDecimalMath::sin);
    }

    @Override
    public PartOfExpression diff() {
        return new Cos(part);
    }
}
