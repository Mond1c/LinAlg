package org.linalg.expression.operations;


import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Type;

public class Divide extends BinaryOperation {
    public Divide(PartOfExpression left, PartOfExpression right) {
        super(left, right, "/", 2);
    }

    @Override
    protected PartOfExpression calculate(Type x, Type y) {
        return x.divide(y);
    }

    @Override
    public PartOfExpression diff() {
        return new Divide(new Subtract(new Multiply(left.diff(), right), new Multiply(left, right.diff())),
                new Multiply(right, right));
    }
}
