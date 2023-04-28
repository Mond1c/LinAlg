import org.linalg.expression.parts.Const;
import org.linalg.expression.parts.Matrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class TestMatrix {

    @BeforeAll
    public static void setup() {
        System.out.println("Matrix test is starting");
    }

    @Test
    public void testAdd() {
        Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Matrix b = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Matrix c = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(2), BigDecimal.valueOf(4)},
                {BigDecimal.valueOf(6), BigDecimal.valueOf(8)}});
        Assertions.assertEquals(a.add(b), c);
    }

    @Test
    public void testSubtract() {
        Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(2), BigDecimal.valueOf(4)},
                {BigDecimal.valueOf(6), BigDecimal.valueOf(8)}});
        Matrix b = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Matrix c = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Assertions.assertEquals(a.subtract(b), c);
    }

    @Test
    public void testMultiplyByScalar() {
        Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        BigDecimal b = BigDecimal.TWO;
        Matrix c = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(2), BigDecimal.valueOf(4)},
                {BigDecimal.valueOf(6), BigDecimal.valueOf(8)}});
        Assertions.assertEquals(a.multiply(new Const(b)), c);
    }

    @Test
    public void testMultiplyByMatrix() {
        Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Matrix b = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Matrix c = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(7), BigDecimal.valueOf(10)},
                {BigDecimal.valueOf(15), BigDecimal.valueOf(22)}});
        Assertions.assertEquals(a.multiply(b), c);
    }

    @Test
    public void testTranspose() {
        Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Matrix b = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(3)},
                {BigDecimal.valueOf(2), BigDecimal.valueOf(4)}});
        a.transpose();;
        Assertions.assertEquals(a, b);
    }

    @Test
    public void testInverse() {
        Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Matrix b = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(-2), BigDecimal.valueOf(1)},
                {BigDecimal.valueOf(1.5), BigDecimal.valueOf(-0.5)}});
        a.inverse();
        Assertions.assertEquals(a, b);
    }

    @Test
    public void testDeterminant() {
        Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Assertions.assertEquals(a.determinant(), BigDecimal.TWO.negate());
    }

    @Test
    public void testRank() {
        Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Assertions.assertEquals(a.rank(), 2);
    }

    @Test
    public void testNegate() {
        Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Matrix b = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(-1), BigDecimal.valueOf(-2)},
                {BigDecimal.valueOf(-3), BigDecimal.valueOf(-4)}});
        Assertions.assertEquals(a.negate(), b);
    }

    @Test
    public void testTriangle() {
        Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Matrix b = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(0), BigDecimal.valueOf(-2)}});
        a.triangle();
        Assertions.assertEquals(a, b);
    }

    @Test
    public void testPower() {
        final Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        final Const b = new Const(BigDecimal.valueOf(10));
        final Matrix c = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(4783807), BigDecimal.valueOf(6972050)},
                {BigDecimal.valueOf(10458075), BigDecimal.valueOf(15241882)}});
        Assertions.assertEquals(a.pow(b), c);
    }
}
