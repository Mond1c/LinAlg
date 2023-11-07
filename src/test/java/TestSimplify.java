import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.linalg.expression.parser.Parser;
import org.linalg.expression.parts.Const;
import org.linalg.expression.parts.Variable;

import java.math.BigDecimal;
import java.util.Random;

public class TestSimplify {
    private static final Random RANDOM = new Random();
    private static final Parser PARSER = new Parser();

    @Test
    public void testAdd() {
        final Const c1 = new Const(BigDecimal.valueOf(RANDOM.nextInt()));
        final Const c2 = new Const(BigDecimal.valueOf(RANDOM.nextInt()));
        Assertions.assertEquals(c1.add(c2), PARSER.parse(c1 + " + " + c2).evaluate().simplify());
        Assertions.assertEquals(new Variable("x"), PARSER.parse("x + 0").evaluate().simplify());
    }

    @Test
    public void testSubtract() {
        final Const c1 = new Const(BigDecimal.valueOf(RANDOM.nextInt()));
        final Const c2 = new Const(BigDecimal.valueOf(RANDOM.nextInt()));
        Assertions.assertEquals(c1.subtract(c2), PARSER.parse(c1 + " - " + c2).evaluate().simplify());
        Assertions.assertEquals(new Variable("x"), PARSER.parse("x - 0").evaluate().simplify());
    }

    @Test
    public void testMultiply() {
        final Const c1 = new Const(BigDecimal.valueOf(RANDOM.nextInt()));
        final Const c2 = new Const(BigDecimal.valueOf(RANDOM.nextInt()));
        Assertions.assertEquals(c1.multiply(c2), PARSER.parse(c1 + " * " + c2).evaluate().simplify());
        Assertions.assertEquals(Const.ZERO, PARSER.parse("x * 0").evaluate().simplify());

    }

    @Test
    public void testDivide() {
        final Const c1 = new Const(BigDecimal.valueOf(RANDOM.nextInt()));
        Const c2 = new Const(BigDecimal.valueOf(RANDOM.nextInt()));
        while (c2.equals(Const.ZERO)) {
            c2 = new Const(BigDecimal.valueOf(RANDOM.nextInt()));
        }
        Assertions.assertEquals(c1.divide(c2), PARSER.parse(c1 + " / " + c2).evaluate().simplify());
        Assertions.assertEquals(Const.ZERO, PARSER.parse("0 / x").evaluate().simplify());
    }
}
