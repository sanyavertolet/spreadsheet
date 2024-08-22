package com.sanyavertolet.interview.parser.tokenizer;

import com.sanyavertolet.interview.exceptions.expressions.ExpressionParsingException;

import java.util.List;

/**
 * An interface for tokenizing strings into a list of {@link Token} objects.
 * The {@code Tokenizer} interface defines the method for breaking down an input string
 * into meaningful tokens that can be used for parsing expressions.
 */
public interface Tokenizer {

    /**
     * Tokenizes the given input string into a list of {@link Token} objects.
     * The method processes the input string and identifies tokens based on predefined rules,
     * such as recognizing operators, numbers, references, and other symbols.
     *
     * @param input the input string to be tokenized.
     * @return a list of {@link Token} objects representing the parsed tokens from the input string.
     * @throws ExpressionParsingException if an error occurs during tokenization, such as encountering an invalid token.
     */
    List<Token> tokenize(String input) throws ExpressionParsingException;
}
