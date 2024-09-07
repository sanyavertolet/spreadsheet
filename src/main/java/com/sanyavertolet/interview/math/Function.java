package com.sanyavertolet.interview.math;

import com.sanyavertolet.interview.data.value.Value;
import com.sanyavertolet.interview.exceptions.expressions.ExpressionEvaluationException;
import com.sanyavertolet.interview.utils.FunctionUtils;

import java.util.List;
import java.util.Locale;

/**
 * An enum representing various built-in functions that can be evaluated in expressions.
 * Each function has a defined number of arguments and a specific implementation for its evaluation.
 */
public enum Function {
    /**
     * Represents the mathematical constant pi. It provides a value of approximately 3.14159.
     * No arguments are needed for this function.
     */
    PI(0, args -> Value.of(Math.PI)),

    /**
     * Represents the mathematical constant e. It provides a value of approximately 2.71828.
     * No arguments are needed for this function.
     */
    E(0, args -> Value.of(Math.E)),

    /**
     * Computes the result of raising the first argument (base) to the power of the second argument (exponent).
     * Requires two arguments: the base and the exponent.
     */
    POW(2, args -> Value.of(Math.pow(args.get(0).asDouble(), args.get(1).asDouble()))),

    /**
     * Calculates the sine of an angle given in radians.
     * Requires one argument: the angle in radians.
     */
    SIN(1, args -> Value.of(Math.sin(args.get(0).asDouble()))),

    /**
     * Calculates the cosine of an angle given in radians.
     * Requires one argument: the angle in radians.
     */
    COS(1, args -> Value.of(Math.cos(args.get(0).asDouble()))),

    /**
     * Calculates the tangent of an angle given in radians.
     * Requires one argument: the angle in radians.
     */
    TAN(1, args -> Value.of(Math.tan(args.get(0).asDouble()))),

    /**
     * Calculates the cotangent of an angle given in radians, which is the reciprocal of the tangent.
     * Requires one argument: the angle in radians.
     */
    CTG(1, args -> Value.of(1 / Math.tan(args.get(0).asDouble()))),

    /**
     * Computes the arcsine (inverse sine) of a value, returning the angle in radians.
     * Requires one argument: the value for which the arcsine is to be calculated.
     */
    ASIN(1, args -> Value.of(Math.asin(args.get(0).asDouble()))),

    /**
     * Computes the arccosine (inverse cosine) of a value, returning the angle in radians.
     * Requires one argument: the value for which the arccosine is to be calculated.
     */
    ACOS(1, args -> Value.of(Math.acos(args.get(0).asDouble()))),

    /**
     * Computes the arc-tangent (inverse tangent) of a value, returning the angle in radians.
     * Requires one argument: the value for which the arc-tangent is to be calculated.
     */
    ATAN(1, args -> Value.of(Math.atan(args.get(0).asDouble()))),

    /**
     * Computes the natural logarithm (base e) of a value.
     * Requires one argument: the value for which the natural logarithm is to be calculated.
     */
    LN(1, args -> Value.of(Math.log(args.get(0).asDouble()))),

    /**
     * Computes the logarithm of a value with a specified base.
     * Requires two arguments: the value and the base for the logarithm calculation.
     * The base must be greater than 1.
     */
    LOG(2, args -> Value.of(Math.log(args.get(1).asDouble()) / Math.log(args.get(0).asDouble()))),

    /**
     * Computes the absolute value of a number.
     * Requires one argument: the value for which the absolute value is to be calculated.
     */
    ABS(1, args -> Value.of(Math.abs(args.get(0).asDouble()))),

    /**
     * Computes the cube root of a number.
     * Requires one argument: the value for which the cube root is to be calculated.
     */
    CBRT(1, args -> Value.of(Math.cbrt(args.get(0).asDouble()))),

    /**
     * Computes the square root of a number.
     * Requires one argument: the value for which the square root is to be calculated.
     */
    SQRT(1, args -> Value.of(Math.sqrt(args.get(0).asDouble()))),

    /**
     * Computes the minimum of two numbers.
     * Requires two arguments: the values to compare.
     */
    MIN(2, args -> Value.of(Math.min(args.get(0).asDouble(), args.get(1).asDouble()))),

    /**
     * Computes the maximum of two numbers.
     * Requires two arguments: the values to compare.
     */
    MAX(2, args -> Value.of(Math.max(args.get(0).asDouble(), args.get(1).asDouble()))),

    /**
     * Computes the smallest integer greater than or equal to a number.
     * Requires one argument: the value for which the ceiling is to be calculated.
     */
    CEILING(1, args -> Value.of(Math.ceil(args.get(0).asDouble()))),

    /**
     * Computes the largest integer less than or equal to a number.
     * Requires one argument: the value for which the floor is to be calculated.
     */
    FLOOR(1, args -> Value.of(Math.floor(args.get(0).asDouble()))),

    /**
     * Computes the remainder of the division of two integers.
     * Requires two arguments: the dividend and the divisor.
     * The divisor must not be zero.
     */
    MOD(2, args -> Value.of(args.get(0).asInteger() % args.get(1).asInteger())),

    /**
     * Computes AND for two booleans.
     * Requires two boolean arguments.
     */
    AND(2, args -> Value.of(args.get(0).asBoolean() && args.get(1).asBoolean())),

    /**
     * Computes OR for two booleans.
     * Requires two boolean arguments.
     */
    OR(2, args -> Value.of(args.get(0).asBoolean() || args.get(1).asBoolean())),

    /**
     * Computes NOT for boolean.
     * Requires a boolean arguments.
     */
    NOT(1, args -> Value.of(!args.get(0).asBoolean())),

    /**
     * Checks if a string contains a specified substring.
     * Requires two arguments: the string and the substring to search for.
     */
    CONTAINS(2, args -> Value.of(args.get(0).asString().contains(args.get(1).asString()))),

    /**
     * Concatenates two strings.
     * Requires two arguments: the strings to concatenate.
     */
    CONCAT(2, args -> Value.of(args.get(0).asString() + args.get(1).asString())),

    /**
     * Repeats a string a specified number of times.
     * Requires two arguments: the string to repeat and the number of times to repeat it.
     */
    REPEAT(2, args -> Value.of(args.get(0).asString().repeat(args.get(1).asInteger()))),

    /**
     * Computes the length of a string.
     * Requires one argument: the string whose length is to be calculated.
     */
    LENGTH(1, args -> Value.of(args.get(0).asString().length())),

    /**
     * Converts a value to a string representation.
     * Requires one argument: the value to be converted to a string.
     */
    STRING(1, args -> Value.of(args.get(0).toString())),

    /**
     * Computes the sum of values in a range.
     * Requires one argument: the range of values to sum.
     */
    SUM(1, FunctionUtils::sum),

    /**
     * Computes the average of values in a range.
     * Requires one argument: the range of values to average.
     */
    AVERAGE(1, FunctionUtils::average),

    /**
     * Counts the number of values in a range.
     * Requires one argument: the range of values to count.
     */
    COUNT(1, FunctionUtils::count),

    /**
     * Returns one of two values based on a condition.
     * Requires three arguments: the condition, the value if true, and the value if false.
     */
    IF(3, args -> args.get(0).asBoolean() ? args.get(1) : args.get(2)),

    /**
     * Returns one of two values based on whether an expression results in an error.
     * Requires two arguments: the expression to evaluate and the value to return if there is an error.
     */
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
    @SuppressWarnings("PMD.AvoidCatchingNPE")
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
        return Function.valueOf(name.toUpperCase(Locale.getDefault()));
    }
}
