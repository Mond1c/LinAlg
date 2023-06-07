package org.linalg.expression;

import java.math.BigDecimal;

public interface PartOfExpression {
    default PartOfExpression evaluate() {
        return this;
    }

    PartOfExpression evaluate(BigDecimal x);
    PartOfExpression diff();

    default String toMiniString() {
        return toString();
    }

    default String toLatexString() {
        return toString();
    }

    default PartOfExpression simplify() {
        return this;
    }
}
