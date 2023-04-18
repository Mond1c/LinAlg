public class Calculator {
    public static String solveSystemOfEquations(Matrix a, Matrix b) {
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
        final StringBuilder ans = new StringBuilder();
        final StringBuilder builder = new StringBuilder();
        for (int i = c.rows() - 1; i >= 0; i--) {
            builder.setLength(0);
            for (int j = 0; j < c.cols() - 1; j++) {
                if (c.get(i, j) != 0) {
                    builder.append(c.get(i, j)).append(" * x").append(j + 1).append(" ");
                }
            }
            if (!builder.isEmpty()) {
                builder.append('=').append(c.get(i, c.cols() - 1)).append('\n');
                ans.append(builder);
            }
        }
        return ans.toString();
    }
}
