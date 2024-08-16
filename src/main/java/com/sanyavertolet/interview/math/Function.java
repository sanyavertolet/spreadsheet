package com.sanyavertolet.interview.math;

import com.sanyavertolet.interview.data.value.Value;
import com.sanyavertolet.interview.exceptions.expressions.ExpressionEvaluationException;
import com.sanyavertolet.interview.utils.FunctionUtils;

import java.util.List;

public enum Function {
    PI(0, args -> Value.of(Math.PI)),
    E(0, args -> Value.of(Math.E)),
    POW(2, args -> Value.of(Math.pow(args.get(0).asDouble(), args.get(1).asDouble()))),

    SIN(1, args -> Value.of(Math.sin(args.get(0).asDouble()))),
    COS(1, args -> Value.of(Math.cos(args.get(0).asDouble()))),
    TAN(1, args -> Value.of(Math.tan(args.get(0).asDouble()))),
    CTG(1, args -> Value.of(1 / Math.tan(args.get(0).asDouble()))),

    ASIN(1, args -> Value.of(Math.asin(args.get(0).asDouble()))),
    ACOS(1, args -> Value.of(Math.acos(args.get(0).asDouble()))),
    ATAN(1, args -> Value.of(Math.atan(args.get(0).asDouble()))),

    LN(1, args -> Value.of(Math.log(args.get(0).asDouble()))),
    LOG(2, args -> Value.of(Math.log(args.get(1).asDouble()) / Math.log(args.get(0).asDouble()))),

    ABS(1, args -> Value.of(Math.abs(args.get(0).asDouble()))),
    CBRT(1, args -> Value.of(Math.cbrt(args.get(0).asDouble()))),
    SQRT(1, args -> Value.of(Math.sqrt(args.get(0).asDouble()))),

    MIN(1, args -> Value.of(Math.min(args.get(0).asDouble(), args.get(1).asDouble()))),
    MAX(1, args -> Value.of(Math.max(args.get(0).asDouble(), args.get(1).asDouble()))),

    CONTAINS(2, args -> Value.of(args.get(0).asString().contains(args.get(1).asString()))),
    REPEAT(2, args -> Value.of(args.get(0).asString().repeat(args.get(1).asInteger()))),
    STRING(1, args -> Value.of(args.get(0).asString())),
    
    SUM(1, FunctionUtils::sum)
    ;

    private final Integer argumentsSize;
    private final FunctionEvaluator functionEvaluator;

    Function(Integer argumentsSize, FunctionEvaluator functionEvaluator) {
        this.argumentsSize = argumentsSize;
        this.functionEvaluator = functionEvaluator;
    }

    public Integer getArgumentsSize() {
        return argumentsSize;
    }

    public Value evaluate(List<Value> arguments) throws ExpressionEvaluationException {
        if (arguments.size() != argumentsSize) {
            throw new ExpressionEvaluationException(name() + " should have exactly " + argumentsSize + " arguments");
        }
        try {
            return functionEvaluator.invoke(arguments);
        } catch (NullPointerException exception) {
            throw new ExpressionEvaluationException("Could not evaluate expression as one of the arguments is null");
        }
    }

    private interface FunctionEvaluator {
        Value invoke(List<Value> args) throws ExpressionEvaluationException;
    }

    public static Function named(String name) {
        return Function.valueOf(name.toUpperCase());
    }
}
