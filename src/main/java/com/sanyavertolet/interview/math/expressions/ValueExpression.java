package com.sanyavertolet.interview.math.expressions;

import com.sanyavertolet.interview.data.value.Value;
import com.sanyavertolet.interview.math.CellReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an expression that holds a constant {@link Value}. The {@code ValueExpression} class
 * is a specific type of {@link Expression} that encapsulates a single value.
 */
public class ValueExpression extends Expression {
    private final Value value;

    /**
     * Constructs a {@code ValueExpression} from a raw string.
     * The string is parsed into a {@link Value}.
     *
     * @param rawString the string to be parsed into a value.
     */
    public ValueExpression(String rawString) {
        this.value = Value.of(rawString);
    }

    /**
     * Constructs a {@code ValueExpression} with a given {@link Value}.
     *
     * @param value the value to be encapsulated by this expression.
     */
    private ValueExpression(Value value) {
        this.value = value;
    }

    /**
     * Parses a string into a {@code ValueExpression}.
     * The string is interpreted and converted into an appropriate {@link Value}.
     *
     * @param value the string to be parsed into a value.
     * @return a new {@code ValueExpression} containing the parsed value.
     */
    public static ValueExpression parse(String value) {
        return new ValueExpression(Value.parse(value));
    }

    /**
     * Returns the {@link Value} contained within this expression.
     *
     * @return the value encapsulated by this expression.
     */
    public Value getValue() {
        return value;
    }

    /**
     * Returns a pretty-printed string representation of this expression, with the specified indentation.
     *
     * @param shift the number of dots to prepend for indentation.
     * @return a formatted string representing the value.
     */
    @Override
    public String prettyPrint(int shift) {
        return ".".repeat(shift) + value + "\n";
    }

    /**
     * Returns an empty list of {@link CellReference} objects, as this expression does not contain any cell references.
     *
     * @return an empty list of cell references.
     */
    @Override
    public List<CellReference> getCellReferences() {
        return new ArrayList<>();
    }

    /**
     * Returns a string representation of the value.
     *
     * @return the value as a string.
     */
    @Override
    public String toString() {
        return value.toString();
    }
}
