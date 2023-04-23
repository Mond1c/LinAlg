import org.example.expression.parts.Const;
import org.example.expression.parts.Tensor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class TestTensor {
    @Test
    public void testAdd() {
        final Tensor a = new Tensor(new BigDecimal[][][][][]{
                {
                        {
                                {
                                        {BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3)},
                                        {BigDecimal.valueOf(4), BigDecimal.valueOf(5), BigDecimal.valueOf(6)},
                                        {BigDecimal.valueOf(7), BigDecimal.valueOf(8), BigDecimal.valueOf(9)}
                                }}}});
        final Tensor b = new Tensor(new BigDecimal[][][][][]{
                {
                        {
                                {
                                        {BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3)},
                                        {BigDecimal.valueOf(4), BigDecimal.valueOf(5), BigDecimal.valueOf(6)},
                                        {BigDecimal.valueOf(7), BigDecimal.valueOf(8), BigDecimal.valueOf(9)}
                                }}}});
        final Tensor answer = new Tensor(new BigDecimal[][][][][]{
                {
                        {
                                {
                                        {BigDecimal.valueOf(2), BigDecimal.valueOf(4), BigDecimal.valueOf(6)},
                                        {BigDecimal.valueOf(8), BigDecimal.valueOf(10), BigDecimal.valueOf(12)},
                                        {BigDecimal.valueOf(14), BigDecimal.valueOf(16), BigDecimal.valueOf(18)}
                                }}}});

        Assertions.assertEquals(a.add(b), answer);
    }

    @Test
    public void testSubtract() {
        final Tensor a = new Tensor(new BigDecimal[][][][][]{
                {
                        {
                                {
                                        {BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3)},
                                        {BigDecimal.valueOf(4), BigDecimal.valueOf(5), BigDecimal.valueOf(6)},
                                        {BigDecimal.valueOf(7), BigDecimal.valueOf(8), BigDecimal.valueOf(9)}
                                }}}});
        final Tensor b = new Tensor(new BigDecimal[][][][][]{
                {
                        {
                                {
                                        {BigDecimal.valueOf(1), BigDecimal.valueOf(1), BigDecimal.valueOf(1)},
                                        {BigDecimal.valueOf(1), BigDecimal.valueOf(1), BigDecimal.valueOf(1)},
                                        {BigDecimal.valueOf(1), BigDecimal.valueOf(1), BigDecimal.valueOf(1)}
                                }}}});
        final Tensor answer = new Tensor(new BigDecimal[][][][][]{
                {
                        {
                                {
                                        {BigDecimal.valueOf(0), BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                                        {BigDecimal.valueOf(3), BigDecimal.valueOf(4), BigDecimal.valueOf(5)},
                                        {BigDecimal.valueOf(6), BigDecimal.valueOf(7), BigDecimal.valueOf(8)}
                                }}}});

        Assertions.assertEquals(a.subtract(b), answer);
    }

    @Test
    public void testMultiplyByScalar() {
        final Tensor a = new Tensor(new BigDecimal[][][][][]{
                {
                        {
                                {
                                        {BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3)},
                                        {BigDecimal.valueOf(4), BigDecimal.valueOf(5), BigDecimal.valueOf(6)},
                                        {BigDecimal.valueOf(7), BigDecimal.valueOf(8), BigDecimal.valueOf(9)}
                                }}}});
        final Const b = new Const(BigDecimal.valueOf(2));
        final Tensor answer = new Tensor(new BigDecimal[][][][][]{
                {
                        {
                                {
                                        {BigDecimal.valueOf(2), BigDecimal.valueOf(4), BigDecimal.valueOf(6)},
                                        {BigDecimal.valueOf(8), BigDecimal.valueOf(10), BigDecimal.valueOf(12)},
                                        {BigDecimal.valueOf(14), BigDecimal.valueOf(16), BigDecimal.valueOf(18)}
                                }}}});

        Assertions.assertEquals(a.multiply(b), answer);
    }

    @Test
    public void testMultiplyByTensor() {

    }
}
