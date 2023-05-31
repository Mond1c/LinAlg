package org.linalg.expression.operations;

import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Const;
import org.linalg.math.BigDecimalMath;

public class Sqrt extends Function {
    public Sqrt(PartOfExpression part) {
        super(part, "sqrt", BigDecimalMath::sqrt);
    }

    @Override
    public PartOfExpression diff() {
        return new Divide(part.diff(), new Multiply(Const.TWO, new Sqrt(part)));
    }

    @Override
    public String toLatexString() {
        return "\\sqrt{" + part.toLatexString() + "}";
    }
}
