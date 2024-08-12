package com.sanyavertolet.interview.data.factory;

import com.sanyavertolet.interview.data.*;
import com.sanyavertolet.interview.data.accessor.DataAccessor;
import com.sanyavertolet.interview.exceptions.ExpressionParsingException;
import com.sanyavertolet.interview.math.expressions.evaluator.ExpressionEvaluator;
import com.sanyavertolet.interview.math.expressions.evaluator.SimpleExpressionEvaluator;
import com.sanyavertolet.interview.parser.ExpressionParser;
import com.sanyavertolet.interview.parser.ShuntingYardParser;

public class SimpleDataFactory implements DataFactory {
    private final ExpressionParser expressionParser;
    private final ExpressionEvaluator expressionEvaluator;

    public SimpleDataFactory(DataAccessor dataAccessor) {
        this.expressionParser = new ShuntingYardParser();
        this.expressionEvaluator = new SimpleExpressionEvaluator(dataAccessor);
    }

    @Override
    public Data create(String cellText) {
        try {
            return new DoubleData(cellText, Double.valueOf(cellText));
        } catch (NumberFormatException ignored) { }

        if (cellText.trim().equalsIgnoreCase("true")) {
             return new BooleanData(cellText, true);
        } else if (cellText.trim().equalsIgnoreCase("false")) {
            return new BooleanData(cellText, false);
        }

        if (cellText.startsWith("=")) {
            try {
                return new ExpressionData(cellText, expressionParser.parse(cellText), expressionEvaluator::evaluate);
            } catch (ExpressionParsingException exception) {
                return new TextData(cellText);
            }
        }

        return new TextData(cellText);
    }
}
