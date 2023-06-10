package org.linalg.expression.operations;

import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Const;
import org.linalg.math.BigDecimalMath;

public class Log extends Function {
    public Log(PartOfExpression part) {
        super(part, "log", BigDecimalMath::log);
    }

    @Override
    public PartOfExpression diff() {
        if (containsVariable(part)) {
            return new Divide(part.diff(), part);
        }
        return new Divide(Const.ONE, part);
    }

    @Override
    public String toLatexString() {
        return "\\ln(" + part.toLatexString() + ")";
    }

    @Override
    public PartOfExpression simplify() {
        return new Log(super.simplify());
    }
}
