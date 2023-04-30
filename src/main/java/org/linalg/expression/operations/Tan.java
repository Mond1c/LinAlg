package org.linalg.expression.operations;

import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Const;
import org.linalg.math.BigDecimalMath;

import java.math.BigDecimal;

public class Tan extends Function {
    public Tan(PartOfExpression part) {
        super(part, "tan", BigDecimalMath::tan);
    }

    @Override
    public PartOfExpression diff() {
        return new Divide(Const.ONE, new Multiply(new Cos(part), new Cos(part)));
    }
}
