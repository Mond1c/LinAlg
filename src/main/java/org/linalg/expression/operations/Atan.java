package org.linalg.expression.operations;

import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Const;
import org.linalg.math.BigDecimalMath;

public class Atan extends Function {
    public Atan(PartOfExpression part) {
        super(part, "arctan", BigDecimalMath::atan);
    }

    @Override
    public PartOfExpression diff() {
        if (containsVariable(part)) {
            return new Divide(part.diff(), new Add(Const.ONE, new Multiply(part, part)));
        }
        return new Divide(Const.ONE, new Add(Const.ONE, new Multiply(part, part)));
    }
}
