package com.sanyavertolet.interview.parser;

import com.sanyavertolet.interview.exceptions.ParsingException;

public interface ExpressionParser {
    TreeNode parse(String expression) throws ParsingException;
}
