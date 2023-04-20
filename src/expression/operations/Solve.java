package expression.operations;

import expression.PartOfExpression;
import expression.parts.Matrix;
import expression.parts.Type;

public class Solve extends BinaryOperation {
    public Solve(PartOfExpression left, PartOfExpression right) {
        super(left, right, "solve");
    }

    @Override
    protected PartOfExpression calculate(Type x, Type y) {
        if (!(x instanceof Matrix matrix1) || !(y instanceof Matrix matrix2)) {
            throw new IllegalArgumentException("You can solve system of equations only with two matrcies");
        }
        return Calculator.solveSystemOfEquations(matrix1, matrix2);
    }
}
