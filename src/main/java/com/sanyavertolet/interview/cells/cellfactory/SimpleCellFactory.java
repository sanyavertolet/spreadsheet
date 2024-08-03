package com.sanyavertolet.interview.cells.cellfactory;

import com.sanyavertolet.interview.cells.*;
import com.sanyavertolet.interview.parser.ExpressionParser;
import com.sanyavertolet.interview.parser.TreeExpressionParser;

public class SimpleCellFactory implements CellFactory {
    private final ExpressionParser expressionParser;

    public SimpleCellFactory() {
        expressionParser = new TreeExpressionParser();
    }

    public SimpleCellFactory(ExpressionParser expressionParser) {
        this.expressionParser = expressionParser;
    }

    @Override
    public Cell create(String cellText) {
        try {
            return new IntegerCell(cellText, Integer.valueOf(cellText));
        } catch (NumberFormatException ignored) { }

        try {
            return new DoubleCell(cellText, Double.valueOf(cellText));
        } catch (NumberFormatException ignored) { }

        if (cellText.trim().equalsIgnoreCase("true")) {
             return new BooleanCell(cellText, true);
        } else if (cellText.trim().equalsIgnoreCase("false")) {
            return new BooleanCell(cellText, false);
        }

        if (cellText.startsWith("=")) {
            return new ExpressionCell(cellText, expressionParser);
        }

        return new TextCell(cellText);
    }
}
