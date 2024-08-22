package com.sanyavertolet.interview.data.value;

import com.sanyavertolet.interview.exceptions.expressions.ExpressionEvaluationException;
import com.sanyavertolet.interview.exceptions.expressions.ValueCastException;

/**
 * An abstract sealed class representing a value that can be used in expressions and evaluated.
 * The {@code Value} class provides basic arithmetic and comparison operations, and it permits
 * specific subclasses such as {@link BooleanValue}, {@link DoubleValue}, {@link StringValue},
 * {@link IntegerValue}, and {@link IterableValue}.
 * <p>
 * Subclasses of {@code Value} are expected to implement these operations where appropriate.
 * By default, most operations will throw a {@link ValueCastException} if they are not supported
 * by the specific type of value.
 * </p>
 */
public sealed abstract class Value permits BooleanValue, DoubleValue, StringValue, IntegerValue, IterableValue {

    /**
     * Adds this value to another value. By default, throws a {@link ValueCastException}.
     *
     * @param other the value to add to this value.
     * @return the result of the addition.
     * @throws ValueCastException if the addition is not supported.
     */
    public Value plus(Value other) throws ValueCastException {
        throw new ValueCastException("+ (plus)", this, other);
    }

    /**
     * Subtracts another value from this value. By default, throws a {@link ValueCastException}.
     *
     * @param other the value to subtract from this value.
     * @return the result of the subtraction.
     * @throws ValueCastException if the subtraction is not supported.
     */
    public Value minus(Value other) throws ValueCastException {
        throw new ValueCastException("- (minus)", this, other);
    }

    /**
     * Multiplies this value by another value. By default, throws a {@link ValueCastException}.
     *
     * @param other the value to multiply with this value.
     * @return the result of the multiplication.
     * @throws ValueCastException if the multiplication is not supported.
     */
    public Value multiply(Value other) throws ValueCastException {
        throw new ValueCastException("* (multiply)", this, other);
    }

    /**
     * Divides this value by another value. By default, throws a {@link ValueCastException}.
     *
     * @param other the value to divide this value by.
     * @return the result of the division.
     * @throws ValueCastException if the division is not supported.
     */
    public Value divide(Value other) throws ExpressionEvaluationException {
        throw new ValueCastException("/ (divide)", this, other);
    }

    /**
     * Raises this value to the power of another value. By default, throws a {@link ValueCastException}.
     *
     * @param other the exponent value.
     * @return the result of the exponentiation.
     * @throws ValueCastException if the exponentiation is not supported.
     */
    public Value pow(Value other) throws ValueCastException {
        throw new ValueCastException("^ (power)", this, other);
    }

    /**
     * Checks if this value is less than another value. By default, throws a {@link ValueCastException}.
     *
     * @param other the value to compare against.
     * @return the result of the comparison.
     * @throws ValueCastException if the comparison is not supported.
     */
    public Value lt(Value other) throws ValueCastException {
        throw new ValueCastException("< (less than)", this, other);
    }

    /**
     * Checks if this value is greater than another value. By default, throws a {@link ValueCastException}.
     *
     * @param other the value to compare against.
     * @return the result of the comparison.
     * @throws ValueCastException if the comparison is not supported.
     */
    public Value gt(Value other) throws ValueCastException {
        throw new ValueCastException("> (greater than)", this, other);
    }

    /**
     * Checks if this value is less than or equal to another value. By default, throws a {@link ValueCastException}.
     *
     * @param other the value to compare against.
     * @return the result of the comparison.
     * @throws ValueCastException if the comparison is not supported.
     */
    public Value leq(Value other) throws ValueCastException {
        throw new ValueCastException("<= (less than or equal)", this, other);
    }

    /**
     * Checks if this value is greater than or equal to another value. By default, throws a {@link ValueCastException}.
     *
     * @param other the value to compare against.
     * @return the result of the comparison.
     * @throws ValueCastException if the comparison is not supported.
     */
    public Value geq(Value other) throws ValueCastException {
        throw new ValueCastException(">= (greater than or equal)", this, other);
    }

    /**
     * Checks if this value is equal to another value.
     *
     * @param other the value to compare against.
     * @return a {@link BooleanValue} representing the result of the equality comparison.
     */
    public Value eq(Value other) {
        return Value.of(equals(other));
    }

    /**
     * Checks if this value is not equal to another value.
     *
     * @param other the value to compare against.
     * @return a {@link BooleanValue} representing the result of the inequality comparison.
     */
    public Value neq(Value other) {
        return Value.of(!equals(other));
    }

    /**
     * Returns the string representation of this value. By default, throws a {@link ValueCastException}.
     *
     * @return the string representation of this value.
     * @throws ValueCastException if the conversion is not supported.
     */
    public String asString() throws ValueCastException {
        throw new ValueCastException(this, String.class);
    }

    /**
     * Returns the double representation of this value. By default, throws a {@link ValueCastException}.
     *
     * @return the double representation of this value.
     * @throws ValueCastException if the conversion is not supported.
     */
    public Double asDouble() throws ValueCastException {
        throw new ValueCastException(this, Double.class);
    }

    /**
     * Returns the integer representation of this value. By default, throws a {@link ValueCastException}.
     *
     * @return the integer representation of this value.
     * @throws ValueCastException if the conversion is not supported.
     */
    public Integer asInteger() throws ValueCastException {
        throw new ValueCastException(this, Integer.class);
    }

    /**
     * Returns the boolean representation of this value. By default, throws a {@link ValueCastException}.
     *
     * @return the boolean representation of this value.
     * @throws ValueCastException if the conversion is not supported.
     */
    public Boolean asBoolean() throws ValueCastException {
        throw new ValueCastException(this, Boolean.class);
    }

    /**
     * Creates a {@code Value} instance from a double. If the value is an integer, it returns an {@link IntegerValue};
     * otherwise, it returns a {@link DoubleValue}.
     *
     * @param value the double value.
     * @return a {@code Value} instance representing the provided double value.
     */
    public static Value of(Double value) {
        return value.intValue() == value ? new IntegerValue(value.intValue()) : new DoubleValue(value);
    }

    /**
     * Creates a {@code Value} instance from a string.
     *
     * @param value the string value.
     * @return a {@code Value} instance representing the provided string value.
     */
    public static Value of(String value) {
        return new StringValue(value);
    }

    /**
     * Creates a {@code Value} instance from a boolean.
     *
     * @param value the boolean value.
     * @return a {@code Value} instance representing the provided boolean value.
     */
    public static Value of(Boolean value) {
        return new BooleanValue(value);
    }

    /**
     * Creates a {@code Value} instance from an integer.
     *
     * @param value the integer value.
     * @return a {@code Value} instance representing the provided integer value.
     */
    public static Value of(Integer value) {
        return new IntegerValue(value);
    }

    /**
     * Parses a string to determine its corresponding {@code Value} type. The method attempts to parse the string
     * as a boolean, then as an integer, and finally as a double. If none of these conversions are successful,
     * the string is treated as a {@link StringValue}.
     *
     * @param valueText the string representation of the value to be parsed.
     * @return a {@code Value} instance corresponding to the parsed string.
     */
    public static Value parse(String valueText) {
        if (valueText.trim().equalsIgnoreCase("true")) {
            return Value.of(true);
        } else if (valueText.trim().equalsIgnoreCase("false")) {
            return Value.of(false);
        }

        try {
            return Value.of(Integer.valueOf(valueText));
        } catch (NumberFormatException ignored) { }

        try {
            return Value.of(Double.valueOf(valueText));
        } catch (NumberFormatException ignored) { }

        return Value.of(valueText);
    }
}
