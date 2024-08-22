package com.sanyavertolet.interview.math.operators;

/**
 * An abstract class representing a general operator used in expressions.
 * The {@code Operator} class provides a common structure for operators, including a symbol that represents the operator.
 */
public abstract class Operator {
    protected final String symbol;

    /**
     * Constructs an {@code Operator} with the specified symbol.
     *
     * @param symbol the symbol representing the operator.
     */
    protected Operator(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the symbol associated with this operator.
     *
     * @return the symbol as a string.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Returns a string representation of the operator, which is the operator's symbol.
     *
     * @return the symbol as a string.
     */
    @Override
    public String toString() {
        return getSymbol();
    }
}
