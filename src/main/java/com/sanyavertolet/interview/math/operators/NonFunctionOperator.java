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
        OPEN_PARENTHESIS("("),
        CLOSE_PARENTHESIS(")"),
        COMMA(","),
        COLON(":"),

        PLUS("+"),
        MINUS("-"),
        MULTIPLY("*"),
        DIVIDE("/"),
        POWER("^"),

        LT("<"),
        LEQ("<="),
        GT(">"),
        GEQ(">="),
        EQ("=="),
        NEQ("!=");

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
        String symbol() {
            return symbol;
        }
    }

    /**
     * Enum representing the associativity of an operator.
     * Operators can be left-associative or right-associative.
     */
    public enum Associativity {
        LEFT,
        RIGHT
    }

    /**
     * Constructs a {@code NonFunctionOperator} based on the provided symbol.
     *
     * @param symbol the symbol of the operator.
     * @throws NoSuchElementException if the symbol does not correspond to a known operator type.
     */
    public NonFunctionOperator(String symbol) {
        super(symbol);
        Optional<Type> operator = Arrays.stream(Type.values()).filter(it -> it.symbol().equals(symbol)).findFirst();
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
    public Type type() {
        return type;
    }

    /**
     * Returns the associativity of this operator.
     *
     * @return the {@link Associativity} of this operator.
     * @throws IllegalStateException if the operator does not have a defined associativity.
     */
    public Associativity associativity() {
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
    public int precedence() {
        return switch (type) {
            case EQ, NEQ, LT, GT, LEQ, GEQ -> 1;
            case PLUS, MINUS -> 2;
            case MULTIPLY, DIVIDE -> 3;
            case POWER -> 4;
            default -> throw new IllegalStateException("Operator has no priority: " + this);
        };
    }
}
