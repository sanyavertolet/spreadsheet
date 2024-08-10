package com.sanyavertolet.interview.math;

import com.sanyavertolet.interview.exceptions.ExpressionEvaluationException;
import com.sanyavertolet.interview.math.expressions.Expression;

import java.util.List;

public enum Function {
    PI(0, (args) -> Math.PI),
    E(0, (args) -> Math.E),
    POW(2, (args) -> Math.pow(args.get(0).evaluate(), args.get(1).evaluate())),

    SIN(1, (args) -> Math.sin(args.get(0).evaluate())),
    COS(1, (args) -> Math.cos(args.get(0).evaluate())),
    TAN(1, (args) -> Math.tan(args.get(0).evaluate())),
    CTG(1, (args) -> 1 / Math.tan(args.get(0).evaluate())),

    ASIN(1, (args) -> Math.asin(args.get(0).evaluate())),
    ACOS(1, (args) -> Math.acos(args.get(0).evaluate())),
    ATAN(1, (args) -> Math.atan(args.get(0).evaluate())),

    LN(1, (args) -> Math.log(args.get(0).evaluate())),
    LOG(2, (args) -> Math.log(args.get(1).evaluate()) / Math.log(args.get(0).evaluate())),

    ABS(1, (args) -> Math.abs(args.get(0).evaluate())),
    CBRT(1, (args) -> Math.cbrt(args.get(0).evaluate())),
    SQRT(1, (args) -> Math.sqrt(args.get(0).evaluate())),

    MAX(1, (args) -> Math.max(args.get(0).evaluate(), args.get(1).evaluate())),
    MIN(1, (args) -> Math.min(args.get(0).evaluate(), args.get(1).evaluate())),
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

    public Double evaluate(List<Expression> arguments) throws ExpressionEvaluationException {
        if (arguments.size() != argumentsSize) {
            throw new ExpressionEvaluationException(name() + " should have exactly " + argumentsSize + " arguments");
        }
        return evaluator.invoke(arguments);
    }

    private interface Evaluator {
        Double invoke(List<Expression> args) throws ExpressionEvaluationException;
    }

    public static Function named(String name) {
        return Function.valueOf(name.toUpperCase());
    }
}
