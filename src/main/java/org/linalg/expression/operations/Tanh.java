package org.linalg.expression.operations;

import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Const;
import org.linalg.math.BigDecimalMath;

import java.math.BigDecimal;

public class Tanh extends Function {
    public Tanh(PartOfExpression part) {
        super(part, "tanh", BigDecimalMath::tanh);
    }

    @Override
    public PartOfExpression diff() {
        return new Divide(Const.ONE, new Multiply(new Cosh(part), new Cosh(part)));
    }
}
