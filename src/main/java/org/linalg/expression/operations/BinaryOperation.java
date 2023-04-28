package org.linalg.expression.operations;


import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Type;

public abstract class BinaryOperation implements PartOfExpression {
    protected final PartOfExpression left;
    protected final PartOfExpression right;
    protected final String operation;

    public BinaryOperation(final PartOfExpression left, final PartOfExpression right, final String operation) {
        this.left = left;
        this.right = right;
        this.operation = operation;
    }


    protected abstract PartOfExpression calculate(Type x, Type y);

    @Override
    public PartOfExpression evaluate() {
        if (!(left.evaluate() instanceof Type l) || !(right.evaluate() instanceof Type r)) {
            throw new IllegalArgumentException("Can't make operation with not Type classes");
        }
        return calculate(l, r);
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " " + operation + " " + right.toString() + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof BinaryOperation otherOperation)) {
            return false;
        }
        return otherOperation.operation.equals(operation) && left.equals(otherOperation.left) && right.equals(otherOperation.right);
    }
}
