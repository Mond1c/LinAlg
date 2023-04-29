package org.linalg.expression.parts;

import org.linalg.expression.PartOfExpression;

import java.math.BigDecimal;

public class Variable implements PartOfExpression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }


    @Override
    public PartOfExpression evaluate(BigDecimal x) {
        return new Const(x);
    }

    @Override
    public PartOfExpression diff() {
        return Const.ONE;
    }

}
