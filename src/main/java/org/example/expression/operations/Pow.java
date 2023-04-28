package org.example.expression.operations;

import org.example.expression.PartOfExpression;
import org.example.expression.parts.Const;
import org.example.expression.parts.Matrix;
import org.example.expression.parts.Type;

public class Pow extends BinaryOperation {

    public Pow(PartOfExpression left, PartOfExpression right) {
        super(left, right, "pow");
    }

    @Override
    protected PartOfExpression calculate(Type x, Type y) {
        if (!(y instanceof Const p)) {
            throw new IllegalArgumentException("Power can be only constant");
        }
        if (x instanceof Const c) {
            return c.pow(p);
        }
        if (x instanceof Matrix m) {
            return m.pow(p);
        }
        throw new IllegalArgumentException("You can power only const and matrix");
    }
}
