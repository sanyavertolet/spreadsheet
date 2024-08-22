package com.sanyavertolet.interview.math.operators;

import com.sanyavertolet.interview.math.Function;

/**
 * Represents a function operator used in expressions, encapsulating a {@link Function}.
 * The {@code FunctionOperator} class extends the {@link Operator} class and links a symbol
 * to a specific {@link Function} that can be evaluated in an expression.
 */
public class FunctionOperator extends Operator {
    private final Function function;

    /**
     * Constructs a {@code FunctionOperator} based on the provided symbol.
     * The symbol is used to look up and associate the corresponding {@link Function}.
     *
     * @param symbol the symbol representing the function operator.
     * @throws IllegalArgumentException if the symbol does not correspond to a known function.
     */
    public FunctionOperator(String symbol) {
        super(symbol);
        function = Function.named(symbol);
    }

    /**
     * Returns the {@link Function} associated with this operator.
     *
     * @return the function associated with this operator.
     */
    public Function getFunction() {
        return function;
    }
}
