import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.linalg.expression.operations.Add;
import org.linalg.expression.operations.Divide;
import org.linalg.expression.operations.Multiply;
import org.linalg.expression.operations.Subtract;
import org.linalg.expression.parser.Parser;
import org.linalg.expression.parts.Const;
import org.linalg.expression.parts.Variable;

import java.math.BigDecimal;
import java.util.Random;

public class TestDiff {
    private final static int TEST_COUNT = 100;
    private final static Random RANDOM = new Random();
    private final static Parser PARSER = new Parser();

    @Test
    public void testConstDiff() {
        Assertions.assertEquals(Const.ZERO, Const.ONE.diff());
        for (int i = 0; i < TEST_COUNT; i++) {
            Assertions.assertEquals(Const.ZERO, new Const(BigDecimal.valueOf(RANDOM.nextInt())).diff());
        }
    }

    @Test
    public void testVariableDiff() {
        Assertions.assertEquals(Const.ONE, new Variable("x").diff());
        for (int i = 0; i < TEST_COUNT; i++) {
            BigDecimal value = BigDecimal.valueOf(RANDOM.nextInt());
            Assertions.assertEquals(new Add(new Multiply(Const.ZERO, new Variable("x")), new Multiply(new Const(value), Const.ONE)),
                    new Multiply(new Const(value), new Variable("x")).diff());
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
        Assertions.assertEquals(new Add(new Multiply(Const.ONE, Const.TWO), new Multiply(new Variable("x"), Const.ZERO)), PARSER.parse("diff (x * 2)").evaluate());
    }

    @Test
    public void testDivide() {
        Assertions.assertEquals(new Divide(new Subtract(new Multiply(Const.ZERO, Const.ONE), new Multiply(Const.ZERO, Const.ZERO)), new Multiply(Const.ONE, Const.ONE)),
                PARSER.parse("diff (0 / 1)").evaluate());
        Assertions.assertEquals(new Divide(new Subtract(new Multiply(Const.ZERO, new Variable("x")), new Multiply(Const.TWO, Const.ONE)), new Multiply(new Variable("x"), new Variable("x"))),
                PARSER.parse("diff (2 / x)").evaluate());
    }
}
