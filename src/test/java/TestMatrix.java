import org.linalg.expression.parts.Const;
import org.linalg.expression.parts.Matrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class TestMatrix {


    @Test
    public void testAdd() {
        Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Matrix b = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Matrix c = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(2), BigDecimal.valueOf(4)},
                {BigDecimal.valueOf(6), BigDecimal.valueOf(8)}});
        Assertions.assertEquals(c, a.add(b));
    }

    @Test
    public void testSubtract() {
        Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(2), BigDecimal.valueOf(4)},
                {BigDecimal.valueOf(6), BigDecimal.valueOf(8)}});
        Matrix b = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Matrix c = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Assertions.assertEquals(c, a.subtract(b));
    }

    @Test
    public void testMultiplyByScalar() {
        Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        BigDecimal b = BigDecimal.TWO;
        Matrix c = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(2), BigDecimal.valueOf(4)},
                {BigDecimal.valueOf(6), BigDecimal.valueOf(8)}});
        Assertions.assertEquals(c, a.multiply(new Const(b)));
    }

    @Test
    public void testMultiplyByMatrix() {
        Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Matrix b = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Matrix c = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(7), BigDecimal.valueOf(10)},
                {BigDecimal.valueOf(15), BigDecimal.valueOf(22)}});
        Assertions.assertEquals(c, a.multiply(b));
    }

    @Test
    public void testTranspose() {
        Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Matrix b = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(3)},
                {BigDecimal.valueOf(2), BigDecimal.valueOf(4)}});
        a.transpose();
        Assertions.assertEquals(b, a);
    }

    @Test
    public void testInverse() {
        Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Matrix b = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(-2), BigDecimal.valueOf(1)},
                {BigDecimal.valueOf(1.5), BigDecimal.valueOf(-0.5)}});
        a.inverse();
        Assertions.assertEquals(b, a);
    }

    @Test
    public void testDeterminant() {
        Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Assertions.assertEquals(BigDecimal.TWO.negate(), a.determinant());
    }

    @Test
    public void testRank() {
        Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Assertions.assertEquals(2, a.rank());
    }

    @Test
    public void testNegate() {
        Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Matrix b = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(-1), BigDecimal.valueOf(-2)},
                {BigDecimal.valueOf(-3), BigDecimal.valueOf(-4)}});
        Assertions.assertEquals(b, a.negate());
    }

    @Test
    public void testTriangle() {
        Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Matrix b = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(0), BigDecimal.valueOf(-2)}});
        a.triangle();
        Assertions.assertEquals(b, a);
    }

    @Test
    public void testPower() {
        final Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        final Const b = new Const(BigDecimal.valueOf(10));
        final Matrix c = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(4783807), BigDecimal.valueOf(6972050)},
                {BigDecimal.valueOf(10458075), BigDecimal.valueOf(15241882)}});
        Assertions.assertEquals(c, a.pow(b));
    }
}
