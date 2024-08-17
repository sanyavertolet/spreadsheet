package com.sanyavertolet.interview.math.operators;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

public class NonFunctionOperator extends Operator {
    private final Type type;

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
        NEQ("!="),
        ;

        private final String symbol;

        Type(String symbol) {
            this.symbol = symbol;
        }

        String symbol() {
            return symbol;
        }
    }

    public enum Associativity {
        LEFT,
        RIGHT,
    }

    public NonFunctionOperator(String symbol) {
        super(symbol);
        Optional<Type> operator = Arrays.stream(Type.values()).filter(it -> it.symbol().equals(symbol)).findFirst();
        if(operator.isEmpty()) {
            throw new NoSuchElementException("Operator not found: " + symbol);
        }
        type = operator.get();
    }

    public Type type() {
        return type;
    }

    public Associativity associativity() {
        return switch (type) {
            case PLUS, MINUS, MULTIPLY, DIVIDE, EQ, NEQ, LT, GT, LEQ, GEQ -> Associativity.LEFT;
            case POWER -> Associativity.RIGHT;
            default -> throw new IllegalStateException("Operator has no associativity: " + this);
        };
    }

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
