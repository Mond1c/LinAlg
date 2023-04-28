package org.linalg.expression.operations;


import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Type;

public class Negate extends UnaryOperation {
    public Negate(PartOfExpression part) {
        super(part, "-");
    }

    @Override
    protected PartOfExpression calculate(Type x) {
        return x.negate();
    }
}