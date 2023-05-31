package org.linalg.expression.operations;

import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Const;
import org.linalg.math.BigDecimalMath;

import java.math.BigDecimal;

public class Asin extends Function {
    public Asin(PartOfExpression part) {
        super(part, "arcsin", BigDecimalMath::asin);
    }

    @Override
    public PartOfExpression diff() {
        return new Divide(Const.ONE, new Sqrt(new Subtract(Const.ONE, new Multiply(part, part))));
    }
}
