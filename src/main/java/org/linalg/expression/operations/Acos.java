package org.linalg.expression.operations;

import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Const;
import org.linalg.math.BigDecimalMath;

import java.math.BigDecimal;

public class Acos extends Function {
    public Acos(PartOfExpression part) {
        super(part, "acos", BigDecimalMath::acos);
    }

    @Override
    public PartOfExpression diff() {
        return new Negate(new Divide(Const.ONE, new Sqrt(new Subtract(Const.ONE, new Multiply(part, part)))));
    }
}
