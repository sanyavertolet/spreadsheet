package com.sanyavertolet.interview.parser;

import com.sanyavertolet.interview.exceptions.expressions.ExpressionParsingException;
import com.sanyavertolet.interview.math.expressions.Expression;

public interface ExpressionParser {
    Expression parse(String expression) throws ExpressionParsingException;
}
