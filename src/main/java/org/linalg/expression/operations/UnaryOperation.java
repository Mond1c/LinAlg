package org.linalg.expression.operations;


import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Type;

import java.math.BigDecimal;

public abstract class UnaryOperation implements PartOfExpression {
    protected final PartOfExpression part;
    private final String operation;
    private final int priority;

    public UnaryOperation(PartOfExpression part, String operation, int priority) {
        this.part = part;
        this.operation = operation;
        this.priority = priority;
    }

    protected abstract PartOfExpression calculate(Type x);

    @Override
    public PartOfExpression evaluate(BigDecimal x) {
        if (!(part.evaluate(x) instanceof Type p)) {
            throw new IllegalArgumentException("Can't make operation with not Type classes");
        }
        return calculate(p);
    }

    @Override
    public String toString() {
        return "(" + operation + " " + part.toString() + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof UnaryOperation otherOperation)) {
            return false;
        }
        return otherOperation.operation.equals(operation) && part.equals(otherOperation.part);
    }

    @Override
    public String toMiniString() {
        if (!(part instanceof BinaryOperation binaryOperation) || binaryOperation.getPriority() > priority) {
            return operation + " " + part.toMiniString();
        }
        return operation + "(" + part.toMiniString() + ")";
    }
}
