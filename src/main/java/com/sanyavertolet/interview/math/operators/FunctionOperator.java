package com.sanyavertolet.interview.math.operators;

import com.sanyavertolet.interview.math.Function;

public class FunctionOperator extends Operator {
    private final Function function;
    public FunctionOperator(String symbol) {
        super(symbol);
        function = Function.named(symbol);
    }

    public Function getFunction() {
        return function;
    }
}
