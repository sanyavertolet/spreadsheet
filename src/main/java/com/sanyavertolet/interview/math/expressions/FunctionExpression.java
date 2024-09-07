package com.sanyavertolet.interview.math.expressions;

import com.sanyavertolet.interview.math.CellReference;
import com.sanyavertolet.interview.math.Function;
import com.sanyavertolet.interview.exceptions.FunctionArgumentException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents an expression that involves a function and its arguments.
 * The {@code FunctionExpression} class encapsulates a {@link Function} and a list of {@link Expression} arguments
 * that are passed to the function for evaluation.
 */
public class FunctionExpression extends Expression {
    private final List<Expression> arguments;
    private final Function function;

    /**
     * Constructs a {@code FunctionExpression} with the specified function and arguments.
     * The number of arguments must match the expected number of arguments for the function.
     *
     * @param function  the {@link Function} to be applied.
     * @param arguments the list of {@link Expression} arguments for the function.
     * @throws FunctionArgumentException if the number of arguments does not match the function's expected argument count.
     */
    public FunctionExpression(Function function, List<Expression> arguments) throws FunctionArgumentException {
        this.function = function;
        if (arguments.size() != function.getArgumentsSize()) {
            throw new FunctionArgumentException("Insufficient number of arguments for function " + function);
        }
        this.arguments = arguments;
    }

    /**
     * Returns a list of all {@link CellReference} objects within the arguments of this function expression.
     *
     * @return a list of cell references used in the function arguments.
     */
    @Override
    public List<CellReference> getCellReferences() {
        List<CellReference> cellReferences = new ArrayList<>();
        for (Expression argument : arguments) {
            cellReferences.addAll(argument.getCellReferences());
        }
        return cellReferences;
    }

    /**
     * Returns a pretty-printed string representation of this function expression, with the specified indentation.
     *
     * @param shift the number of dots to prepend for indentation.
     * @return a formatted string representing the function expression.
     */
    @Override
    public String prettyPrint(int shift) {
        StringBuilder builder = new StringBuilder();
        builder.append(".".repeat(shift)).append(function.name()).append('\n');
        for (Expression argument : arguments) {
            builder.append(argument.prettyPrint(shift + 2));
        }
        return builder.toString();
    }

    /**
     * Returns the list of arguments for this function expression.
     *
     * @return the list of {@link Expression} arguments.
     */
    public List<Expression> getArguments() {
        return arguments;
    }

    /**
     * Returns the function associated with this expression.
     *
     * @return the {@link Function} used in this expression.
     */
    public Function getFunction() {
        return function;
    }

    /**
     * Returns a string representation of the function expression in the format "FUNCTION(arg1, arg2, ...)".
     *
     * @return the function expression as a string.
     */
    @Override
    public String toString() {
        return function.name() + arguments.stream().map(Object::toString).collect(Collectors.joining(", ", "(", ")"));
    }
}
