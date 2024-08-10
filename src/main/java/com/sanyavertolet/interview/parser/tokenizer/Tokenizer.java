package com.sanyavertolet.interview.parser.tokenizer;

import com.sanyavertolet.interview.exceptions.ExpressionParsingException;

import java.util.List;

public interface Tokenizer {
    List<Token> tokenize(String input) throws ExpressionParsingException;
}
