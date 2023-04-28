import org.linalg.expression.PartOfExpression;
import org.linalg.expression.parser.Parser;
import org.linalg.expression.parts.Const;
import org.linalg.expression.parts.Matrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class TestParser {
    @BeforeAll
    public static void setup() {
        System.out.println("Parser test is starting");
    }

    @Test
    public void testConstExpression() {
        Assertions.assertEquals(new Parser().parse("1 + 2 + 3 / 5 * (1 + 2)").evaluate(), new Const(BigDecimal.valueOf(4.8)));

    }

    @Test
    public void testMatrixAdd() {
        final PartOfExpression expr = new Parser().parse("{{1, 2}, {3, 4}} + {{1, 2}, {3, 4}}");
        final Matrix ans = new Matrix(new BigDecimal[][]{{BigDecimal.TWO, BigDecimal.valueOf(4)},
                {BigDecimal.valueOf(6), BigDecimal.valueOf(8)}});

        Assertions.assertEquals(expr.evaluate(), ans);
    }

    @Test
    public void testMatrixSubtract() {
        final PartOfExpression expr = new Parser().parse("{{1, 2}, {3, 4}} - {{4, 3}, {2, 1}}");
        final Matrix ans = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(-3), BigDecimal.valueOf(-1)},
                {BigDecimal.valueOf(1), BigDecimal.valueOf(3)}});

        Assertions.assertEquals(expr.evaluate(), ans);
    }

    @Test
    public void testMatrixMultiplyByScalar() {
        final PartOfExpression expr = new Parser().parse("{{1, 2}, {3, 4}} * 10");
        final Matrix ans = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(10), BigDecimal.valueOf(20)},
                {BigDecimal.valueOf(30), BigDecimal.valueOf(40)}});

        Assertions.assertEquals(expr.evaluate(), ans);
    }

    @Test
    public void testMatrixMultiplyByMatrix() {
        final PartOfExpression expr = new Parser().parse("{{1, 2}, {3, 4}} * {{1, 2}, {3, 4}}");
        final Matrix ans = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(7), BigDecimal.valueOf(10)},
                {BigDecimal.valueOf(15), BigDecimal.valueOf(22)}});

        Assertions.assertEquals(expr.evaluate(), ans);
    }

    @Test
    public void testTranspose() {
        final PartOfExpression expr = new Parser().parse("transpose {{1, 2}, {3, 4}}");
        final Matrix ans = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(3)},
                {BigDecimal.valueOf(2), BigDecimal.valueOf(4)}});

        Assertions.assertEquals(expr.evaluate(), ans);
    }

    @Test
    public void testInverse() {
        final PartOfExpression expr = new Parser().parse("inverse {{1, 2}, {3, 4}}");
        final Matrix ans = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(-2), BigDecimal.valueOf(1)},
                {BigDecimal.valueOf(1.5), BigDecimal.valueOf(-0.5)}});

        Assertions.assertEquals(expr.evaluate(), ans);
    }

    @Test
    public void testDeterminant() {
        final PartOfExpression expr = new Parser().parse("det {{1, 2}, {3, 4}}");
        final Const ans = new Const(BigDecimal.TWO.negate());

        Assertions.assertEquals(expr.evaluate(), ans);
    }

    @Test
    public void testRank() {
        final PartOfExpression expr = new Parser().parse("rank {{1, 2}, {3, 4}}");
        final Const ans = new Const(BigDecimal.TWO);

        Assertions.assertEquals(expr.evaluate(), ans);
    }

    @Test
    public void testNegate() {
        final PartOfExpression expr = new Parser().parse("- {{1, 2}, {3, 4}}");
        final Matrix ans = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(-1), BigDecimal.valueOf(-2)},
                {BigDecimal.valueOf(-3), BigDecimal.valueOf(-4)}});

        Assertions.assertEquals(expr.evaluate(), ans);
    }

    @Test
    public void testTriangle() {
        final PartOfExpression expr = new Parser().parse("triangle {{1, 2}, {3, 4}}");
        final Matrix ans = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(0), BigDecimal.valueOf(-2)}});

        Assertions.assertEquals(expr.evaluate(), ans);
    }

    @Test
    public void testPowerConst() {
        final PartOfExpression expr = new Parser().parse("2 pow 10");
        final Const c = new Const(BigDecimal.valueOf(1024));
        Assertions.assertEquals(expr.evaluate(), c);
    }

    @Test
    public void testPowerMatrix() {
        final PartOfExpression expr = new Parser().parse("{{1, 2}, {3, 4}} pow 10");
        final Matrix c = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(4783807), BigDecimal.valueOf(6972050)},
                {BigDecimal.valueOf(10458075), BigDecimal.valueOf(15241882)}});
        Assertions.assertEquals(expr.evaluate(), c);
    }
}
