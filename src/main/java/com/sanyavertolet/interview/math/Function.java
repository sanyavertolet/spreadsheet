package com.sanyavertolet.interview.math;

import com.sanyavertolet.interview.exceptions.expressions.ExpressionEvaluationException;

import java.util.List;

public enum Function {
    PI(0, (args) -> Math.PI),
    E(0, (args) -> Math.E),
    POW(2, (args) -> Math.pow(args.get(0), args.get(1))),

    SIN(1, (args) -> Math.sin(args.get(0))),
    COS(1, (args) -> Math.cos(args.get(0))),
    TAN(1, (args) -> Math.tan(args.get(0))),
    CTG(1, (args) -> 1 / Math.tan(args.get(0))),

    ASIN(1, (args) -> Math.asin(args.get(0))),
    ACOS(1, (args) -> Math.acos(args.get(0))),
    ATAN(1, (args) -> Math.atan(args.get(0))),

    LN(1, (args) -> Math.log(args.get(0))),
    LOG(2, (args) -> Math.log(args.get(1)) / Math.log(args.get(0))),

    ABS(1, (args) -> Math.abs(args.get(0))),
    CBRT(1, (args) -> Math.cbrt(args.get(0))),
    SQRT(1, (args) -> Math.sqrt(args.get(0))),

    MAX(1, (args) -> Math.max(args.get(0), args.get(1))),
    MIN(1, (args) -> Math.min(args.get(0), args.get(1))),
    ;

    private final int argumentsSize;
    private final Evaluator evaluator;

    Function(int argumentsSize, Evaluator evaluator) {
        this.argumentsSize = argumentsSize;
        this.evaluator = evaluator;
    }

    public int getArgumentsSize() {
        return argumentsSize;
    }

    public Double evaluate(List<Double> arguments) throws ExpressionEvaluationException {
        if (arguments.size() != argumentsSize) {
            throw new ExpressionEvaluationException(name() + " should have exactly " + argumentsSize + " arguments");
        }
        return evaluator.invoke(arguments);
    }

    private interface Evaluator {
        Double invoke(List<Double> args) throws ExpressionEvaluationException;
    }

    public static Function named(String name) {
        return Function.valueOf(name.toUpperCase());
    }
}
