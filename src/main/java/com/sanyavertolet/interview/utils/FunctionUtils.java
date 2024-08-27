package com.sanyavertolet.interview.utils;

import com.sanyavertolet.interview.data.value.*;
import com.sanyavertolet.interview.exceptions.expressions.ExpressionEvaluationException;

import java.util.List;

/**
 * Utility class that provides logic for evaluating common functions, such as sum, count, and average,
 * on a range of numeric values.
 */
public class FunctionUtils {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private FunctionUtils() {}

    /**
     * Calculates the sum of all numeric values within the provided list of arguments.
     * The arguments must contain exactly one {@link IterableValue} representing a range of values.
     *
     * @param values the function arguments, which should contain one {@link IterableValue}.
     * @return the sum of all numeric values in the range as a {@link Value}.
     * @throws ExpressionEvaluationException if the argument is not an {@link IterableValue}.
     */
    public static Value sum(List<Value> values) throws ExpressionEvaluationException {
        if (!(values.get(0) instanceof IterableValue iterableValue)) {
            throw new ExpressionEvaluationException("SUM function should have only one valid range argument");
        }
        return sum(iterableValue);
    }

    /**
     * Helper method that performs the sum calculation on an {@link IterableValue}.
     *
     * @param iterableValue the iterable value containing the numeric values to sum.
     * @return the sum of all numeric values in the iterable as a {@link Value}.
     * @throws ExpressionEvaluationException if an error occurs during the calculation.
     */
    private static Value sum(IterableValue iterableValue) throws ExpressionEvaluationException {
        Value accumulator = new IntegerValue(0);
        for (Value value : iterableValue.getValues()) {
            if (value instanceof IntegerValue || value instanceof DoubleValue) {
                accumulator = accumulator.plus(value);
            }
        }
        return accumulator;
    }

    /**
     * Counts the number of numeric values within the provided list of arguments.
     * The arguments must contain exactly one {@link IterableValue} representing a range of values.
     *
     * @param values the function arguments, which should contain one {@link IterableValue}.
     * @return the count of all numeric values in the range as a {@link Value}.
     * @throws ExpressionEvaluationException if the argument is not an {@link IterableValue}.
     */
    public static Value count(List<Value> values) throws ExpressionEvaluationException {
        if (!(values.get(0) instanceof IterableValue iterableValue)) {
            throw new ExpressionEvaluationException("COUNT function should have only one valid range argument");
        }
        return count(iterableValue);
    }

    /**
     * Helper method that performs the count calculation on an {@link IterableValue}.
     *
     * @param iterableValue the iterable value containing the numeric values to count.
     * @return the count of all numeric values in the iterable as a {@link Value}.
     */
    private static Value count(IterableValue iterableValue) {
        int counter = 0;
        for (Value value : iterableValue.getValues()) {
            if (value instanceof IntegerValue || value instanceof DoubleValue) {
                counter += 1;
            }
        }
        return Value.of(counter);
    }

    /**
     * Calculates the average of all numeric values within the provided list of arguments.
     * The arguments must contain exactly one {@link IterableValue} representing a range of values.
     *
     * @param values the function arguments, which should contain one {@link IterableValue}.
     * @return the average of all numeric values in the range as a {@link Value}.
     * @throws ExpressionEvaluationException if the argument is not an {@link IterableValue}.
     */
    public static Value average(List<Value> values) throws ExpressionEvaluationException {
        if (!(values.get(0) instanceof IterableValue iterableValue)) {
            throw new ExpressionEvaluationException("AVERAGE function should have only one valid range argument");
        }
        Value sum = sum(iterableValue);
        Value count = count(iterableValue);
        return sum.divide(count);
    }
}
