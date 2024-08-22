package com.sanyavertolet.interview.parser;

import com.sanyavertolet.interview.exceptions.CellReferenceException;
import com.sanyavertolet.interview.exceptions.expressions.ExpressionParsingException;
import com.sanyavertolet.interview.math.CellReference;
import com.sanyavertolet.interview.math.Function;
import com.sanyavertolet.interview.exceptions.FunctionArgumentException;
import com.sanyavertolet.interview.math.expressions.*;
import com.sanyavertolet.interview.math.operators.FunctionOperator;
import com.sanyavertolet.interview.math.operators.NonFunctionOperator;
import com.sanyavertolet.interview.math.operators.Operator;
import com.sanyavertolet.interview.parser.tokenizer.SimpleTokenizer;
import com.sanyavertolet.interview.parser.tokenizer.Token;
import com.sanyavertolet.interview.parser.tokenizer.Tokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ShuntingYardParser implements ExpressionParser {
    private final Logger logger = LoggerFactory.getLogger(ShuntingYardParser.class);
    private final Tokenizer tokenizer = new SimpleTokenizer();

    @Override
    public Expression parse(String expression) throws ExpressionParsingException {
        logger.debug("Parsing expression: {}", expression);
        List<Token> tokens = tokenizer.tokenize(expression);

        try {
            return parse(tokens);
        } catch (ExpressionParsingException exception) {
            logger.trace(exception.getMessage());
            throw exception;
        } catch (Exception exception) {
            logger.trace(exception.getMessage());
            throw new ExpressionParsingException(exception.getMessage(), exception);
        }
    }

    private boolean shouldPushOperatorHigher(Stack<Operator> operators, NonFunctionOperator currentOperator) {
        if (operators.isEmpty()) {
            return false;
        }
        Operator topStackOperator = operators.peek();
        return topStackOperator instanceof NonFunctionOperator operator
                && operator.type() != NonFunctionOperator.Type.OPEN_PARENTHESIS && operator.type() != NonFunctionOperator.Type.COMMA
                && (operator.precedence() > currentOperator.precedence() || (operator.precedence() == currentOperator.precedence() && currentOperator.associativity() == NonFunctionOperator.Associativity.LEFT));
    }

    private boolean isUnaryMinus(NonFunctionOperator operator, Token previousToken) {
        return operator.type() == NonFunctionOperator.Type.MINUS &&
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

    private Expression getBinaryExpression(Stack<Expression> expressions, Stack<Operator> operators) throws ExpressionParsingException {
        Expression right = expressions.pop();
        Operator operator = operators.pop();
        if (operator instanceof NonFunctionOperator nonFunctionOperator) {
            return new BinaryExpression(expressions.pop(), right, nonFunctionOperator);
        }
        throw new ExpressionParsingException("Found insufficient operator: " + operator.getSymbol());
    }

    private Expression getBinaryExpression(Stack<Expression> expressions, NonFunctionOperator operator) {
        Expression right = expressions.pop();
        return new BinaryExpression(expressions.pop(), right, operator);
    }

    private boolean isFunctionWithoutArguments(int functionTokenIndex, List<Token> tokens) {
        if (functionTokenIndex + 2 >= tokens.size()) {
            return false;
        }
        return tokens.get(functionTokenIndex + 2).type() == Token.Type.CLOSE_PARENTHESIS;
    }

    private boolean isRange(Stack<Expression> expressions, Stack<Operator> operators) {
        if (expressions.isEmpty() || operators.isEmpty()) {
            return false;
        }
        if (!(expressions.peek() instanceof CellReferenceExpression)) {
            return false;
        }

        return operators.peek() instanceof NonFunctionOperator nonFunctionOperator && nonFunctionOperator.type() == NonFunctionOperator.Type.COLON;
    }

    private Expression parse(List<Token> tokens) throws ExpressionParsingException, FunctionArgumentException, CellReferenceException {
        Stack<Expression> expressions = new Stack<>();
        Stack<Operator> operators = new Stack<>();

        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            switch (token.type()) {
                case NUMBER -> expressions.add(ValueExpression.parse(token.value()));
                case REFERENCE -> {
                    if (isFunction(getNextToken(i, tokens))) {
                        if (isFunctionWithoutArguments(i, tokens)) {
                            i += 2;
                            expressions.add(new FunctionExpression(Function.named(token.value()), List.of()));
                        } else {
                            operators.push(new FunctionOperator(token.value()));
                        }
                    } else {
                        CellReference cellReference = CellReference.of(token.value());
                        if (isRange(expressions, operators)) {
                            CellReferenceExpression fromExpression = (CellReferenceExpression) expressions.pop();
                            operators.pop();
                            expressions.add(new RangeExpression(fromExpression.getCellReference(), cellReference));
                        } else {
                            expressions.add(new CellReferenceExpression(cellReference));
                        }
                    }
                }
                case COLON -> operators.push(new NonFunctionOperator(":"));
                case OPERATOR -> {
                    NonFunctionOperator currentOperator = new NonFunctionOperator(token.value());
                    if (isUnaryMinus(currentOperator, getPreviousToken(i, tokens))) {
                        expressions.add(ValueExpression.parse("-1"));
                        operators.push(new NonFunctionOperator("*"));
                    } else if (operators.isEmpty()) {
                        operators.push(currentOperator);
                    } else {
                        while (shouldPushOperatorHigher(operators, currentOperator)) {
                            expressions.add(getBinaryExpression(expressions, operators));
                        }
                        operators.push(new NonFunctionOperator(token.value()));
                    }
                }
                case COMMA -> operators.add(new NonFunctionOperator(","));
                case OPEN_PARENTHESIS -> operators.add(new NonFunctionOperator("("));
                case CLOSE_PARENTHESIS -> {
                    List<Expression> arguments = new ArrayList<>();
                    while (operators.peek() instanceof NonFunctionOperator operator && operator.type() != NonFunctionOperator.Type.OPEN_PARENTHESIS) {
                        if (operators.isEmpty()) {
                            throw new ExpressionParsingException("Closing parenthesis not found.");
                        }
                        operators.pop();
                        if (operator.type() == NonFunctionOperator.Type.COMMA) {
                            arguments.add(0, expressions.pop());
                        } else {
                            expressions.add(getBinaryExpression(expressions, operator));
                        }
                    }
                    if (operators.isEmpty() || operators.peek() instanceof NonFunctionOperator operator && operator.type() != NonFunctionOperator.Type.OPEN_PARENTHESIS) {
                        throw new ExpressionParsingException("Failed parenthesis.");
                    }

                    operators.pop();
                    if (!operators.isEmpty() && operators.peek() instanceof FunctionOperator functionOperator) {
                        operators.pop();
                        arguments.add(0, expressions.pop());
                        expressions.add(new FunctionExpression(functionOperator.getFunction(), arguments));
                        arguments = new ArrayList<>();
                    }
                    if (!arguments.isEmpty()) {
                        throw new ExpressionParsingException("Failed while parsing function.");
                    }
                }
                case STRING -> expressions.push(new ValueExpression(token.value()));
                default -> throw new ExpressionParsingException("Unexpected token type: " + token);
            }
        }

        while (!operators.isEmpty()) {
            if (operators.peek() instanceof NonFunctionOperator operator && operator.type() == NonFunctionOperator.Type.OPEN_PARENTHESIS) {
                throw new ExpressionParsingException("Closing parenthesis not found.");
            }

            expressions.add(getBinaryExpression(expressions, operators));
        }

        if (expressions.size() > 1) {
            throw new ExpressionParsingException("Could not build expression tree: extra expressions found.");
        }

        return expressions.peek();
    }
}
