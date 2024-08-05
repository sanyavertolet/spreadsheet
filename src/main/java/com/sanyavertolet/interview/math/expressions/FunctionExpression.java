package com.sanyavertolet.interview.math.expressions;

import com.sanyavertolet.interview.math.Function;
import com.sanyavertolet.interview.exceptions.EvaluationException;
import com.sanyavertolet.interview.exceptions.FunctionArgumentException;

import java.util.ArrayList;
import java.util.List;

public class FunctionExpression extends Expression {
    private List<Expression> arguments;
    Function function;

    public FunctionExpression(Function function) {
        this.function = function;
        this.arguments = new ArrayList<>();
    }

    public void setArguments(List<Expression> arguments) throws FunctionArgumentException {
        if (arguments.size() != function.getArgumentsSize()) {
            throw new FunctionArgumentException("Insufficient number of arguments for function " + function);
        }
        this.arguments = arguments;
    }

    @Override
    public void recalculate() throws EvaluationException {
        value = function.evaluate(arguments);
    }
}
