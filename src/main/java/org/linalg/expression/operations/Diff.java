package org.linalg.expression.operations;

import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Type;
import java.math.BigDecimal;

public class Diff extends UnaryOperation {
    public Diff(PartOfExpression part) {
        super(part, "diff", 5);
    }

    @Override
    public PartOfExpression evaluate(BigDecimal x) {
        return part.diff().evaluate(x);
    }

    @Override
    public PartOfExpression evaluate() {
        return part.diff();
    }

    @Override
    public PartOfExpression diff() {
        throw new UnsupportedOperationException("diff is an unsupported operation");
    }

    @Override
    protected PartOfExpression calculate(Type x) {
        throw new UnsupportedOperationException("calculate is an unsupported operation");
    }

    @Override
    public PartOfExpression simplify() {
        return new Diff(super.simplify());
    }
}
