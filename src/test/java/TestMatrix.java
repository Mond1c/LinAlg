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

public class TestMatrix {

    @BeforeAll
    static void setup() {
        System.out.println("Matrix test is starting");
    }

    @Test
    void testAdd() {
        Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Matrix b = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Matrix c = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(2), BigDecimal.valueOf(4)},
                {BigDecimal.valueOf(6), BigDecimal.valueOf(8)}});
        Assertions.assertEquals(a.add(b), c);
    }

    @Test
    void testSubtract() {
        Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(2), BigDecimal.valueOf(4)},
                {BigDecimal.valueOf(6), BigDecimal.valueOf(8)}});
        Matrix b = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Matrix c = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Assertions.assertEquals(a.subtract(b), c);
    }

    @Test
    void testMultiplyByScalar() {
        Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        BigDecimal b = BigDecimal.TWO;
        Matrix c = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(2), BigDecimal.valueOf(4)},
                {BigDecimal.valueOf(6), BigDecimal.valueOf(8)}});
        Assertions.assertEquals(a.multiply(new Const(b)), c);
    }

    @Test
    void testMultiplyByMatrix() {
        Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Matrix b = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Matrix c = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(7), BigDecimal.valueOf(10)},
                {BigDecimal.valueOf(15), BigDecimal.valueOf(22)}});
        Assertions.assertEquals(a.multiply(b), c);
    }

    @Test
    void testTranspose() {
        Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Matrix b = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(3)},
                {BigDecimal.valueOf(2), BigDecimal.valueOf(4)}});
        a.transpose();;
        Assertions.assertEquals(a, b);
    }

    @Test
    void testInverse() {
        Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Matrix b = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(-2), BigDecimal.valueOf(1)},
                {BigDecimal.valueOf(1.5), BigDecimal.valueOf(-0.5)}});
        a.inverse();
        Assertions.assertEquals(a, b);
    }

    @Test
    void testDeterminant() {
        Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Assertions.assertEquals(a.determinant(), BigDecimal.TWO.negate());
    }

    @Test
    void testRank() {
        Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Assertions.assertEquals(a.rank(), 2);
    }

    @Test
    void testNegate() {
        Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Matrix b = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(-1), BigDecimal.valueOf(-2)},
                {BigDecimal.valueOf(-3), BigDecimal.valueOf(-4)}});
        Assertions.assertEquals(a.negate(), b);
    }

    @Test
    void testTriangle() {
        Matrix a = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}});
        Matrix b = new Matrix(new BigDecimal[][]{{BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(0), BigDecimal.valueOf(-2)}});
        a.triangle();
        Assertions.assertEquals(a, b);
    }
}