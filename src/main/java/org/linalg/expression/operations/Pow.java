package org.linalg.expression.operations;

import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Const;
import org.linalg.expression.parts.Matrix;
import org.linalg.expression.parts.Type;

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
