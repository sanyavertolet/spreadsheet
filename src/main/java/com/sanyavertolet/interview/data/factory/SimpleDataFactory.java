package com.sanyavertolet.interview.data.factory;

import com.sanyavertolet.interview.data.*;
import com.sanyavertolet.interview.data.value.Value;
import com.sanyavertolet.interview.exceptions.expressions.ExpressionEvaluationException;
import com.sanyavertolet.interview.exceptions.expressions.ExpressionParsingException;
import com.sanyavertolet.interview.math.expressions.Expression;
import com.sanyavertolet.interview.math.expressions.evaluator.ExpressionEvaluator;
import com.sanyavertolet.interview.parser.ExpressionParser;
import com.sanyavertolet.interview.parser.ShuntingYardParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleDataFactory implements DataFactory {
    private final Logger logger = LoggerFactory.getLogger(SimpleDataFactory.class);
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
            } catch (ExpressionParsingException | ExpressionEvaluationException exception) {
                logger.error(exception.getMessage());
            }
        } else {
            value = Value.parse(cellText);
        }
        logger.debug("Created cell from text {} with value {}", cellText, value);
        return new Data(cellText, value, expression);
    }
}
