package org.linalg.expression.operations;


import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Type;

import java.math.BigDecimal;

public abstract class BinaryOperation implements PartOfExpression {
    protected final PartOfExpression left;
    protected final PartOfExpression right;
    protected final String operation;
    private final int priority;

    public BinaryOperation(final PartOfExpression left, final PartOfExpression right, final String operation, int priority) {
        this.left = left;
        this.right = right;
        this.operation = operation;
        this.priority = priority;
    }


    protected abstract PartOfExpression calculate(Type x, Type y);

    @Override
    public PartOfExpression evaluate(BigDecimal x) {
        if (!(left.evaluate(x) instanceof Type l) || !(right.evaluate(x) instanceof Type r)) {
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

    private boolean isBracketNeededForLeftSide() {
        if (!(left instanceof BinaryOperation binaryOperation)) {
            return false;
        }
        return binaryOperation.priority < priority;
    }

    private boolean isBracketNeededForRightSide() {
        if (!(right instanceof BinaryOperation binaryOperation)) {
            return false;
        }
        return binaryOperation.priority < priority
                || (this instanceof Divide)
                || (this instanceof Subtract || right instanceof Divide)
                && priority == binaryOperation.priority;
    }

    @Override
    public String toMiniString() {
        final StringBuilder builder = new StringBuilder();
        boolean leftSide = isBracketNeededForLeftSide();
        boolean rightSide = isBracketNeededForRightSide();
        if (leftSide) {
            builder.append('(');
        }
        builder.append(left.toMiniString());
        if (leftSide) {
            builder.append(')');
        }
        builder.append(' ').append(operation).append(' ');
        if (rightSide) {
            builder.append('(');
        }
        builder.append(right.toMiniString());
        if (rightSide) {
            builder.append(')');
        }
        return builder.toString();
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toLatexString() {
        return left.toLatexString() + " " + operation + " " + right.toLatexString();
    }
}
