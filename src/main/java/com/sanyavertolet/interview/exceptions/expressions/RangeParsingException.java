package com.sanyavertolet.interview.exceptions.expressions;

import com.sanyavertolet.interview.math.CellReference;

public class RangeParsingException extends ExpressionParsingException {
    @SuppressWarnings("unused")
    public RangeParsingException(CellReference from, CellReference to) {
        super("Could not construct RangeExpression " + from + ":" + to);
    }

    @SuppressWarnings("unused")
    public RangeParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
