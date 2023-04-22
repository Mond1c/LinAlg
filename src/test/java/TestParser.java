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
        final Matrix ans = new Matrix(new BigDecimal[][]{{BigDecimal.TWO, BigDecimal.valueOf(4)}, {BigDecimal.valueOf(6), BigDecimal.valueOf(8)}});

        Assertions.assertEquals(ans, expr.evaluate());
    }
}
