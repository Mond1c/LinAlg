package org.linalg.expression.operations;

import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Const;
import org.linalg.math.BigDecimalMath;

public class Asin extends Function {
    public Asin(PartOfExpression part) {
        super(part, "arcsin", BigDecimalMath::asin);
    }

    @Override
    public PartOfExpression diff() {
        if (containsVariable(part)) {
            return new Multiply(new Divide(Const.ONE, new Sqrt(new Subtract(Const.ONE, new Multiply(part, part)))), part.diff());
        }
        return new Divide(Const.ONE, new Sqrt(new Subtract(Const.ONE, new Multiply(part, part))));
    }

    @Override
    public PartOfExpression simplify() {
        return new Asin(super.simplify());
    }
}
