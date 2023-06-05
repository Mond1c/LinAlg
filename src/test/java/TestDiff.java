import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.linalg.expression.operations.Add;
import org.linalg.expression.operations.Multiply;
import org.linalg.expression.parts.Const;
import org.linalg.expression.parts.Variable;

import java.math.BigDecimal;
import java.util.Random;

public class TestDiff {
    private final static int TEST_COUNT = 100;
    private final static Random RANDOM = new Random();

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
        Assertions.assertEquals(new Add(Const.ZERO, Const.ZERO), new Add(Const.ONE, Const.TWO).diff());
        Assertions.assertEquals(new Add(Const.ONE, Const.ZERO), new Add(new Variable("x"), Const.TWO).diff());
    }
}
