package org.linalg.expression.operations;

import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Const;
import org.linalg.math.BigDecimalMath;


public class Acos extends Function {
    public Acos(PartOfExpression part) {
        super(part, "arccos", BigDecimalMath::acos);
    }

    @Override
    public PartOfExpression diff() {
        if (containsVariable(this)) {
            return new Multiply(new Negate(new Divide(Const.ONE, new Sqrt(new Subtract(Const.ONE, new Multiply(part, part))))), 
                part.diff());
        }
        return new Negate(new Divide(Const.ONE, new Sqrt(new Subtract(Const.ONE, new Multiply(part, part)))));
    }

    @Override
    public PartOfExpression simplify() {
        return new Acos(super.simplify());
    }
}
