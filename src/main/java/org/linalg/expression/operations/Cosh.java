package org.linalg.expression.operations;

import org.linalg.expression.PartOfExpression;
import org.linalg.math.BigDecimalMath;

public class Cosh extends Function {
    public Cosh(PartOfExpression part) {
        super(part, "cosh", BigDecimalMath::cosh);
    }

    @Override
    public PartOfExpression diff() {
        if (containsVariable(this)) {
            return new Multiply(new Sinh(part), part.diff());
        }
        return new Sinh(part);
    }
}
