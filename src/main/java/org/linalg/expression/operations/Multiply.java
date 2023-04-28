package org.linalg.expression.operations;


import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Type;

public class Multiply extends BinaryOperation {
    public Multiply(PartOfExpression left, PartOfExpression right) {
        super(left, right, "*");
    }

    @Override
    protected PartOfExpression calculate(Type x, Type y) {
        return x.multiply(y);
    }
}
