package org.example.expression.operations;


import org.example.expression.PartOfExpression;
import org.example.expression.parts.Matrix;
import org.example.expression.parts.Type;

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
