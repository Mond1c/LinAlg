package org.linalg.expression.operations;


import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parts.Const;
import org.linalg.expression.parts.Type;

public class Multiply extends BinaryOperation {
    public Multiply(PartOfExpression left, PartOfExpression right) {
        super(left, right, "*", 2);
    }

    @Override
    protected PartOfExpression calculate(Type x, Type y) {
        return x.multiply(y);
    }

    @Override
    public PartOfExpression diff() {
        return new Add(new Multiply(left.diff(), right), new Multiply(left, right.diff()));
    }

    @Override
    public PartOfExpression simplify() {
        PartOfExpression l = left.simplify();
        PartOfExpression r = right.simplify();
        if (l instanceof Const lhs && lhs.equals(Const.ZERO)
            || r instanceof Const rhs && rhs.equals(Const.ZERO)) {
            return Const.ZERO;
        }
        if (l instanceof Const lhs && lhs.equals(Const.ONE)) {
            return r;
        }
        if (r instanceof Const rhs && rhs.equals(Const.ONE)) {
            return l;
        }
        return super.simplify();
    }
}
