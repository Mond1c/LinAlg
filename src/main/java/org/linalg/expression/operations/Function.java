package org.linalg.expression.operations;

import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Const;
import org.linalg.expression.parts.Type;

import java.math.BigDecimal;

public class Function extends UnaryOperation {
    private final java.util.function.Function<BigDecimal, BigDecimal> function;

    public Function(PartOfExpression part, String operation, java.util.function.Function<BigDecimal, BigDecimal> function) {
        super(part, operation);
        this.function = function;
    }

    @Override
    protected PartOfExpression calculate(Type x) {
        if (x instanceof Const c) {
            return new Const(function.apply(c.value()));
        }
        throw new IllegalArgumentException("Functions are only for constants");
    }
}
