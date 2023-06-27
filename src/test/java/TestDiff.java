import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.linalg.expression.PartOfExpression;
import org.linalg.expression.operations.*;
import org.linalg.expression.parser.Parser;
import org.linalg.expression.parts.Const;
import org.linalg.expression.parts.Variable;

import java.math.BigDecimal;
import java.util.Random;

public class TestDiff {
    private final static int TEST_COUNT = 100;
    private final static Random RANDOM = new Random();
    private final static Parser PARSER = new Parser();
    private final static Variable X = new Variable("x");

    @Test
    public void testConstDiff() {
        Assertions.assertEquals(Const.ZERO, Const.ONE.diff());
        for (int i = 0; i < TEST_COUNT; i++) {
            Assertions.assertEquals(Const.ZERO, new Const(BigDecimal.valueOf(RANDOM.nextInt())).diff());
        }
    }

    @Test
    public void testVariableDiff() {
        Assertions.assertEquals(Const.ONE, X.diff());
        for (int i = 0; i < TEST_COUNT; i++) {
            BigDecimal value = BigDecimal.valueOf(RANDOM.nextInt());
            Assertions.assertEquals(new Add(new Multiply(Const.ZERO, X), new Multiply(new Const(value), Const.ONE)),
                    new Multiply(new Const(value), X).diff());
        }
    }

    @Test
    public void testAdd() {
        Assertions.assertEquals(new Add(Const.ZERO, Const.ZERO), PARSER.parse("diff (1 + 1)").evaluate());
        Assertions.assertEquals(new Add(Const.ONE, Const.ZERO), PARSER.parse("diff (x + 2)").evaluate());
    }

    @Test
    public void testSubtract() {
        Assertions.assertEquals(new Subtract(Const.ZERO, Const.ZERO), PARSER.parse("diff (1 - 1)").evaluate());
        Assertions.assertEquals(new Subtract(Const.ONE, Const.ZERO), PARSER.parse("diff (x - 1)").evaluate());
    }

    @Test
    public void testMultiply() {
        Assertions.assertEquals(new Add(new Multiply(Const.ZERO, Const.ZERO), new Multiply(Const.ZERO, Const.ZERO)), PARSER.parse("diff (0 * 0)").evaluate());
        Assertions.assertEquals(new Add(new Multiply(Const.ONE, Const.TWO), new Multiply(X, Const.ZERO)), PARSER.parse("diff (x * 2)").evaluate());
    }

    @Test
    public void testDivide() {
        Assertions.assertEquals(new Divide(new Subtract(new Multiply(Const.ZERO, Const.ONE), new Multiply(Const.ZERO, Const.ZERO)), new Multiply(Const.ONE, Const.ONE)),
                PARSER.parse("diff (0 / 1)").evaluate());
        Assertions.assertEquals(new Divide(new Subtract(new Multiply(Const.ZERO, X), new Multiply(Const.TWO, Const.ONE)), new Multiply(X, X)),
                PARSER.parse("diff (2 / x)").evaluate());
    }

    @Test
    public void testPow() {
        for (int i = 0; i < TEST_COUNT; i++) {
            final BigDecimal power = BigDecimal.valueOf(RANDOM.nextInt());
            final String expr = "diff (x pow " + power + ")";
            Assertions.assertEquals(new Multiply(new Const(power), new Pow(X, new Const(power.subtract(BigDecimal.ONE)))),
                    PARSER.parse(expr).evaluate());
        }
    }

    @Test
    public void testTrigonometric() {
        for (int i = 0; i < TEST_COUNT; i++) {
            final Const coefficient = new Const(BigDecimal.valueOf(RANDOM.nextInt()));
            final PartOfExpression diffPart = new Add(new Multiply(Const.ZERO, X),
                    new Multiply(coefficient, Const.ONE));
            final PartOfExpression part = new Multiply(coefficient, X);
            String expr = "diff (sin(" + coefficient + " * x))";
            Assertions.assertEquals(new Multiply(new Cos(part), diffPart),
                    PARSER.parse(expr).evaluate());
            expr = "diff (cos(" + coefficient + " * x))";
            Assertions.assertEquals(new Multiply(new Negate(new Sin(part)), diffPart),
                    PARSER.parse(expr).evaluate());
            expr = "diff (tan(" + coefficient + " * x))";
            Assertions.assertEquals(new Divide(diffPart, new Multiply(new Cos(part), new Cos(part))), PARSER.parse(expr).evaluate());
        }
    }

    @Test
    public void testLog() {
        final Const coefficient = new Const(BigDecimal.valueOf(RANDOM.nextInt()));
        Assertions.assertEquals(new Divide(new Add(new Multiply(Const.ZERO, X), new Multiply(coefficient, Const.ONE)),
                new Multiply(coefficient, X)), PARSER.parse("diff (log(" + coefficient + " * x))").evaluate());

    }

    @Test
    public void testSqrt() {
        final Const coefficient = new Const(BigDecimal.valueOf(RANDOM.nextInt()));
        Assertions.assertEquals(new Divide(new Add(new Multiply(Const.ZERO, X), new Multiply(coefficient, Const.ONE)),
                        new Multiply(Const.TWO, new Sqrt(new Multiply(coefficient, X)))),
                PARSER.parse("diff (sqrt(" + coefficient + " * x))").evaluate());
    }
}
