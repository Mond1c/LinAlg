package org.linalg.expression.operations;

import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Const;
import org.linalg.math.BigDecimalMath;

import java.math.BigDecimal;

public class Atan extends Function {
    public Atan(PartOfExpression part) {
        super(part, "arctan", BigDecimalMath::atan);
    }

    @Override
    public PartOfExpression diff() {
        return new Divide(Const.ONE, new Add(Const.ONE, new Multiply(part, part)));
    }
}
