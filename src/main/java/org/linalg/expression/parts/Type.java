package org.linalg.expression.parts;

import org.linalg.expression.PartOfExpression;

public interface Type {
    PartOfExpression add(Type other);
    PartOfExpression subtract(Type other);
    PartOfExpression multiply(Type other);
    PartOfExpression divide(Type other);
    PartOfExpression negate();
    PartOfExpression pow(Const power);
}
