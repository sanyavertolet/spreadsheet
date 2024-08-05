package com.sanyavertolet.interview.parser;

import com.sanyavertolet.interview.Function;
import com.sanyavertolet.interview.exceptions.FunctionArgumentException;
import com.sanyavertolet.interview.exceptions.ParsingException;
import com.sanyavertolet.interview.expressions.*;
import com.sanyavertolet.interview.parser.tokenizer.SimpleTokenizer;
import com.sanyavertolet.interview.parser.tokenizer.Token;
import com.sanyavertolet.interview.parser.tokenizer.Tokenizer;

import java.util.*;

public class ShuntingYardParser implements ExpressionParser {
    private final Tokenizer tokenizer = new SimpleTokenizer();

    @Override
    public Expression parse(String expression) throws ParsingException {
        List<Token> tokens = tokenizer.tokenize(expression);

        try {
            return parse(tokens);
        } catch (ParsingException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new ParsingException(exception.getMessage());
        }
    }

    private boolean shouldPushOperatorHigher(Stack<Operator> operators, Operator currentOperator) {
        Operator topStackOperator = operators.isEmpty() ? null : operators.peek();
        return topStackOperator != null && topStackOperator != Operator.OPEN_PARENTHESIS &&
                (topStackOperator.precedence() > currentOperator.precedence() ||
                (topStackOperator.precedence() == currentOperator.precedence() && currentOperator.associativity() == Operator.Associativity.LEFT));
    }

    private boolean isUnaryMinus(Operator operator, Token previousToken) {
        return operator == Operator.MINUS &&
                (previousToken == null || previousToken.type() == Token.Type.OPEN_PARENTHESIS || previousToken.type() == Token.Type.OPERATOR);
    }

    private boolean isFunction(Token nextToken) {
        return nextToken != null && nextToken.type() == Token.Type.OPEN_PARENTHESIS;
    }

    private Token getPreviousToken(int index, List<Token> tokens) {
        return index == 0 ? null : tokens.get(index - 1);
    }

    private Token getNextToken(int index, List<Token> tokens) {
        return index + 1 < tokens.size() ? tokens.get(index + 1) : null;
    }

    private Expression getBinaryExpression(Stack<Expression> expressions, Stack<Operator> operators) {
        Expression right = expressions.pop();
        return new BinaryExpression(expressions.pop(), right, operators.pop());
    }

    private Expression getBinaryExpression(Stack<Expression> expressions, Operator operator) {
        Expression right = expressions.pop();
        return new BinaryExpression(expressions.pop(), right, operator);
    }

    private Expression parse(List<Token> tokens) throws ParsingException, FunctionArgumentException {
        Stack<Expression> expressions = new Stack<>();
        Stack<Operator> operators = new Stack<>();
        List<Expression> arguments = new ArrayList<>();

        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            switch (token.type()) {
                case NUMBER -> expressions.add(new NumberExpression(token.value()));
                case REFERENCE -> {
                    if (isFunction(getNextToken(i, tokens))) {
                        expressions.add(new FunctionExpression(Function.named(token.value())));
                    } else {
                        expressions.add(new CellReferenceExpression(token.value()));
                    }
                }
                case OPERATOR -> {
                    Operator currentOperator = Operator.of(token.value());

                    if (isUnaryMinus(currentOperator, getPreviousToken(i, tokens))) {
                        expressions.add(new NumberExpression("-1"));
                        operators.push(Operator.MULTIPLY);
                    } else if (operators.isEmpty()) {
                        operators.push(currentOperator);
                    } else {
                        while (shouldPushOperatorHigher(operators, currentOperator)) {
                            expressions.add(getBinaryExpression(expressions, operators));
                        }
                        operators.push(Operator.of(token.value()));
                    }
                }
                case COMMA -> operators.add(Operator.COMMA);
                case OPEN_PARENTHESIS -> operators.add(Operator.OPEN_PARENTHESIS);
                case CLOSE_PARENTHESIS -> {
                    while (operators.peek() != Operator.OPEN_PARENTHESIS) {
                        if (operators.isEmpty()) {
                            throw new ParsingException("Closing parenthesis not found.");
                        }
                        Operator operator = operators.pop();
                        if (operator == Operator.COMMA) {
                            if (arguments.isEmpty()) {
                                arguments.add(0, expressions.pop());
                            }
                            arguments.add(0, expressions.pop());
                        } else {
                            expressions.add(getBinaryExpression(expressions, operator));
                        }
                    }
                    if (operators.isEmpty() || operators.peek() != Operator.OPEN_PARENTHESIS) {
                        throw new ParsingException("Failed parenthesis.");
                    }
                    operators.pop();
                    if (expressions.peek() instanceof FunctionExpression expression) {
                        expression.setArguments(arguments);
                        arguments = new ArrayList<>();
                    }
                    if (!arguments.isEmpty()) {
                        throw new ParsingException("Failed while parsing function.");
                    }
                }
                default -> throw new ParsingException("Unexpected token type: " + token);
            }
        }

        while (!operators.isEmpty()) {
            if (operators.peek() == Operator.OPEN_PARENTHESIS) {
                throw new ParsingException("Closing parenthesis not found.");
            }

            expressions.add(getBinaryExpression(expressions, operators));
        }

        if (expressions.size() > 1) {
            throw new ParsingException("Could not build expression tree: extra expressions found.");
        }

        return expressions.peek();
    }
}
