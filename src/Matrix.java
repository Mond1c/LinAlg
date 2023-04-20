import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Matrix {
    private int n;
    private int m;
    private double[][] data;

    public Matrix() {

    }

    public Matrix(int n, int m) {
        this.n = n;
        this.m = m;
        this.data = new double[n][m];
    }

    public Matrix(double[][] data) {
        this.data = data;
    }

    public double get(int row, int column) {
        if (row < 0 || row >= n || column < 0 || column > m) {
            throw new IndexOutOfBoundsException("Index out of bounds (row, column) = (" + row + ", " + column + ")");
        }
        return data[row][column];
    }

    public void set(int row, int column, double newValue) {
        if (row < 0 || row >= n || column < 0 || column > m) {
            throw new IndexOutOfBoundsException("Index out of bounds (row, column) = (" + row + ", " + column + ")");
        }
        data[row][column] = newValue;
    }

    private double[][] copyData() {
        double[][] result = new double[n][m];
        for (int i = 0; i < n; i++) {
            result[i] = Arrays.copyOf(data[i], m);
        }
        return result;
    }

    private double determinant(double[][] a, int n) {
        double det = 1, total = 1;

        for (int i = 0; i < n; i++) {
            int index = i;
            while (index < n && a[index][i] == 0) {
                index++;
            }
            if (index == n) {
                continue;
            }
            if (index != i) {
                for (int j = 0; j < n; j++) {
                    double tmp = a[index][j];
                    a[index][j] = a[i][j];
                    a[i][j] = tmp;
                }
                det = det * ((index - i) % 2 == 0 ? 1 : -1);
            }
            double[] temp = Arrays.copyOf(a[i], a[i].length);

            for (int j = i + 1; j < n; j++) {
                double num1 = temp[i];
                double num2 = a[j][i];
                for (int k = 0; k < n; k++) {
                    a[j][k] = (num1 * a[j][k]) - (num2 * temp[k]);
                }
                total *= num1;
            }
        }

        for (int i = 0; i < n; i++) {
            det = det * a[i][i];
        }
        return det / total;
    }

    private double[][] getCofactor(double[][] a, int p, int q, int n) {
        int i = 0, j = 0;
        double[][] ans = new double[n][n];
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

    private double[][] getAdj(double[][] a, int n) {
        double[][] ans = new double[n][n];
        if (n == 1) {
            return ans;
        }
        double sign = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double[][] temp = getCofactor(a, i, j, n);
                sign = ((i + j) % 2 == 0) ? 1 : -1;
                ans[j][i] = (sign) * determinant(temp, n - 1);
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
        double[][] temp = copyData();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                data[j][i] = temp[i][j];
            }
        }
    }

    public double determinant() {
        if (n != m) {
            throw new IllegalArgumentException("Can't find determinant of non-square matrix " + n + " != " + m);
        }
        return determinant(copyData(), n);
    }

    public void inverse() {
        if (n != m) {
            throw new IllegalArgumentException("Can't find inverse matrix of non-square matrix " + n + " != " + m);
        }
        double det = determinant();
        if (det == 0) {
            throw new IllegalArgumentException("Can't find inverse matrix with determinant equals 0");
        }
        double[][] adj = getAdj(copyData(), n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                data[i][j] = adj[i][j] / det;
            }
        }
    }

    int rank() {
        int rank = 0;
        double[][] a = copyData();
        boolean[] selected_row = new boolean[n];
        for (int i = 0; i < m; i++) {
            int j;
            for (j = 0; j < n; j++) {
                if (!selected_row[j] && Math.abs(a[j][i]) > 1E-9) {
                    break;
                }
            }

            if (j != n) {
                rank++;
                selected_row[j] = true;
                for (int k = i + 1; k < m; k++) {
                    a[j][k] /= a[j][i];
                }
                for (int k = 0; k < n; k++) {
                    if (k != j && Math.abs(a[k][i]) > 1E-9) {
                        for (int p = i + 1; p < m; p++) {
                            a[k][p] -= a[j][p] * a[k][i];
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
                double coff = data[j][i] / data[i][i];
                for (int k = 0; k < m; k++) {
                    data[j][k] -= coff * data[i][k];
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
                result.data[i][j] = data[i][j] + other.data[i][j];
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
                result.data[i][j] = data[i][j] - other.data[i][j];
            }
        }
        return result;
    }

    public Matrix multiply(double scalar) {
        Matrix result = new Matrix(n, m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                result.data[i][j] = data[i][j] * scalar;
            }
        }
        return result;
    }

    public Matrix multiply(Matrix other) {
        Matrix result = new Matrix(n, other.m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < other.m; j++) {
                result.data[i][j] = 0;
                for (int k = 0; k < m; k++) {
                    result.data[i][j] += data[i][k] * other.data[k][j];
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
                maxLength = Math.max(maxLength, Double.toString(data[i][j]).length());
            }
        }
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (j != 0) {
                    builder.append("| ");
                }
                builder.append(data[i][j]);
                builder.append(" ".repeat(Math.max(0, maxLength - Double.toString(data[i][j]).length() + 1)));
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
                if (matrix.data[i][j] != data[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void readMatrix(Matrix matrix, final Scanner scanner) {
        for (int i = 0; i < matrix.n; i++) {
            for (int j = 0; j < matrix.m; j++) {
                matrix.data[i][j] = scanner.nextDouble();
            }
        }
    }
}
