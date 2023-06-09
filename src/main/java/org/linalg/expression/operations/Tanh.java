package org.linalg.expression.operations;

import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Const;
import org.linalg.math.BigDecimalMath;

public class Tanh extends Function {
    public Tanh(PartOfExpression part) {
        super(part, "tanh", BigDecimalMath::tanh);
    }

    @Override
    public PartOfExpression diff() {
        if (containsVariable(this)) {
            return new Divide(part.diff(), new Multiply(new Cosh(part), new Cosh(part)));
        }
        return new Divide(Const.ONE, new Multiply(new Cosh(part), new Cosh(part)));
    }

    @Override
    public PartOfExpression simplify() {
        return new Tanh(super.simplify());
    }
}
