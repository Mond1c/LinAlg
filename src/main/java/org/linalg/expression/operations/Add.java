package org.linalg.expression.operations;


import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Type;

public class Add extends BinaryOperation {
    public Add(PartOfExpression left, PartOfExpression right) {
        super(left, right, "+", 1);
    }

    @Override
    protected PartOfExpression calculate(Type x, Type y) {
        return x.add(y);
    }

    @Override
    public PartOfExpression diff() {
        return new Add(left.diff(), right.diff());
    }
}
