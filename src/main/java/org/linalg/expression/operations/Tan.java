package org.linalg.expression.operations;

import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Const;
import org.linalg.math.BigDecimalMath;

public class Tan extends Function {
    public Tan(PartOfExpression part) {
        super(part, "tan", BigDecimalMath::tan);
    }

    @Override
    public PartOfExpression diff() {
        if (containsVariable(this)) {
            return new Divide(part.diff(), new Multiply(new Cos(part), new Cos(part)));
        }
        return new Divide(Const.ONE, new Multiply(new Cos(part), new Cos(part)));
    }

    @Override
    public PartOfExpression simplify() {
        return new Tan(super.simplify());
    }
}
