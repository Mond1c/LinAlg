package org.linalg.expression.operations;

import org.linalg.expression.PartOfExpression;
import org.linalg.math.BigDecimalMath;

public class Sinh extends Function {
    public Sinh(PartOfExpression part) {
        super(part, "sinh", BigDecimalMath::sinh);
    }

    @Override
    public PartOfExpression diff() {
        if (containsVariable(this)) {
            return new Multiply(new Cosh(part), part.diff());
        }
        return new Cosh(part);
    }
}
