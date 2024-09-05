package com.sanyavertolet.interview.math.operators;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Represents a non-function operator used in expressions, such as arithmetic or comparison operators.
 * The {@code NonFunctionOperator} class extends the {@link Operator} class and provides additional
 * information about the operator's type, associativity, and precedence.
 */
public class NonFunctionOperator extends Operator {
    private final Type type;

    /**
     * Enum representing the different types of non-function operators.
     * Each operator type has an associated symbol.
     */
    public enum Type {
        /**
         * Represents the opening parenthesis '('.
         * Used to group expressions or arguments.
         */
        OPEN_PARENTHESIS("("),

        /**
         * Represents the closing parenthesis ')'.
         * Used to close groups of expressions or arguments.
         */
        CLOSE_PARENTHESIS(")"),

        /**
         * Represents the comma ','.
         * Used to separate arguments or values in lists or function calls.
         */
        COMMA(","),

        /**
         * Represents the colon ':'.
         * Used in ranges to specify the start and end points.
         */
        COLON(":"),

        /**
         * Represents the addition operator '+'.
         * Used to add two values or expressions.
         */
        PLUS("+"),

        /**
         * Represents the subtraction operator '-'.
         * Used to subtract one value or expression from another.
         */
        MINUS("-"),

        /**
         * Represents the multiplication operator '*'.
         * Used to multiply two values or expressions.
         */
        MULTIPLY("*"),

        /**
         * Represents the division operator '/'.
         * Used to divide one value or expression by another.
         */
        DIVIDE("/"),

        /**
         * Represents the power operator '^'.
         * Used to raise one value or expression to the power of another.
         */
        POWER("^"),

        /**
         * Represents the less than operator.
         * Used to compare if one value or expression is less than another.
         */
        LT("<"),

        /**
         * Represents the less than or equal to operator.
         * Used to compare if one value or expression is less than or equal to another.
         */
        LEQ("<="),

        /**
         * Represents the greater than operator '>'.
         * Used to compare if one value or expression is greater than another.
         */
        GT(">"),

        /**
         * Represents the greater than or equal to operator '>='.
         * Used to compare if one value or expression is greater than or equal to another.
         */
        GEQ(">="),

        /**
         * Represents the equality operator '=='.
         * Used to compare if two values or expressions are equal.
         */
        EQ("=="),

        /**
         * Represents the inequality operator '!='.
         * Used to compare if two values or expressions are not equal.
         */
        NEQ("!="),
        ;

        private final String symbol;

        /**
         * Constructs a {@code Type} with the specified symbol.
         *
         * @param symbol the symbol representing the operator.
         */
        Type(String symbol) {
            this.symbol = symbol;
        }

        /**
         * Returns the symbol associated with this operator type.
         *
         * @return the symbol as a string.
         */
        String getSymbol() {
            return symbol;
        }
    }

    /**
     * Enum representing the associativity of an operator.
     * Operators can be left-associative or right-associative.
     */
    public enum Associativity {
        /**
         * Represents left-associative operators.
         * Operators are evaluated from left to right.
         * For example, in the expression "1 - 2 - 3", the subtraction is evaluated as "(1 - 2) - 3".
         */
        LEFT,

        /**
         * Represents right-associative operators.
         * Operators are evaluated from right to left.
         * For example, in the expression "a ^ b ^ c", the exponentiation is evaluated as "a ^ (b ^ c)".
         */
        RIGHT,
    }

    /**
     * Constructs a {@code NonFunctionOperator} based on the provided symbol.
     *
     * @param symbol the symbol of the operator.
     * @throws NoSuchElementException if the symbol does not correspond to a known operator type.
     */
    public NonFunctionOperator(String symbol) {
        super(symbol);
        Optional<Type> operator = Arrays.stream(Type.values()).filter(it -> it.getSymbol().equals(symbol)).findFirst();
        if (operator.isEmpty()) {
            throw new NoSuchElementException("Operator not found: " + symbol);
        }
        type = operator.get();
    }

    /**
     * Returns the type of this operator.
     *
     * @return the {@link Type} of this operator.
     */
    public Type getType() {
        return type;
    }

    /**
     * Returns the associativity of this operator.
     *
     * @return the {@link Associativity} of this operator.
     * @throws IllegalStateException if the operator does not have a defined associativity.
     */
    public Associativity getAssociativity() {
        return switch (type) {
            case PLUS, MINUS, MULTIPLY, DIVIDE, EQ, NEQ, LT, GT, LEQ, GEQ -> Associativity.LEFT;
            case POWER -> Associativity.RIGHT;
            default -> throw new IllegalStateException("Operator has no associativity: " + this);
        };
    }

    /**
     * Returns the precedence of this operator.
     * Higher precedence operators are evaluated before lower precedence operators.
     *
     * @return the precedence level as an integer.
     * @throws IllegalStateException if the operator does not have a defined precedence.
     */
    public int getPrecedence() {
        return switch (type) {
            case EQ, NEQ, LT, GT, LEQ, GEQ -> 1;
            case PLUS, MINUS -> 2;
            case MULTIPLY, DIVIDE -> 3;
            case POWER -> 4;
            default -> throw new IllegalStateException("Operator has no priority: " + this);
        };
    }
}
