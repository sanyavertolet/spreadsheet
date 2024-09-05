package com.sanyavertolet.interview.data.value;

import com.sanyavertolet.interview.exceptions.expressions.ExpressionEvaluationException;
import com.sanyavertolet.interview.exceptions.expressions.ValueCastException;

import java.util.Objects;

/**
 * A final class representing a boolean value in an expression. The {@code BooleanValue} class extends
 * the {@link Value} class and provides specific implementations for handling arithmetic operations,
 * comparisons, and conversions involving boolean values.
 */
public final class BooleanValue extends Value {
    private final Boolean value;

    /**
     * Constructs a {@code BooleanValue} with the specified boolean value.
     *
     * @param value the boolean value to be represented by this {@code BooleanValue}.
     */
    public BooleanValue(Boolean value) {
        this.value = value;
    }

    /**
     * Adds this boolean value to another {@link Value}. The boolean is first converted to an integer (1 for true, 0 for false),
     * and then the addition is performed. Supports addition with {@link BooleanValue}, {@link IntegerValue}, and {@link DoubleValue}.
     *
     * @param other the value to add to this value.
     * @return a {@link Value} representing the sum of this value and the provided value.
     * @throws ValueCastException if the provided value cannot be converted to a numeric type.
     */
    @Override
    public Value plus(Value other) throws ValueCastException {
        if (other instanceof BooleanValue || other instanceof IntegerValue) {
            return Value.of(asInteger() + other.asInteger());
        } else if (other instanceof DoubleValue) {
            return Value.of(asDouble() + other.asDouble());
        }
        return super.plus(other);
    }

    /**
     * Subtracts another {@link Value} from this boolean value. The boolean is first converted to an integer (1 for true, 0 for false),
     * and then the subtraction is performed. Supports subtraction with {@link BooleanValue}, {@link IntegerValue}, and {@link DoubleValue}.
     *
     * @param other the value to subtract from this value.
     * @return a {@link Value} representing the difference between this value and the provided value.
     * @throws ValueCastException if the provided value cannot be converted to a numeric type.
     */
    @Override
    public Value minus(Value other) throws ValueCastException {
        if (other instanceof BooleanValue || other instanceof IntegerValue) {
            return Value.of(asInteger() - other.asInteger());
        } else if (other instanceof DoubleValue) {
            return Value.of(asDouble() - other.asDouble());
        }
        return super.minus(other);
    }

    /**
     * Multiplies this boolean value by another {@link Value}. The boolean is first converted to an integer (1 for true, 0 for false),
     * and then the multiplication is performed. Supports multiplication with {@link BooleanValue}, {@link IntegerValue}, and {@link DoubleValue}.
     *
     * @param other the value to multiply with this value.
     * @return a {@link Value} representing the product of this value and the provided value.
     * @throws ValueCastException if the provided value cannot be converted to a numeric type.
     */
    @Override
    public Value multiply(Value other) throws ValueCastException {
        if (other instanceof BooleanValue || other instanceof IntegerValue) {
            return Value.of(asInteger() * other.asInteger());
        } else if (other instanceof DoubleValue) {
            return Value.of(asDouble() * other.asDouble());
        }
        return super.multiply(other);
    }

    /**
     * Divides this boolean value by another {@link Value}. The boolean is first converted to an integer (1 for true, 0 for false),
     * and then the division is performed. Supports division with {@link BooleanValue}, {@link IntegerValue}, and {@link DoubleValue}.
     * Throws an exception if attempting to divide by zero.
     *
     * @param other the value to divide this value by.
     * @return a {@link Value} representing the quotient of this value and the provided value.
     * @throws ExpressionEvaluationException if the division is by zero.
     * @throws ValueCastException            if the provided value cannot be converted to a numeric type.
     */
    @Override
    public Value divide(Value other) throws ExpressionEvaluationException {
        if (other.asDouble().equals(0.0)) {
            throw new ExpressionEvaluationException("Division by zero");
        }
        if (other instanceof BooleanValue || other instanceof IntegerValue) {
            return Value.of(asInteger() / other.asInteger());
        } else if (other instanceof DoubleValue) {
            return Value.of(asDouble() / other.asDouble());
        }
        return super.divide(other);
    }

    /**
     * Raises this boolean value to the power of another {@link Value}. The boolean is first converted to a double (1.0 for true, 0.0 for false),
     * and then the exponentiation is performed. Supports exponentiation with any numeric type.
     *
     * @param other the exponent value.
     * @return a {@link Value} representing the result of the exponentiation.
     * @throws ValueCastException if the provided value cannot be converted to a numeric type.
     */
    @Override
    public Value pow(Value other) throws ValueCastException {
        if (other instanceof IntegerValue || other instanceof DoubleValue || other instanceof BooleanValue) {
            return Value.of(Math.pow(asDouble(), other.asDouble()));
        }
        return super.pow(other);
    }

    /**
     * Checks if this boolean value is less than another {@link Value}. The boolean is first converted to a double (1.0 for true, 0.0 for false),
     * and then the comparison is performed.
     *
     * @param other the value to compare against.
     * @return a {@link Value} representing whether this value is less than the provided value.
     * @throws ValueCastException if the provided value cannot be converted to a numeric type.
     */
    @Override
    public Value lt(Value other) throws ValueCastException {
        if (other instanceof IntegerValue || other instanceof DoubleValue || other instanceof BooleanValue) {
            return Value.of(asDouble() < other.asDouble());
        }
        return super.lt(other);
    }

    /**
     * Checks if this boolean value is greater than another {@link Value}. The boolean is first converted to a double (1.0 for true, 0.0 for false),
     * and then the comparison is performed.
     *
     * @param other the value to compare against.
     * @return a {@link Value} representing whether this value is greater than the provided value.
     * @throws ValueCastException if the provided value cannot be converted to a numeric type.
     */
    @Override
    public Value gt(Value other) throws ValueCastException {
        if (other instanceof IntegerValue || other instanceof DoubleValue) {
            return Value.of(asDouble() > other.asDouble());
        }
        return super.gt(other);
    }

    /**
     * Checks if this boolean value is less than or equal to another {@link Value}. The boolean is first converted to a double (1.0 for true, 0.0 for false),
     * and then the comparison is performed.
     *
     * @param other the value to compare against.
     * @return a {@link Value} representing whether this value is less than or equal to the provided value.
     * @throws ValueCastException if the provided value cannot be converted to a numeric type.
     */
    @Override
    public Value leq(Value other) throws ValueCastException {
        if (other instanceof IntegerValue || other instanceof DoubleValue || other instanceof BooleanValue) {
            return Value.of(asDouble() <= other.asDouble());
        }
        return super.leq(other);
    }

    /**
     * Checks if this boolean value is greater than or equal to another {@link Value}. The boolean is first converted to a double (1.0 for true, 0.0 for false),
     * and then the comparison is performed.
     *
     * @param other the value to compare against.
     * @return a {@link Value} representing whether this value is greater than or equal to the provided value.
     * @throws ValueCastException if the provided value cannot be converted to a numeric type.
     */
    @Override
    public Value geq(Value other) throws ValueCastException {
        if (other instanceof IntegerValue || other instanceof DoubleValue || other instanceof BooleanValue) {
            return Value.of(asDouble() >= other.asDouble());
        }
        return super.geq(other);
    }

    /**
     * Returns the boolean representation of this value.
     *
     * @return the boolean value.
     */
    @Override
    public Boolean asBoolean() {
        return value;
    }

    /**
     * Returns the double representation of this value. True is represented as 1.0 and false as 0.0.
     *
     * @return the double value.
     */
    @Override
    public Double asDouble() {
        return value ? 1.0 : 0.0;
    }

    /**
     * Returns the integer representation of this value. True is represented as 1 and false as 0.
     *
     * @return the integer value.
     */
    @Override
    public Integer asInteger() {
        return value ? 1 : 0;
    }

    /**
     * Returns a string representation of this {@code BooleanValue}.
     *
     * @return the string representation of the boolean value.
     */
    @Override
    public String toString() {
        return value.toString();
    }

    /**
     * Compares this {@code BooleanValue} to another object for equality.
     * Returns {@code true} if the other object is a {@code BooleanValue} or {@code Boolean} and represents the same boolean value.
     *
     * @param obj the object to compare with this {@code BooleanValue}.
     * @return {@code true} if the specified object is equal to this {@code BooleanValue}; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof BooleanValue booleanValue) {
            return asBoolean() == booleanValue.asBoolean();
        } else if (obj instanceof Boolean booleanClass) {
            return asBoolean() == booleanClass;
        }
        return false;
    }

    /**
     * Returns the hash code for this {@code BooleanValue}.
     *
     * @return the hash code of the boolean value.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
