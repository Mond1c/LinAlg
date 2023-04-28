package org.linalg.math;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.function.Function;

public class BigDecimalMath {
    private static final int PRECISION = 10;
    private static final MathContext CONTEXT = new MathContext(PRECISION);

    private static BigDecimal applyFunction(Function<Double, Double> f, BigDecimal x) {
        return new BigDecimal(f.apply(x.doubleValue()), CONTEXT);
    }

    public static BigDecimal sin(BigDecimal x) {
        return applyFunction(Math::sin, x);
    }

    public static BigDecimal cos(BigDecimal x) {
        return applyFunction(Math::cos, x);
    }

    public static BigDecimal tan(BigDecimal x) {
        return applyFunction(Math::tan, x);
    }

    public static BigDecimal asin(BigDecimal x) {
        return applyFunction(Math::asin, x);
    }

    public static BigDecimal acos(BigDecimal x) {
        return applyFunction(Math::acos, x);
    }

    public static BigDecimal atan(BigDecimal x) {
        return applyFunction(Math::atan, x);
    }

    public static BigDecimal sinh(BigDecimal x) {
        return applyFunction(Math::sinh, x);
    }

    public static BigDecimal cosh(BigDecimal x) {
        return applyFunction(Math::cosh, x);
    }

    public static BigDecimal tanh(BigDecimal x) {
        return applyFunction(Math::tanh, x);
    }

    public static BigDecimal log(BigDecimal x) {
        return applyFunction(Math::log, x);
    }
}
