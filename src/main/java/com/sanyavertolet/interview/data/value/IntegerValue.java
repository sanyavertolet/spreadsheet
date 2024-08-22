package com.sanyavertolet.interview.data.value;

import com.sanyavertolet.interview.exceptions.expressions.ExpressionEvaluationException;
import com.sanyavertolet.interview.exceptions.expressions.ValueCastException;

import java.util.Objects;

/**
 * A final class representing an integer value in an expression. The {@code IntegerValue} class extends
 * the {@link Value} class and provides specific implementations for handling arithmetic operations,
 * comparisons, and conversions involving integer values.
 */
public final class IntegerValue extends Value {
    private final Integer value;

    /**
     * Constructs an {@code IntegerValue} with the specified integer value.
     *
     * @param value the integer value to be represented by this {@code IntegerValue}.
     */
    public IntegerValue(Integer value) {
        this.value = value;
    }

    /**
     * Adds this integer value to another {@link Value}. Supports addition with both {@link IntegerValue}
     * and {@link DoubleValue}.
     *
     * @param other the value to add to this value.
     * @return a {@link Value} representing the sum of this value and the provided value.
     * @throws ValueCastException if the provided value cannot be converted to a numeric type.
     */
    @Override
    public Value plus(Value other) throws ValueCastException {
        if (other instanceof DoubleValue) {
            return Value.of(value + other.asDouble());
        }
        return Value.of(value + other.asInteger());
    }

    /**
     * Subtracts another {@link Value} from this integer value. Supports subtraction with both {@link IntegerValue}
     * and {@link DoubleValue}.
     *
     * @param other the value to subtract from this value.
     * @return a {@link Value} representing the difference between this value and the provided value.
     * @throws ValueCastException if the provided value cannot be converted to a numeric type.
     */
    @Override
    public Value minus(Value other) throws ValueCastException {
        if (other instanceof DoubleValue) {
            return Value.of(value - other.asDouble());
        }
        return Value.of(value - other.asInteger());
    }

    /**
     * Multiplies this integer value by another {@link Value}. Supports multiplication with both {@link IntegerValue}
     * and {@link DoubleValue}.
     *
     * @param other the value to multiply with this value.
     * @return a {@link Value} representing the product of this value and the provided value.
     * @throws ValueCastException if the provided value cannot be converted to a numeric type.
     */
    @Override
    public Value multiply(Value other) throws ValueCastException {
        if (other instanceof DoubleValue) {
            return Value.of(value * other.asDouble());
        }
        return Value.of(value * other.asInteger());
    }

    /**
     * Divides this integer value by another {@link Value}. Supports division with both {@link IntegerValue}
     * and {@link DoubleValue}. Throws an exception if attempting to divide by zero.
     *
     * @param other the value to divide this value by.
     * @return a {@link Value} representing the quotient of this value and the provided value.
     * @throws ExpressionEvaluationException if the division is by zero.
     * @throws ValueCastException if the provided value cannot be converted to a numeric type.
     */
    @Override
    public Value divide(Value other) throws ExpressionEvaluationException {
        if (other.asDouble() == 0.0) {
            throw new ExpressionEvaluationException("Division by zero");
        }
        if (other instanceof DoubleValue) {
            return Value.of(value / other.asDouble());
        }
        return Value.of(value / other.asInteger());
    }

    /**
     * Raises this integer value to the power of another {@link Value}. Supports exponentiation with any numeric type.
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
     * Checks if this integer value is less than another {@link Value}.
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
     * Checks if this integer value is greater than another {@link Value}.
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
     * Checks if this integer value is less than or equal to another {@link Value}.
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
     * Checks if this integer value is greater than or equal to another {@link Value}.
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
     * Returns the double representation of this integer value.
     *
     * @return the double value.
     */
    @Override
    public Double asDouble() {
        return value.doubleValue();
    }

    /**
     * Returns the integer representation of this value.
     *
     * @return the integer value.
     */
    @Override
    public Integer asInteger() {
        return value;
    }

    /**
     * Returns a string representation of this {@code IntegerValue}.
     *
     * @return the string representation of the integer value.
     */
    @Override
    public String toString() {
        return value.toString();
    }

    /**
     * Compares this {@code IntegerValue} to another object for equality.
     * Returns {@code true} if the other object is a numeric type and represents the same numeric value.
     *
     * @param obj the object to compare with this {@code IntegerValue}.
     * @return {@code true} if the specified object is equal to this {@code IntegerValue}; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof IntegerValue integerValue) {
            return asInteger().equals(integerValue.asInteger());
        } else if (obj instanceof DoubleValue doubleValue) {
            return asDouble().equals(doubleValue.asDouble());
        } else if (obj instanceof Double doubleClass) {
            return asDouble().equals(doubleClass);
        } else if (obj instanceof Integer integer) {
            return asInteger().equals(integer);
        }
        return false;
    }

    /**
     * Returns the hash code for this {@code IntegerValue}.
     *
     * @return the hash code of the integer value.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
