import org.example.expression.PartOfExpression;
import org.example.expression.parser.Parser;
import org.example.expression.parts.Const;
import org.example.expression.parts.Matrix;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Random;

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
}
