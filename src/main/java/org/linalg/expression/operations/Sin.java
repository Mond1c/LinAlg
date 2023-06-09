package org.linalg.expression.operations;

import org.linalg.expression.PartOfExpression;
import org.linalg.math.BigDecimalMath;

public class Sin extends Function {
    public Sin(PartOfExpression part) {
        super(part, "sin", BigDecimalMath::sin);
    }

    @Override
    public PartOfExpression diff() {
        if (containsVariable(this)) {
            return new Multiply(new Cos(part), part.diff());
        }
        return new Cos(part);
    }

    @Override
    public PartOfExpression simplify() {
        return new Sin(super.simplify());
    }
}
