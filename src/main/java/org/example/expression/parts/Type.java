package org.example.expression.parts;

import org.example.expression.PartOfExpression;

public interface Type {
    PartOfExpression add(Type other);
    PartOfExpression subtract(Type other);
    PartOfExpression multiply(Type other);
    PartOfExpression divide(Type other);
    PartOfExpression negate();
}
