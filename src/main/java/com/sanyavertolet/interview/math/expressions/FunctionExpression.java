package com.sanyavertolet.interview.math.expressions;

import com.sanyavertolet.interview.math.CellReference;
import com.sanyavertolet.interview.math.Function;
import com.sanyavertolet.interview.exceptions.FunctionArgumentException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionExpression extends Expression {
    private final List<Expression> arguments;
    Function function;

    public FunctionExpression(Function function, List<Expression> arguments) throws FunctionArgumentException {
        this.function = function;
        if (arguments.size() != function.getArgumentsSize()) {
            throw new FunctionArgumentException("Insufficient number of arguments for function " + function);
        }
        this.arguments = arguments;
    }

    @Override
    public List<CellReference> getCellReferences() {
        List<CellReference> cellReferences = new ArrayList<>();
        for (Expression argument : arguments) {
            cellReferences.addAll(argument.getCellReferences());
        }
        return cellReferences;
    }

    @Override
    public String prettyPrint(int shift) {
        StringBuilder builder = new StringBuilder();
        builder.append(".".repeat(shift)).append(function.name()).append("\n");
        for (Expression argument : arguments) {
            builder.append(argument.prettyPrint(shift + 2));
        }
        return builder.toString();
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    public Function getFunction() {
        return function;
    }

    @Override
    public String toString() {
        return function.name() + arguments.stream().map(Object::toString).collect(Collectors.joining(", ", "(", ")"));
    }
}
