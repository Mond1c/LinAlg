package org.linalg.expression.parts;


import org.linalg.expression.PartOfExpression;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

public class Matrix implements Type, PartOfExpression {
    private int n;
    private int m;
    private BigDecimal[][] data;

    public Matrix() {

    }

    public Matrix(int n, int m) {
        this.n = n;
        this.m = m;
        this.data = new BigDecimal[n][m];
    }

    public Matrix(BigDecimal[][] data) {
        this.data = data;
        this.n = data.length;
        this.m = data[0].length;
    }

    public BigDecimal get(int row, int column) {
        if (row < 0 || row >= n || column < 0 || column > m) {
            throw new IndexOutOfBoundsException("Index out of bounds (row, column) = (" + row + ", " + column + ")");
        }
        return data[row][column];
    }

    public void set(int row, int column, BigDecimal newValue) {
        if (row < 0 || row >= n || column < 0 || column > m) {
            throw new IndexOutOfBoundsException("Index out of bounds (row, column) = (" + row + ", " + column + ")");
        }
        data[row][column] = newValue;
    }

    private BigDecimal[][] copyData() {
        BigDecimal[][] result = new BigDecimal[n][m];
        for (int i = 0; i < n; i++) {
            result[i] = Arrays.copyOf(data[i], m);
        }
        return result;
    }

    private BigDecimal determinant(BigDecimal[][] a, int n) {
        BigDecimal det = BigDecimal.valueOf(1), total = BigDecimal.valueOf(1);

        for (int i = 0; i < n; i++) {
            int index = i;
            while (index < n && a[index][i].equals(BigDecimal.ZERO)) {
                index++;
            }
            if (index == n) {
                continue;
            }
            if (index != i) {
                for (int j = 0; j < n; j++) {
                    BigDecimal tmp = a[index][j];
                    a[index][j] = a[i][j];
                    a[i][j] = tmp;
                }
                det = det.multiply(BigDecimal.valueOf((index - i) % 2 == 0 ? 1 : -1));
            }
            BigDecimal[] temp = Arrays.copyOf(a[i], a[i].length);

            for (int j = i + 1; j < n; j++) {
                BigDecimal num1 = temp[i];
                BigDecimal num2 = a[j][i];
                for (int k = 0; k < n; k++) {
                    a[j][k] = (num1.multiply(a[j][k])).subtract(num2.multiply(temp[k]));
                }
                total = total.multiply(num1);
            }
        }

        for (int i = 0; i < n; i++) {
            det = det.multiply(a[i][i]);
        }
        return det.divide(total);
    }

    private BigDecimal[][] getCofactor(BigDecimal[][] a, int p, int q, int n) {
        int i = 0, j = 0;
        BigDecimal[][] ans = new BigDecimal[n][n];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (row != p && col != q) {
                    ans[i][j++] = a[row][col];
                    if (j == n - 1) {
                        i++;
                        j = 0;
                    }
                }
            }
        }
        return ans;
    }

    private BigDecimal[][] getAdj(BigDecimal[][] a, int n) {
        BigDecimal[][] ans = new BigDecimal[n][n];
        if (n == 1) {
            return ans;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                BigDecimal[][] temp = getCofactor(a, i, j, n);
                BigDecimal sign = BigDecimal.valueOf(((i + j) % 2 == 0) ? 1 : -1);
                ans[j][i] = sign.multiply(determinant(temp, n - 1));
            }
        }
        return ans;
    }

    public int rows() {
        return n;
    }

    public int cols() {
        return m;
    }

    public void transpose() {
        BigDecimal[][] temp = copyData();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                data[j][i] = temp[i][j];
            }
        }
    }

    public BigDecimal determinant() {
        if (n != m) {
            throw new IllegalArgumentException("Can't find determinant of non-square matrix " + n + " != " + m);
        }
        return determinant(copyData(), n);
    }

    public void inverse() {
        if (n != m) {
            throw new IllegalArgumentException("Can't find inverse matrix of non-square matrix " + n + " != " + m);
        }
        BigDecimal det = determinant();
        if (det.equals(BigDecimal.ZERO)) {
            throw new IllegalArgumentException("Can't find inverse matrix with determinant equals 0");
        }
        BigDecimal[][] adj = getAdj(copyData(), n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                data[i][j] = adj[i][j].divide(det);
            }
        }
    }

    public int rank() {
        int rank = 0;
        BigDecimal[][] a = copyData();
        boolean[] selected_row = new boolean[n];
        for (int i = 0; i < m; i++) {
            int j;
            for (j = 0; j < n; j++) {
                if (!selected_row[j] && a[j][i].abs().compareTo(BigDecimal.valueOf(1E-9)) > 0) {
                    break;
                }
            }

            if (j != n) {
                rank++;
                selected_row[j] = true;
                for (int k = i + 1; k < m; k++) {
                    a[j][k] = a[j][k].divide(a[j][i]);
                }
                for (int k = 0; k < n; k++) {
                    if (k != j && a[k][i].abs().compareTo(BigDecimal.valueOf(1E-9)) > 0) {
                        for (int p = i + 1; p < m; p++) {
                            a[k][p] =  a[k][p].subtract(a[j][p].multiply(a[k][i]));
                        }
                    }
                }
            }
        }
        return rank;
    }

    public void triangle() {
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                BigDecimal coff = data[j][i].divide(data[i][i]);
                for (int k = 0; k < m; k++) {
                    data[j][k] = data[j][k].subtract(coff.multiply(data[i][k]));
                }
            }
        }
    }

    public Matrix add(Matrix other) {
        if (n != other.n || m != other.m) {
            throw new IllegalArgumentException("Matrices must have similar size");
        }
        Matrix result = new Matrix(n, m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                result.data[i][j] = data[i][j].add(other.data[i][j]);
            }
        }
        return result;
    }

    public Matrix subtract(Matrix other) {
        if (n != other.n || m != other.m) {
            throw new IllegalArgumentException("Matrices must have similar size");
        }
        Matrix result = new Matrix(n, m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                result.data[i][j] = data[i][j].subtract(other.data[i][j]);
            }
        }
        return result;
    }

    public Matrix multiply(Const scalar) {
        Matrix result = new Matrix(n, m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                result.data[i][j] = data[i][j].multiply(scalar.value());
            }
        }
        return result;
    }

    public Matrix multiply(Matrix other) {
        Matrix result = new Matrix(n, other.m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < other.m; j++) {
                result.data[i][j] = BigDecimal.ZERO;
                for (int k = 0; k < m; k++) {
                    result.data[i][j] = result.data[i][j].add(data[i][k].multiply(other.data[k][j]));
                }
            }
        }
        return result;
    }

    @Override
    public String toString() {
        int maxLength = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                maxLength = Math.max(maxLength, data[i][j].toString().length());
            }
        }
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (j != 0) {
                    builder.append("| ");
                }
                builder.append(data[i][j]);
                builder.append(" ".repeat(Math.max(0, maxLength - data[i][j].toString().length() + 1)));
            }
            if (i != n - 1) {
                builder.append('\n');
            }
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Matrix matrix)) {
            return false;
        }
        if (n != matrix.n || m != matrix.m) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!matrix.data[i][j].equals(data[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void readMatrix(Matrix matrix, final Scanner scanner) {
        for (int i = 0; i < matrix.n; i++) {
            for (int j = 0; j < matrix.m; j++) {
                matrix.data[i][j] = scanner.nextBigDecimal();
            }
        }
    }

    @Override
    public PartOfExpression add(Type other) {
        if (other instanceof Matrix matrix) {
            return add(matrix);
        }
        throw new IllegalArgumentException("You can add to the matrix only a matrix!");
    }

    @Override
    public PartOfExpression subtract(Type other) {
        if (other instanceof Matrix matrix) {
            return subtract(matrix);
        }
        throw new IllegalArgumentException("You can subtract to the matrix only a matrix!");
    }

    @Override
    public PartOfExpression multiply(Type other) {
        if (other instanceof Matrix matrix) {
            return multiply(matrix);
        } else if (other instanceof Const c) {
            return multiply(c);
        }
        throw new IllegalArgumentException("You can add to the matrix only a matrix or a const!");
    }

    @Override
    public PartOfExpression divide(Type other) {
        throw new IllegalArgumentException("Unsupported operation with matrices");
    }

    @Override
    public PartOfExpression negate() {
        Matrix result = new Matrix(n, m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                result.set(i, j, get(i, j).negate());
            }
        }
        return result;
    }

    private static Matrix pow(Matrix m, BigDecimal power) {
        if (power.toBigInteger().equals(BigInteger.ONE)) {
            return m;
        }
        if (power.toBigInteger().mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            return pow(m.multiply(m), power.divide(BigDecimal.valueOf(2)));
        }
        return pow(m, power.subtract(BigDecimal.ONE)).multiply(m);
    }

    @Override
    public PartOfExpression pow(Const power) {
        return pow(this, power.value());
    }

    @Override
    public PartOfExpression evaluate() {
        return this;
    }
}
