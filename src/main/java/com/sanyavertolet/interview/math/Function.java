package com.sanyavertolet.interview.math;

import com.sanyavertolet.interview.data.value.Value;
import com.sanyavertolet.interview.exceptions.expressions.ExpressionEvaluationException;
import com.sanyavertolet.interview.utils.FunctionUtils;

import java.util.List;

/**
 * An enum representing various built-in functions that can be evaluated in expressions.
 * Each function has a defined number of arguments and a specific implementation for its evaluation.
 */
public enum Function {

    // Mathematical constants
    PI(0, args -> Value.of(Math.PI)),
    E(0, args -> Value.of(Math.E)),

    // Mathematical operations
    POW(2, args -> Value.of(Math.pow(args.get(0).asDouble(), args.get(1).asDouble()))),

    // Trigonometric functions
    SIN(1, args -> Value.of(Math.sin(args.get(0).asDouble()))),
    COS(1, args -> Value.of(Math.cos(args.get(0).asDouble()))),
    TAN(1, args -> Value.of(Math.tan(args.get(0).asDouble()))),
    CTG(1, args -> Value.of(1 / Math.tan(args.get(0).asDouble()))),

    // Inverse trigonometric functions
    ASIN(1, args -> Value.of(Math.asin(args.get(0).asDouble()))),
    ACOS(1, args -> Value.of(Math.acos(args.get(0).asDouble()))),
    ATAN(1, args -> Value.of(Math.atan(args.get(0).asDouble()))),

    // Logarithmic functions
    LN(1, args -> Value.of(Math.log(args.get(0).asDouble()))),
    LOG(2, args -> Value.of(Math.log(args.get(1).asDouble()) / Math.log(args.get(0).asDouble()))),

    // Other mathematical functions
    ABS(1, args -> Value.of(Math.abs(args.get(0).asDouble()))),
    CBRT(1, args -> Value.of(Math.cbrt(args.get(0).asDouble()))),
    SQRT(1, args -> Value.of(Math.sqrt(args.get(0).asDouble()))),
    MIN(2, args -> Value.of(Math.min(args.get(0).asDouble(), args.get(1).asDouble()))),
    MAX(2, args -> Value.of(Math.max(args.get(0).asDouble(), args.get(1).asDouble()))),
    CEILING(1, args -> Value.of(Math.ceil(args.get(0).asDouble()))),
    FLOOR(1, args -> Value.of(Math.floor(args.get(0).asDouble()))),
    MOD(2, args -> Value.of(args.get(0).asInteger() % args.get(1).asInteger())),

    // String functions
    CONTAINS(2, args -> Value.of(args.get(0).asString().contains(args.get(1).asString()))),
    CONCAT(2, args -> Value.of(args.get(0).asString() + args.get(1).asString())),
    REPEAT(2, args -> Value.of(args.get(0).asString().repeat(args.get(1).asInteger()))),
    LENGTH(1, args -> Value.of(args.get(0).asString().length())),
    STRING(1, args -> Value.of(args.get(0).toString())),

    // Statistical functions
    SUM(1, FunctionUtils::sum),
    AVERAGE(1, FunctionUtils::average),
    COUNT(1, FunctionUtils::count),

    // Conditional functions
    IF(3, args -> args.get(0).asBoolean() ? args.get(1) : args.get(2)),
    IFERROR(2, args -> args.get(0) != null ? args.get(0) : args.get(1)),
    ;

    private final Integer argumentsSize;
    private final FunctionEvaluator functionEvaluator;

    /**
     * Constructs a {@code Function} with the specified number of arguments and the function evaluation logic.
     *
     * @param argumentsSize the number of arguments required by the function.
     * @param functionEvaluator the evaluation logic for the function.
     */
    Function(Integer argumentsSize, FunctionEvaluator functionEvaluator) {
        this.argumentsSize = argumentsSize;
        this.functionEvaluator = functionEvaluator;
    }

    /**
     * Returns the number of arguments required by the function.
     *
     * @return the number of arguments required by the function.
     */
    public Integer getArgumentsSize() {
        return argumentsSize;
    }

    /**
     * Evaluates the function with the given arguments.
     *
     * @param arguments the list of {@link Value} objects representing the function arguments.
     * @return the result of the function evaluation as a {@link Value}.
     * @throws ExpressionEvaluationException if the number of arguments is incorrect or if an error occurs during evaluation.
     */
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

    /**
     * A functional interface representing the logic for evaluating a function.
     */
    private interface FunctionEvaluator {
        Value invoke(List<Value> args) throws ExpressionEvaluationException;
    }

    /**
     * Returns a {@code Function} based on its name.
     *
     * @param name the name of the function (case-insensitive).
     * @return the corresponding {@code Function}.
     */
    public static Function named(String name) {
        return Function.valueOf(name.toUpperCase());
    }
}
