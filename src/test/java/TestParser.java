import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parser.Parser;
import org.linalg.expression.parts.Const;
import org.linalg.expression.parts.Matrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TestParser {
    private final static Parser PARSER = new Parser();
    

    @Test
    public void testConstExpression() {
        Assertions.assertEquals(new Const(BigDecimal.valueOf(4.8).divide(BigDecimal.ONE, 10, RoundingMode.HALF_UP)), PARSER.parse("1 + 2 + 3 / 5 * (1 + 2)").evaluate(BigDecimal.ZERO));

    }

    @Test
    public void testMatrixAdd() {
        final PartOfExpression expr = PARSER.parse("{{1, 2}, {3, 4}} + {{1, 2}, {3, 4}}");
        final Matrix ans = new Matrix(new BigDecimal[][]{{BigDecimal.TWO, BigDecimal.valueOf(4)},
                {BigDecimal.valueOf(6), BigDecimal.valueOf(8)}});

        Assertions.assertEquals(ans, expr.evaluate(BigDecimal.ZERO));
    }

    @Test
    public void testMatrixSubtract() {
        final PartOfExpression expr = PARSER.parse("{{1, 2}, {3, 4}} - {{4, 3}, {2, 1}}");
        final Matrix ans = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(-3), BigDecimal.valueOf(-1)},
                {BigDecimal.valueOf(1), BigDecimal.valueOf(3)}});

        Assertions.assertEquals(ans, expr.evaluate(BigDecimal.ZERO));
    }

    @Test
    public void testMatrixMultiplyByScalar() {
        final PartOfExpression expr = PARSER.parse("{{1, 2}, {3, 4}} * 10");
        final Matrix ans = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(10), BigDecimal.valueOf(20)},
                {BigDecimal.valueOf(30), BigDecimal.valueOf(40)}});

        Assertions.assertEquals(ans, expr.evaluate(BigDecimal.ZERO));
    }

    @Test
    public void testMatrixMultiplyByMatrix() {
        final PartOfExpression expr = PARSER.parse("{{1, 2}, {3, 4}} * {{1, 2}, {3, 4}}");
        final Matrix ans = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(7), BigDecimal.valueOf(10)},
                {BigDecimal.valueOf(15), BigDecimal.valueOf(22)}});

        Assertions.assertEquals(ans, expr.evaluate(BigDecimal.ZERO));
    }

    @Test
    public void testTranspose() {
        final PartOfExpression expr = PARSER.parse("transpose {{1, 2}, {3, 4}}");
        final Matrix ans = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(3)},
                {BigDecimal.valueOf(2), BigDecimal.valueOf(4)}});

        Assertions.assertEquals(ans, expr.evaluate(BigDecimal.ZERO));
    }

    @Test
    public void testInverse() {
        final PartOfExpression expr = PARSER.parse("inverse {{1, 2}, {3, 4}}");
        final Matrix ans = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(-2), BigDecimal.valueOf(1)},
                {BigDecimal.valueOf(1.5), BigDecimal.valueOf(-0.5)}});

        Assertions.assertEquals(ans, expr.evaluate(BigDecimal.ZERO));
    }

    @Test
    public void testDeterminant() {
        final PartOfExpression expr = PARSER.parse("det {{1, 2}, {3, 4}}");
        final Const ans = new Const(BigDecimal.TWO.negate());

        Assertions.assertEquals(ans, expr.evaluate(BigDecimal.ZERO));
    }

    @Test
    public void testRank() {
        final PartOfExpression expr = PARSER.parse("rank {{1, 2}, {3, 4}}");
        final Const ans = new Const(BigDecimal.TWO);

        Assertions.assertEquals(ans, expr.evaluate(BigDecimal.ZERO));
    }

    @Test
    public void testNegate() {
        final PartOfExpression expr = PARSER.parse("- {{1, 2}, {3, 4}}");
        final Matrix ans = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(-1), BigDecimal.valueOf(-2)},
                {BigDecimal.valueOf(-3), BigDecimal.valueOf(-4)}});

        Assertions.assertEquals(ans, expr.evaluate(BigDecimal.ZERO));
    }

    @Test
    public void testTriangle() {
        final PartOfExpression expr = PARSER.parse("triangle {{1, 2}, {3, 4}}");
        final Matrix ans = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(0), BigDecimal.valueOf(-2)}});

        Assertions.assertEquals(ans, expr.evaluate(BigDecimal.ZERO));
    }

    @Test
    public void testPowerConst() {
        final PartOfExpression expr = PARSER.parse("2 pow 10");
        final Const c = new Const(BigDecimal.valueOf(1024));
        Assertions.assertEquals(c, expr.evaluate(BigDecimal.ZERO));
    }

    @Test
    public void testPowerMatrix() {
        final PartOfExpression expr = PARSER.parse("{{1, 2}, {3, 4}} pow 10");
        final Matrix c = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(4783807), BigDecimal.valueOf(6972050)},
                {BigDecimal.valueOf(10458075), BigDecimal.valueOf(15241882)}});
        Assertions.assertEquals(c, expr.evaluate(BigDecimal.ZERO));
    }
}
