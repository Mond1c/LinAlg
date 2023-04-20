import java.util.ArrayList;
import java.util.List;

public class Parser {
    private ParserMode mode;
    private String expression;
    private List<String> parts;

    private final static List<Character> OPERATIONS = List.of('+', '-', '*', '/', '=');

    private List<String> split(String str, List<Character> separators, boolean saveSeparator) {
        final StringBuilder builder = new StringBuilder();
        List<String> ans = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            if (!separators.contains(str.charAt(i)) ||
                    str.charAt(i) == '-' && i + 1 < str.length() && Character.isDigit(str.charAt(i + 1))) {
                builder.append(str.charAt(i));
            } else {
                if (!builder.isEmpty()) {
                    ans.add(builder.toString());
                    builder.setLength(0);
                }
                if (saveSeparator) {
                    ans.add(String.valueOf(str.charAt(i)));
                }
            }
        }
        if (!builder.isEmpty()) {
            ans.add(builder.toString());
        }
        return ans;
    }

    public String parse(String expression) {
        this.expression = expression.trim();
        detectMode();
        parts = split(this.expression, OPERATIONS, true);

        return switch (mode) {
            case MATRIX_CALCULATION -> matrixCalculation();
            case FIND_MATRIX_DETERMINANT -> findDeterminant();
            case INVERSE_MATRIX -> inverse();
            case TRANSPOSE_MATRIX -> transpose();
            case RANK_MATRIX -> rank();
            case TRIANGLE_MATRIX -> triangle();
            case SOLVE_SYSTEM_OF_EQUATIONS -> solve();
            default -> throw new UnsupportedOperationException("Unsupported operation");
        };
    }

    private Matrix parseMatrix(String str) {
        str = str.trim();
        int bracketCount = 0;
        for (char ch : str.toCharArray()) {
            if (ch == '{') {
                bracketCount++;
            } else {
                break;
            }
        }
        if (bracketCount > 2) {
            throw new IllegalArgumentException("It's not a matrix! " + str);
        }
        int n = Math.max(1, str.length() - str.replace("{", "").length() - 1);
        int m = 0;
        for (int i = 2; i < str.length(); i++) {
            if (str.charAt(i) == '}') {
                break;
            } else if (str.charAt(i) == ',') {
                m++;
            }
        }
        m++;
        Matrix matrix = new Matrix(n, m);
        str = str.replace("{", "");
        str = str.replace("}", "");
        List<String> elements = split(str, List.of(','), false);
        int i = 0;
        int j = 0;
        for (String element : elements) {
            matrix.set(i, j++, Double.parseDouble(element));
            if (j == m) {
                i++;
                j = 0;
            }
        }
        return matrix;
    }

    private String solve() {
        if (parts.size() != 3) {
            throw new IllegalArgumentException("To solve system you need to write: A = B where A and B are matrices");
        }
        Matrix a = parseMatrix(parts.get(0));
        Matrix b = parseMatrix(parts.get(2));
        return Calculator.solveSystemOfEquations(a, b);
    }

    private String operation(Matrix a, Matrix b, char operation) {
        return switch (operation) {
            case '+' -> a.add(b).toString();
            case '-' -> a.subtract(b).toString();
            case '*' -> a.multiply(b).toString();
            case '=' -> a.equals(b) ? "true" : "false";
            default -> throw new IllegalArgumentException("Invalid operation: " + operation);
        };
    }

    private double scalarCalculation() {
        if (parts.size() != 3) {
            throw new IllegalArgumentException("To make calculation with scalars you need to write: a +/-/*// b where a and b are scalars");
        }
        double a = Double.parseDouble(parts.get(0));
        double b = Double.parseDouble(parts.get(2));
        return switch (parts.get(1).charAt(0)) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> a / b;
            default -> throw new UnsupportedOperationException("Unsupported operation between scalar and scalar");
        };
    }

    private String matrixCalculation() {
        if (parts.size() != 3) {
            throw new IllegalArgumentException("To make calculation with matrices you need to write: A +/-/* B where A and B are matrices");
        }
        boolean aIsMatrix = parts.get(0).contains("{");
        boolean bIsMatrix = parts.get(2).contains("{");
        if (aIsMatrix && bIsMatrix) {
            Matrix a = parseMatrix(parts.get(0));
            Matrix b = parseMatrix(parts.get(2));
            return operation(a, b, parts.get(1).charAt(0));
        } else if (aIsMatrix || bIsMatrix) {
            if (aIsMatrix) {
                Matrix a = parseMatrix(parts.get(0));
                double b = Double.parseDouble(parts.get(2));
                if (!parts.get(1).equals("*")) {
                    throw new UnsupportedOperationException("Unsupported operation between scalar and matrix");
                }
                return a.multiply(b).toString();
            }
            double a = Double.parseDouble(parts.get(0));
            Matrix b = parseMatrix(parts.get(2));
            if (!parts.get(1).equals("*")) {
                throw new UnsupportedOperationException("Unsupported operation between scalar and matrix");
            }
            return b.multiply(a).toString();
        }
        return String.valueOf(scalarCalculation());
    }

    private String findDeterminant() {
        if (parts.size() != 1) {
            throw new IllegalArgumentException("To find determinant of a matrix you need to write: det A where A is the matrix");
        }
        return String.valueOf(parseMatrix(parts.get(0)).determinant());
    }

    private String inverse() {
        if (parts.size() != 1) {
            throw new IllegalArgumentException("To inverse a matrix you need to write: inverse A where A is the matrix");
        }
        Matrix a = parseMatrix(parts.get(0));
        a.inverse();
        ;
        return a.toString();
    }

    private String transpose() {
        if (parts.size() != 1) {
            throw new IllegalArgumentException("To transpose a matrix you need to write: transpose A where A is the matrix");
        }
        Matrix a = parseMatrix(parts.get(0));
        a.transpose();
        return a.toString();
    }

    private String rank() {
        if (parts.size() != 1) {
            throw new IllegalArgumentException("To find rank of a matrix you need to write: rank A where A is the matrix");
        }
        Matrix a = parseMatrix(parts.get(0));
        return String.valueOf(a.rank());
    }

    private String triangle() {
        if (parts.size() != 1) {
            throw new IllegalArgumentException("To triangle a matrix you need to write: triangle A where A is the matrix");
        }
        Matrix a = parseMatrix(parts.get(0));
        a.triangle();
        return a.toString();
    }

    private void detectMode() {
        if (expression.contains("det")) {
            mode = ParserMode.FIND_MATRIX_DETERMINANT;
        } else if (expression.contains("inverse")) {
            mode = ParserMode.INVERSE_MATRIX;
        } else if (expression.contains("transpose")) {
            mode = ParserMode.TRANSPOSE_MATRIX;
        } else if (expression.contains("rank")) {
            mode = ParserMode.RANK_MATRIX;
        } else if (expression.contains("triangle")) {
            mode = ParserMode.TRIANGLE_MATRIX;
        } else if (expression.contains("solve")) {
            mode = ParserMode.SOLVE_SYSTEM_OF_EQUATIONS;
        } else {
            mode = ParserMode.MATRIX_CALCULATION;
            return;
        }
        this.expression = expression.replace(mode.toString(), "");
    }
}
