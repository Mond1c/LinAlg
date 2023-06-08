package org.linalg.expression.operations;

import org.linalg.expression.PartOfExpression;
import org.linalg.math.BigDecimalMath;


public class Cos extends Function {
    public Cos(PartOfExpression part) {
        super(part, "cos", BigDecimalMath::cos);
    }

    @Override
    public PartOfExpression diff() {
        if (containsVariable(part)) {
            return new Multiply(new Negate(new Sin(part)), part.diff());
        }
        return new Negate(new Sin(part));
    }

    @Override
    public PartOfExpression simplify() {
        return new Cos(super.simplify());
    }
}
