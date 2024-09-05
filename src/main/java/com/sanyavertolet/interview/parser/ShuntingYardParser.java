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

/**
 * An implementation of the {@link ExpressionParser} interface that uses the Shunting Yard algorithm
 * to parse mathematical expressions into an {@link Expression} object. The {@code ShuntingYardParser}
 * handles various types of tokens, including operators, functions, and references, to construct a valid
 * expression tree.
 */
public class ShuntingYardParser implements ExpressionParser {
    private final static Logger logger = LoggerFactory.getLogger(ShuntingYardParser.class);
    private final Tokenizer tokenizer = new SimpleTokenizer();

    /**
     * Parses the given expression string into an {@link Expression} object using the Shunting Yard algorithm.
     *
     * @param expression the string representation of the expression to be parsed.
     * @return an {@link Expression} object representing the parsed expression.
     * @throws ExpressionParsingException if an error occurs during parsing, such as invalid syntax or unrecognized tokens.
     */
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

    /**
     * Determines whether the current operator should be pushed onto the operator stack
     * or whether higher-precedence operators should be processed first.
     *
     * @param operators       the stack of operators.
     * @param currentOperator the current operator being processed.
     * @return {@code true} if higher-precedence operators should be processed first; {@code false} otherwise.
     */
    private boolean shouldPushOperatorHigher(Stack<Operator> operators, NonFunctionOperator currentOperator) {
        if (operators.isEmpty()) {
            return false;
        }
        Operator topStackOperator = operators.peek();
        return topStackOperator instanceof NonFunctionOperator operator
                && operator.getType() != NonFunctionOperator.Type.OPEN_PARENTHESIS && operator.getType() != NonFunctionOperator.Type.COMMA
                && (operator.getPrecedence() > currentOperator.getPrecedence() || (operator.getPrecedence() == currentOperator.getPrecedence() && currentOperator.getAssociativity() == NonFunctionOperator.Associativity.LEFT));
    }

    /**
     * Determines whether the current token represents a unary minus operator.
     *
     * @param operator      the current operator.
     * @param previousToken the previous token in the expression.
     * @return {@code true} if the operator is a unary minus; {@code false} otherwise.
     */
    private boolean isUnaryMinus(NonFunctionOperator operator, Token previousToken) {
        return operator.getType() == NonFunctionOperator.Type.MINUS &&
                (previousToken == null || previousToken.type() == Token.Type.OPEN_PARENTHESIS || previousToken.type() == Token.Type.OPERATOR);
    }

    /**
     * Determines whether the next token represents the start of a function.
     *
     * @param nextToken the next token in the expression.
     * @return {@code true} if the next token starts a function; {@code false} otherwise.
     */
    private boolean isFunction(Token nextToken) {
        return nextToken != null && nextToken.type() == Token.Type.OPEN_PARENTHESIS;
    }

    /**
     * Retrieves the previous token in the token list.
     *
     * @param index  the current index in the token list.
     * @param tokens the list of tokens.
     * @return the previous token, or {@code null} if there is no previous token.
     */
    private Token getPreviousToken(int index, List<Token> tokens) {
        return index == 0 ? null : tokens.get(index - 1);
    }

    /**
     * Retrieves the next token in the token list.
     *
     * @param index  the current index in the token list.
     * @param tokens the list of tokens.
     * @return the next token, or {@code null} if there is no next token.
     */
    private Token getNextToken(int index, List<Token> tokens) {
        return index + 1 < tokens.size() ? tokens.get(index + 1) : null;
    }

    /**
     * Creates a binary expression from the top two expressions and the top operator on their respective stacks.
     *
     * @param expressions the stack of expressions.
     * @param operators   the stack of operators.
     * @return the resulting {@link Expression} object.
     * @throws ExpressionParsingException if the operator is invalid or the expression is incomplete.
     */
    private Expression getBinaryExpression(Stack<Expression> expressions, Stack<Operator> operators) throws ExpressionParsingException {
        Operator operator = operators.pop();
        if (operator instanceof NonFunctionOperator nonFunctionOperator) {
            Expression right = expressions.pop();
            return new BinaryExpression(expressions.pop(), right, nonFunctionOperator);
        }
        throw new ExpressionParsingException("Found insufficient operator: " + operator.getSymbol());
    }

    /**
     * Creates a binary expression from the top two expressions on the stack and the provided operator.
     *
     * @param expressions the stack of expressions.
     * @param operator    the operator to apply to the expressions.
     * @return the resulting {@link Expression} object.
     */
    private Expression getBinaryExpression(Stack<Expression> expressions, NonFunctionOperator operator) {
        Expression right = expressions.pop();
        return new BinaryExpression(expressions.pop(), right, operator);
    }

    /**
     * Determines whether the function has no arguments based on the token list.
     *
     * @param functionTokenIndex the index of the function token.
     * @param tokens             the list of tokens.
     * @return {@code true} if the function has no arguments; {@code false} otherwise.
     */
    private boolean isFunctionWithoutArguments(int functionTokenIndex, List<Token> tokens) {
        if (functionTokenIndex + 2 >= tokens.size()) {
            return false;
        }
        return tokens.get(functionTokenIndex + 2).type() == Token.Type.CLOSE_PARENTHESIS;
    }

    /**
     * Determines whether the top expression and operator form a range expression.
     *
     * @param expressions the stack of expressions.
     * @param operators   the stack of operators.
     * @return {@code true} if the top expression and operator form a range; {@code false} otherwise.
     */
    private boolean isRange(Stack<Expression> expressions, Stack<Operator> operators) {
        if (expressions.isEmpty() || operators.isEmpty()) {
            return false;
        }
        if (!(expressions.peek() instanceof CellReferenceExpression)) {
            return false;
        }

        return operators.peek() instanceof NonFunctionOperator nonFunctionOperator && nonFunctionOperator.getType() == NonFunctionOperator.Type.COLON;
    }

    /**
     * Parses a list of tokens into an {@link Expression} object using the Shunting Yard algorithm.
     *
     * @param tokens the list of tokens to parse.
     * @return the resulting {@link Expression} object.
     * @throws ExpressionParsingException if an error occurs during parsing.
     * @throws FunctionArgumentException  if there is an error with function arguments.
     * @throws CellReferenceException     if there is an error with cell references.
     */
    @SuppressWarnings({"PMD.AvoidLiteralsInIfCondition", "PMD.DataflowAnomalyAnalysis"})
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
                    while (operators.peek() instanceof NonFunctionOperator operator && operator.getType() != NonFunctionOperator.Type.OPEN_PARENTHESIS) {
                        if (operators.isEmpty()) {
                            throw new ExpressionParsingException("Closing parenthesis not found.");
                        }
                        operators.pop();
                        if (operator.getType() == NonFunctionOperator.Type.COMMA) {
                            arguments.add(0, expressions.pop());
                        } else {
                            expressions.add(getBinaryExpression(expressions, operator));
                        }
                    }
                    if (operators.isEmpty() || operators.peek() instanceof NonFunctionOperator operator && operator.getType() != NonFunctionOperator.Type.OPEN_PARENTHESIS) {
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
            if (operators.peek() instanceof NonFunctionOperator operator && operator.getType() == NonFunctionOperator.Type.OPEN_PARENTHESIS) {
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
