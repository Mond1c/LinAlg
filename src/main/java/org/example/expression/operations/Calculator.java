package org.example.expression.operations;


import org.example.expression.parts.Matrix;

public class Calculator {
    public static Matrix solveSystemOfEquations(Matrix a, Matrix b) {
        if (a.rows() != b.rows()) {
            throw new IllegalArgumentException("Count of rows of them matrix B must be equal to count of rows of the matrix A");
        } else if (b.cols() != 1) {
            throw new IllegalArgumentException("Count of columns of the matrix B must be equal 1");
        }
        Matrix c = new Matrix(a.rows(), a.cols() + b.cols());
        for (int i = 0; i < a.rows(); i++) {
            for (int j = 0; j < a.cols(); j++) {
                c.set(i, j, a.get(i, j));
            }
        }
        for (int i = 0; i < a.rows(); i++) {
            c.set(i, a.cols(), b.get(i, 0));
        }
        c.triangle();
        return c;
    }
}
