package org.linalg.expression.parser;


import org.linalg.expression.PartOfExpression;
import org.linalg.expression.operations.*;
import org.linalg.expression.parts.Const;
import org.linalg.expression.parts.Matrix;
import org.linalg.expression.parts.Variable;
import org.linalg.math.BigDecimalMath;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser extends BaseParser {
    private static final Map<String, BigDecimal> CONSTANTS = new HashMap<>();

    static {
        CONSTANTS.put("pi", BigDecimal.valueOf(Math.PI));
        CONSTANTS.put("e", BigDecimal.valueOf(Math.E));
    }

    public PartOfExpression parse(String expression) {
        setSource(new StringSource(expression));
        final PartOfExpression expr = parseExpression();
        if (!eof()) {
            if (take(')')) {
                throw error("No opening parenthesis");
            }
            throw error("Unsupported character");
        }
        return expr;
    }

    private PartOfExpression parseCONSTANTS() {
        for (Map.Entry<String, BigDecimal> pair : CONSTANTS.entrySet()) {
            if (take(pair.getKey())) {
                return new Const(pair.getValue());
            }
        }
        return null;
    }

    private PartOfExpression parseTypesUnaryOperationsAndBrackets() {
        skipWhitespaces();
        if (take('(')) {
            PartOfExpression part = parseExpression();
            if (!take(')')) {
                throw error("No closing parenthesis");
            }
            return part;
        } else if (between('0', '9')) {
            return parseConst(false);
        } else if (take('-')) {
            if (between('0', '9')) {
                return parseConst(true);
            }
            return new Negate(parseTypesUnaryOperationsAndBrackets());
        } else if (take('{')) {
            return parseMatrix();
        } else if (take("det")) {
            return new Determinant(parseTypesUnaryOperationsAndBrackets());
        } else if (take("rank")) {
            return new Rank(parseTypesUnaryOperationsAndBrackets());
        } else if (take("transpose")) {
            return new Transpose(parseTypesUnaryOperationsAndBrackets());
        } else if (take("triangle")) {
            return new Triangle(parseTypesUnaryOperationsAndBrackets());
        } else if (take("inverse")) {
            return new Inverse(parseTypesUnaryOperationsAndBrackets());
        } else if (take("sin")) {
            return new Sin(parseTypesUnaryOperationsAndBrackets());
        } else if (take("cos")) {
            return new Cos(parseTypesUnaryOperationsAndBrackets());
        } else if (take("tan")) {
            return new Tan(parseTypesUnaryOperationsAndBrackets());
        } else if (take("asin")) {
            return new Asin(parseTypesUnaryOperationsAndBrackets());
        } else if (take("acos")) {
            return new Acos(parseTypesUnaryOperationsAndBrackets());
        } else if (take("atan")) {
            return new Atan(parseTypesUnaryOperationsAndBrackets());
        } else if (take("sinh")) {
            return new Sinh(parseTypesUnaryOperationsAndBrackets());
        } else if (take("cosh")) {
            return new Cosh(parseTypesUnaryOperationsAndBrackets());
        } else if (take("tanh")) {
            return new Tanh(parseTypesUnaryOperationsAndBrackets());
        } else if (take("log")) {
            return new Log(parseTypesUnaryOperationsAndBrackets());
        } else if (take('x')) {
            return new Variable("x");
        } else if (take("diff")) {
            return new Diff(parseTypesUnaryOperationsAndBrackets());
        } else if (take("sqrt")) {
            return new Sqrt(parseTypesUnaryOperationsAndBrackets());
        }

        {
            final PartOfExpression part = parseCONSTANTS();
            if (part != null) {
                return part;
            }
        }

        throw error("No argument or unexpected token");
    }

    private PartOfExpression parseExpression() {
        return parsePlusMinus();
    }

    private PartOfExpression parsePlusMinus() {
        PartOfExpression part = parseMulDiv();
        skipWhitespaces();
        while (test('+') || test('-')) {
            part = parseOperation(String.valueOf(take()), part, parseMulDiv());
        }
        return part;
    }

    private PartOfExpression parseMulDiv() {
        PartOfExpression part = parsePower();
        skipWhitespaces();
        while (true) {
            if (take('*')) {
                part = parseOperation("*", part, parsePower());
            } else if (take('/')) {
                part = parseOperation("/", part, parsePower());
            } else if (take("solve")) {
                part = parseOperation("solve", part, parsePower());
            } else {
                break;
            }
            skipWhitespaces();
        }
        return part;
    }

    private PartOfExpression parsePower() {
        PartOfExpression part = parseTypesUnaryOperationsAndBrackets();
        skipWhitespaces();
        while (true) {
            if (take("pow")) {
                part = parseOperation("pow", part, parseTypesUnaryOperationsAndBrackets());
            } else {
                break;
            }
            skipWhitespaces();
        }
        return part;
    }

    private Const parseConst(boolean minus) {
        final StringBuilder builder = new StringBuilder();
        if (minus) {
            builder.append('-');
        }
        while (between('0', '9') || test('.')) {
            builder.append(take());
        }
        skipWhitespaces();
        if (between('0', '9')) {
            throw error("Spaces in number");
        }
        return new Const(new BigDecimal(builder.toString()));
    }

    private Matrix parseMatrix() {
        List<BigDecimal> data = new ArrayList<>();
        int bracketCount = 1;
        int n = 1;
        int m = 0;
        boolean firstCloseBracket = false;
        while (bracketCount != 0 && !eof()) {
            if (take('}')) {
                bracketCount--;
                if (!firstCloseBracket) {
                    m++;
                }
                firstCloseBracket = true;
            } else if (take('{')) {
                bracketCount++;
            } else if (between('0', '9')) {
                data.add(parseConst(false).value());
            } else if (take('-')) {
                data.add(parseConst(true).value());
            } else if (take(',')) {
                if (!firstCloseBracket) {
                    m++;
                } else if (bracketCount == 1) {
                    n++;
                }
            } else {
                take();
            }

            if (bracketCount > 2) {
                throw error("You can use only two {");
            }
        }
        if (bracketCount != 0) {
            throw error("Expected {, but found " + ch);
        }
        BigDecimal[][] fixedData = new BigDecimal[n][m];
        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                fixedData[i][j] = data.get(k++);
            }
        }
        return new Matrix(fixedData);
    }

    private BinaryOperation parseOperation(final String operation, final PartOfExpression left, final PartOfExpression right) {
        return switch (operation) {
            case "+" -> new Add(left, right);
            case "-" -> new Subtract(left, right);
            case "*" -> new Multiply(left, right);
            case "/" -> new Divide(left, right);
            case "solve" -> new Solve(left, right);
            case "pow" -> new Pow(left, right);
            default -> throw error("Unsupported operation");
        };
    }
}
