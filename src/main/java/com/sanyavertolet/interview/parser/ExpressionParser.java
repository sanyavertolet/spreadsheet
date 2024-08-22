package com.sanyavertolet.interview.parser;

import com.sanyavertolet.interview.exceptions.expressions.ExpressionParsingException;
import com.sanyavertolet.interview.math.expressions.Expression;

/**
 * An interface for parsing expressions into {@link Expression} objects.
 * The {@code ExpressionParser} interface defines the method for converting a string representation
 * of an expression into a structured {@link Expression} that can be evaluated or further processed.
 */
public interface ExpressionParser {

    /**
     * Parses the given expression string and converts it into an {@link Expression} object.
     * The method processes the input string to build a structured representation of the expression,
     * which can be evaluated or manipulated programmatically.
     *
     * @param expression the string representation of the expression to be parsed.
     * @return an {@link Expression} object representing the parsed expression.
     * @throws ExpressionParsingException if an error occurs during parsing, such as invalid syntax or unrecognized tokens.
     */
    Expression parse(String expression) throws ExpressionParsingException;
}
