package com.sanyavertolet.interview.parser;

import com.sanyavertolet.interview.exceptions.ParsingException;

public interface Parser {
    TreeNode evaluate(String expression) throws ParsingException;
}
