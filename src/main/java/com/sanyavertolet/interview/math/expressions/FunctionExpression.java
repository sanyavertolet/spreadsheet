package com.sanyavertolet.interview.math.expressions;

import com.sanyavertolet.interview.math.Function;
import com.sanyavertolet.interview.exceptions.FunctionArgumentException;

import java.util.List;

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
}
