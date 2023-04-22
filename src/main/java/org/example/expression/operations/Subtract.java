package org.example.expression.operations;


import org.example.expression.PartOfExpression;
import org.example.expression.parts.Type;

public class Subtract extends BinaryOperation {

    public Subtract(PartOfExpression left, PartOfExpression right) {
        super(left, right, "-");
    }

    @Override
    protected PartOfExpression calculate(Type x, Type y) {
        return x.subtract(y);
    }
}
