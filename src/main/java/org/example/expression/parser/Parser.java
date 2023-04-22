package org.example.expression.parser;


import org.example.expression.PartOfExpression;
import org.example.expression.operations.*;
import org.example.expression.parts.Const;
import org.example.expression.parts.Matrix;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Parser extends BaseParser {


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
            return new Rank(parseMatrix());
        } else if (take("transpose")) {
            return new Transpose(parseTypesUnaryOperationsAndBrackets());
        } else if (take("triangle")) {
            return new Triangle(parseTypesUnaryOperationsAndBrackets());
        } else if (take("inverse")) {
            return new Inverse(parseTypesUnaryOperationsAndBrackets());
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
        PartOfExpression part = parseTypesUnaryOperationsAndBrackets();
        skipWhitespaces();
        while (true) {
            if (take('*')) {
                part = parseOperation("*", part, parseTypesUnaryOperationsAndBrackets());
            } else if (take('/')) {
                part = parseOperation("/", part, parseTypesUnaryOperationsAndBrackets());
            } else if (take("solve")) {
                part = parseOperation("solve", part, parseTypesUnaryOperationsAndBrackets());
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
            default -> throw error("Unsupported operation");
        };
    }
}
