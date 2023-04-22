package org.example.expression.parts;


import org.example.expression.PartOfExpression;

import java.math.BigDecimal;

public record Const(BigDecimal value) implements PartOfExpression, Type {

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Const c) && value.equals(c.value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public PartOfExpression evaluate() {
        return this;
    }

    @Override
    public PartOfExpression add(Type other) {
        if (other instanceof Const c) {
            return new Const(value.add(c.value));
        }
        throw new IllegalArgumentException("You can add to the const only a const");
    }

    @Override
    public PartOfExpression subtract(Type other) {
        if (other instanceof Const c) {
            return new Const(value.subtract(c.value));
        }
        throw new IllegalArgumentException("You can subtract to the const only a const");
    }

    @Override
    public PartOfExpression multiply(Type other) {
        if (other instanceof Const c) {
            return new Const(value.multiply(c.value));
        } else if (other instanceof Matrix matrix) {
            return matrix.multiply(this);
        }
        throw new IllegalArgumentException("You can multiply to the const only a const or a matrix");
    }

    @Override
    public PartOfExpression divide(Type other) {
        if (other instanceof Const c) {
            return new Const(value.divide(c.value));
        }
        throw new IllegalArgumentException("You can divide to the const only a const");
    }

    @Override
    public PartOfExpression negate() {
        return new Const(value.negate());
    }
    
}
