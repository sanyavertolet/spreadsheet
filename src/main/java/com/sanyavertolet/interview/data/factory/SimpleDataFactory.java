package com.sanyavertolet.interview.data.factory;

import com.sanyavertolet.interview.data.*;
import com.sanyavertolet.interview.data.value.Value;
import com.sanyavertolet.interview.exceptions.expressions.ExpressionEvaluationException;
import com.sanyavertolet.interview.exceptions.expressions.ExpressionParsingException;
import com.sanyavertolet.interview.math.expressions.Expression;
import com.sanyavertolet.interview.math.expressions.evaluator.ExpressionEvaluator;
import com.sanyavertolet.interview.parser.ExpressionParser;
import com.sanyavertolet.interview.parser.ShuntingYardParser;

public class SimpleDataFactory implements DataFactory {
    private final ExpressionParser expressionParser;
    private final ExpressionEvaluator expressionEvaluator;

    public SimpleDataFactory(ExpressionEvaluator expressionEvaluator) {
        this.expressionParser = new ShuntingYardParser();
        this.expressionEvaluator = expressionEvaluator;
    }

    @Override
    public Data create(String cellText) {
        Expression expression = null;
        Value value = null;
        if (cellText.startsWith("=")) {
            try {
                expression = expressionParser.parse(cellText);
                value = expressionEvaluator.evaluate(expression);
            } catch (ExpressionParsingException | ExpressionEvaluationException ignored) { }
        } else {
            value = Value.parse(cellText);
        }
        return new Data(cellText, value, expression);
    }
}
