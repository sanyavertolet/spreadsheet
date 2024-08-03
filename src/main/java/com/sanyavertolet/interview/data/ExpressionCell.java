package com.sanyavertolet.interview.data;

import com.sanyavertolet.interview.parser.ExpressionParser;
import com.sanyavertolet.interview.parser.TreeNode;

public class ExpressionCell extends Cell {
    private Double value;
    private TreeNode parsedExpression;
    private final ExpressionParser parser;

    public ExpressionCell(String text, ExpressionParser parser) {
        super(text);
        this.parser = parser;
        // todo: parse tree
    }

    public Double getValue() {
        return value;
    }

    @Override
    public String getValueAsString() {
        return parsedExpression == null ? "ERR" : value.toString();
    }

    public String getPrettyPrintedExpressionTree() {
        // todo: implement tree pretty-print
        return "TBD";
    }
}
