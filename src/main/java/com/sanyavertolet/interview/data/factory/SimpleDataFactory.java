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

/**
 * A simple implementation of the {@link DataFactory} interface that creates {@link Data} instances
 * based on the provided cell text. This class can parse and evaluate expressions starting with "="
 * or directly create value-based data for non-expression text.
 */
public class SimpleDataFactory implements DataFactory {
    private final static Logger logger = LoggerFactory.getLogger(SimpleDataFactory.class);
    private final ExpressionParser expressionParser;
    private final ExpressionEvaluator expressionEvaluator;

    /**
     * Constructs a {@code SimpleDataFactory} with the specified {@link ExpressionEvaluator}.
     *
     * @param expressionEvaluator the evaluator used to evaluate parsed expressions.
     */
    public SimpleDataFactory(ExpressionEvaluator expressionEvaluator) {
        this.expressionParser = new ShuntingYardParser();
        this.expressionEvaluator = expressionEvaluator;
    }

    /**
     * Creates a {@link Data} instance based on the provided cell text. If the text starts with "=",
     * it is treated as an expression, which is parsed and evaluated. Otherwise, the text is treated
     * as a simple value.
     *
     * @param cellText the text representation of the data to be created.
     * @return a {@link Data} instance corresponding to the provided cell text.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
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
