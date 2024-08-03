package com.sanyavertolet.interview.data;

import com.sanyavertolet.interview.parser.TreeNode;

public class ExpressionCell extends Cell {
    private Double value;
    private TreeNode parsedExpression;

    public ExpressionCell(String text) {
        super(text);
        // todo: parse tree
    }

    public Double getValue() {
        return value;
    }

    @Override
    public String getValueAsString() {
        return value.toString();
    }

    public String getPrettyPrintedExpressionTree() {
        // todo: implement tree pretty-print
        return "TBD";
    }
}
