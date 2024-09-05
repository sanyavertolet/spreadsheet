package com.sanyavertolet.interview.data.value;

import com.sanyavertolet.interview.exceptions.expressions.ExpressionEvaluationException;
import com.sanyavertolet.interview.exceptions.expressions.ValueCastException;

import java.util.Objects;

/**
 * A final class representing a double value in an expression. The {@code DoubleValue} class extends
 * the {@link Value} class and provides specific implementations for handling arithmetic operations,
 * comparisons, and conversions involving double values.
 */
public final class DoubleValue extends Value {
    private final Double value;

    /**
     * Constructs a {@code DoubleValue} with the specified double value.
     *
     * @param value the double value to be represented by this {@code DoubleValue}.
     */
    public DoubleValue(Double value) {
        this.value = value;
    }

    /**
     * Adds this double value to another {@link Value}. Supports addition with any numeric type.
     *
     * @param other the value to add to this value.
     * @return a {@link Value} representing the sum of this value and the provided value.
     * @throws ValueCastException if the provided value cannot be converted to a numeric type.
     */
    @Override
    public Value plus(Value other) throws ValueCastException {
        return Value.of(value + other.asDouble());
    }

    /**
     * Subtracts another {@link Value} from this double value. Supports subtraction with any numeric type.
     *
     * @param other the value to subtract from this value.
     * @return a {@link Value} representing the difference between this value and the provided value.
     * @throws ValueCastException if the provided value cannot be converted to a numeric type.
     */
    @Override
    public Value minus(Value other) throws ValueCastException {
        return Value.of(value - other.asDouble());
    }

    /**
     * Multiplies this double value by another {@link Value}. Supports multiplication with any numeric type.
     *
     * @param other the value to multiply with this value.
     * @return a {@link Value} representing the product of this value and the provided value.
     * @throws ValueCastException if the provided value cannot be converted to a numeric type.
     */
    @Override
    public Value multiply(Value other) throws ValueCastException {
        return Value.of(value * other.asDouble());
    }

    /**
     * Divides this double value by another {@link Value}. Supports division with any numeric type.
     * Throws an exception if attempting to divide by zero.
     *
     * @param other the value to divide this value by.
     * @return a {@link Value} representing the quotient of this value and the provided value.
     * @throws ExpressionEvaluationException if the division is by zero.
     * @throws ValueCastException if the provided value cannot be converted to a numeric type.
     */
    @Override
    public Value divide(Value other) throws ExpressionEvaluationException {
        if (other.asDouble().equals(0.0)) {
            throw new ExpressionEvaluationException("Division by zero");
        }
        return Value.of(value / other.asDouble());
    }

    /**
     * Raises this double value to the power of another {@link Value}. Supports exponentiation with any numeric type.
     *
     * @param other the exponent value.
     * @return a {@link Value} representing the result of the exponentiation.
     * @throws ValueCastException if the provided value cannot be converted to a numeric type.
     */
    @Override
    public Value pow(Value other) throws ValueCastException {
        return Value.of(Math.pow(value, other.asDouble()));
    }

    /**
     * Checks if this double value is less than another {@link Value}.
     *
     * @param other the value to compare against.
     * @return a {@link Value} representing whether this value is less than the provided value.
     * @throws ValueCastException if the provided value cannot be converted to a numeric type.
     */
    @Override
    public Value lt(Value other) throws ValueCastException {
        return Value.of(asDouble() < other.asDouble());
    }

    /**
     * Checks if this double value is greater than another {@link Value}.
     *
     * @param other the value to compare against.
     * @return a {@link Value} representing whether this value is greater than the provided value.
     * @throws ValueCastException if the provided value cannot be converted to a numeric type.
     */
    @Override
    public Value gt(Value other) throws ValueCastException {
        return Value.of(asDouble() > other.asDouble());
    }

    /**
     * Checks if this double value is less than or equal to another {@link Value}.
     *
     * @param other the value to compare against.
     * @return a {@link Value} representing whether this value is less than or equal to the provided value.
     * @throws ValueCastException if the provided value cannot be converted to a numeric type.
     */
    @Override
    public Value leq(Value other) throws ValueCastException {
        return Value.of(asDouble() <= other.asDouble());
    }

    /**
     * Checks if this double value is greater than or equal to another {@link Value}.
     *
     * @param other the value to compare against.
     * @return a {@link Value} representing whether this value is greater than or equal to the provided value.
     * @throws ValueCastException if the provided value cannot be converted to a numeric type.
     */
    @Override
    public Value geq(Value other) throws ValueCastException {
        return Value.of(asDouble() >= other.asDouble());
    }

    /**
     * Returns the double representation of this value.
     *
     * @return the double value.
     */
    @Override
    public Double asDouble() {
        return value;
    }

    /**
     * Returns a string representation of this {@code DoubleValue}.
     *
     * @return the string representation of the double value.
     */
    @Override
    public String toString() {
        return value.toString();
    }

    /**
     * Compares this {@code DoubleValue} to another object for equality.
     * Returns {@code true} if the other object is a numeric type and represents the same numeric value.
     *
     * @param obj the object to compare with this {@code DoubleValue}.
     * @return {@code true} if the specified object is equal to this {@code DoubleValue}; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof IntegerValue integerValue) {
            return asDouble().equals(integerValue.asDouble());
        } else if (obj instanceof DoubleValue doubleValue) {
            return asDouble().equals(doubleValue.asDouble());
        } else if (obj instanceof Double doubleClass) {
            return asDouble().equals(doubleClass);
        } else if (obj instanceof Integer integer) {
            return asDouble().equals(integer.doubleValue());
        }
        return false;
    }

    /**
     * Returns the hash code for this {@code DoubleValue}.
     *
     * @return the hash code of the double value.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
