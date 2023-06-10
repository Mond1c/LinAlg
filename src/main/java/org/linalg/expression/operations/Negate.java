package org.linalg.expression.operations;


import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Type;

public class Negate extends UnaryOperation {
    public Negate(PartOfExpression part) {
        super(part, "-", 3);
    }

    @Override
    protected PartOfExpression calculate(Type x) {
        return x.negate();
    }

    @Override
    public PartOfExpression diff() {
        return new Negate(part.diff());
    }

    @Override
    public PartOfExpression simplify() {
        return new Negate(super.simplify());
    }
}
