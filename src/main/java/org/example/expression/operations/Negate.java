package org.example.expression.operations;


import org.example.expression.PartOfExpression;
import org.example.expression.parts.Type;

public class Negate extends UnaryOperation {
    public Negate(PartOfExpression part) {
        super(part, "-");
    }

    @Override
    protected PartOfExpression calculate(Type x) {
        return x.negate();
    }
}
