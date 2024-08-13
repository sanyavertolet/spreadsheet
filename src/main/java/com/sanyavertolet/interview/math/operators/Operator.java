package com.sanyavertolet.interview.math.operators;

public abstract class Operator {
    protected final String symbol;

    protected Operator(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return getSymbol();
    }
}
