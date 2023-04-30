package org.linalg.expression.operations;

import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Const;
import org.linalg.math.BigDecimalMath;

import java.math.BigDecimal;

public class Log extends Function {
    public Log(PartOfExpression part) {
        super(part, "log", BigDecimalMath::log);
    }

    @Override
    public PartOfExpression diff() {
        return new Divide(Const.ONE, part);
    }
}
