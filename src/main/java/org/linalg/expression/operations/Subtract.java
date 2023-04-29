package org.linalg.expression.operations;


import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Type;

public class Subtract extends BinaryOperation {

    public Subtract(PartOfExpression left, PartOfExpression right) {
        super(left, right, "-");
    }

    @Override
    protected PartOfExpression calculate(Type x, Type y) {
        return x.subtract(y);
    }

    @Override
    public PartOfExpression diff() {
        return new Subtract(left.diff(), right.diff());
    }
}
