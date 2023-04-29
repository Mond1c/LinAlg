package org.linalg.expression;

import java.math.BigDecimal;

public interface PartOfExpression {
    PartOfExpression evaluate(BigDecimal x);
    PartOfExpression diff();
}
