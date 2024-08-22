package com.sanyavertolet.interview;

import com.sanyavertolet.interview.exceptions.CellReferenceException;
import com.sanyavertolet.interview.exceptions.FunctionArgumentException;
import com.sanyavertolet.interview.exceptions.expressions.RangeParsingException;
import com.sanyavertolet.interview.math.CellReference;
import com.sanyavertolet.interview.math.Function;
import com.sanyavertolet.interview.math.expressions.*;
import com.sanyavertolet.interview.math.operators.NonFunctionOperator;

import java.util.List;

/**
 * A utility class that provides factory methods for creating various types of {@code Expression} objects.
 * <p>
 * This class contains nested static classes for creating binary expressions, function expressions,
 * value expressions, cell reference expressions, and range expressions.
 * <p>
 * This class is not meant to be instantiated.
 */
public class Expressions {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Expressions() { }

    private static NonFunctionOperator operator(String operator) {
        return new NonFunctionOperator(operator);
    }

    /**
     * Provides factory methods for creating binary expressions.
     * <p>
     * This class is not meant to be instantiated.
     */
    public static class BinaryExpressions {
        private BinaryExpressions() { }

        private static Expression binary(Expression left, Expression right, NonFunctionOperator operator) {
            return new BinaryExpression(left, right, operator);
        }

        /**
         * Creates a binary expression representing addition.
         *
         * @param left  the left operand.
         * @param right the right operand.
         * @return a binary expression representing addition.
         */
        public static Expression plus(Expression left, Expression right) {
            return binary(left, right, operator("+"));
        }

        /**
         * Creates a binary expression representing subtraction.
         *
         * @param left  the left operand.
         * @param right the right operand.
         * @return a binary expression representing subtraction.
         */
        public static Expression minus(Expression left, Expression right) {
            return binary(left, right, operator("-"));
        }

        /**
         * Creates a binary expression representing multiplication.
         *
         * @param left  the left operand.
         * @param right the right operand.
         * @return a binary expression representing multiplication.
         */
        public static Expression mul(Expression left, Expression right) {
            return binary(left, right, operator("*"));
        }

        /**
         * Creates a binary expression representing division.
         *
         * @param left  the left operand.
         * @param right the right operand.
         * @return a binary expression representing division.
         */
        public static Expression div(Expression left, Expression right) {
            return binary(left, right, operator("/"));
        }

        /**
         * Creates a binary expression representing less than comparison.
         *
         * @param left  the left operand.
         * @param right the right operand.
         * @return a binary expression representing less than comparison.
         */
        public static Expression lt(Expression left, Expression right) {
            return binary(left, right, operator("<"));
        }

        /**
         * Creates a binary expression representing less than or equal to comparison.
         *
         * @param left  the left operand.
         * @param right the right operand.
         * @return a binary expression representing less than or equal to comparison.
         */
        public static Expression leq(Expression left, Expression right) {
            return binary(left, right, operator("<="));
        }

        /**
         * Creates a binary expression representing greater than comparison.
         *
         * @param left  the left operand.
         * @param right the right operand.
         * @return a binary expression representing greater than comparison.
         */
        public static Expression gt(Expression left, Expression right) {
            return binary(left, right, operator(">"));
        }

        /**
         * Creates a binary expression representing greater than or equal to comparison.
         *
         * @param left  the left operand.
         * @param right the right operand.
         * @return a binary expression representing greater than or equal to comparison.
         */
        public static Expression geq(Expression left, Expression right) {
            return binary(left, right, operator(">="));
        }

        /**
         * Creates a binary expression representing equality comparison.
         *
         * @param left  the left operand.
         * @param right the right operand.
         * @return a binary expression representing equality comparison.
         */
        public static Expression eq(Expression left, Expression right) {
            return binary(left, right, operator("=="));
        }

        /**
         * Creates a binary expression representing inequality comparison.
         *
         * @param left  the left operand.
         * @param right the right operand.
         * @return a binary expression representing inequality comparison.
         */
        public static Expression neq(Expression left, Expression right) {
            return binary(left, right, operator("!="));
        }

        /**
         * Creates a binary expression representing exponentiation.
         *
         * @param left  the base operand.
         * @param right the exponent operand.
         * @return a binary expression representing exponentiation.
         */
        public static Expression powOp(Expression left, Expression right) {
            return binary(left, right, operator("^"));
        }
    }

    /**
     * Provides factory methods for creating function expressions.
     * <p>
     * This class is not meant to be instantiated.
     */
    public static class Functions {

        private static Expression function(Function function) throws FunctionArgumentException {
            return new FunctionExpression(function, List.of());
        }

        /**
         * Creates a function expression representing the mathematical constant π.
         *
         * @return a function expression representing π.
         * @throws FunctionArgumentException if there is an issue creating the function expression.
         */
        public static Expression pi() throws FunctionArgumentException {
            return function(Function.PI);
        }

        /**
         * Creates a function expression representing the mathematical constant e.
         *
         * @return a function expression representing e.
         * @throws FunctionArgumentException if there is an issue creating the function expression.
         */
        public static Expression e() throws FunctionArgumentException {
            return function(Function.E);
        }

        /**
         * Creates a function expression representing exponentiation.
         *
         * @param left  the base operand.
         * @param right the exponent operand.
         * @return a function expression representing exponentiation.
         * @throws FunctionArgumentException if there is an issue creating the function expression.
         */
        public static Expression powF(Expression left, Expression right) throws FunctionArgumentException {
            return new FunctionExpression(Function.POW, List.of(left, right));
        }

        /**
         * Creates a function expression representing the contains function.
         *
         * @param string the string to search within.
         * @param needle the substring to search for.
         * @return a function expression representing the contains function.
         * @throws FunctionArgumentException if there is an issue creating the function expression.
         */
        public static Expression contains(Expression string, Expression needle) throws FunctionArgumentException {
            return new FunctionExpression(Function.CONTAINS, List.of(string, needle));
        }

        /**
         * Creates a function expression representing string conversion.
         *
         * @param expression the expression to convert to a string.
         * @return a function expression representing string conversion.
         * @throws FunctionArgumentException if there is an issue creating the function expression.
         */
        public static Expression string(Expression expression) throws FunctionArgumentException {
            return new FunctionExpression(Function.STRING, List.of(expression));
        }

        /**
         * Creates a function expression representing summing a range.
         *
         * @param range the range to sum.
         * @return a function expression representing the sum of the range.
         * @throws FunctionArgumentException if there is an issue creating the function expression.
         */
        public static Expression sum(Expression range) throws FunctionArgumentException {
            return new FunctionExpression(Function.SUM, List.of(range));
        }

        /**
         * Creates a function expression representing concatenation of two expressions.
         *
         * @param first  the first expression.
         * @param second the second expression.
         * @return a function expression representing concatenation.
         * @throws FunctionArgumentException if there is an issue creating the function expression.
         */
        public static Expression concat(Expression first, Expression second) throws FunctionArgumentException {
            return new FunctionExpression(Function.CONCAT, List.of(first, second));
        }

        /**
         * Creates a function expression representing an if-else condition.
         *
         * @param condition      the condition expression.
         * @param valueIfTrue   the value if the condition is true.
         * @param valueIfFalse  the value if the condition is false.
         * @return a function expression representing an if-else condition.
         * @throws FunctionArgumentException if there is an issue creating the function expression.
         */
        public static Expression iff(Expression condition, Expression valueIfTrue, Expression valueIfFalse) throws FunctionArgumentException {
            return new FunctionExpression(Function.IF, List.of(condition, valueIfTrue, valueIfFalse));
        }

        /**
         * Creates a function expression representing an if-error condition.
         *
         * @param expression      the expression to evaluate.
         * @param valueIfError    the value if the expression results in an error.
         * @return a function expression representing an if-error condition.
         * @throws FunctionArgumentException if there is an issue creating the function expression.
         */
        public static Expression ifErr(Expression expression, Expression valueIfError) throws FunctionArgumentException {
            return new FunctionExpression(Function.IFERROR, List.of(expression, valueIfError));
        }
    }

    /**
     * Provides factory methods for creating value expressions.
     * <p>
     * This class is not meant to be instantiated.
     */
    public static class Values {
        private Values() { }

        /**
         * Creates a value expression from a string.
         *
         * @param string the string value.
         * @return a value expression representing the string.
         */
        public static Expression value(String string) {
            return ValueExpression.parse(string);
        }

        /**
         * Creates a raw value expression from a string.
         *
         * @param string the string value.
         * @return a raw value expression representing the string.
         */
        public static Expression rawValue(String string) {
            return new ValueExpression(string);
        }

        /** A constant value expression representing 1. */
        public static Expression one = value("1");

        /** A constant value expression representing 2. */
        public static Expression two = value("2");

        /** A constant value expression representing 3. */
        public static Expression three = value("3");

        /** A constant value expression representing 4. */
        public static Expression four = value("4");

        /** A constant value expression representing 5. */
        public static Expression five = value("5");

        /** A constant value expression representing 7. */
        public static Expression seven = value("7");

        /** A constant value expression representing 10. */
        public static Expression ten = value("10");

        /** A constant value expression representing 42. */
        public static Expression fortyTwo = value("42");

        /** A constant value expression representing true. */
        public static Expression trueExpr = value("true");

        /** A constant value expression representing false. */
        public static Expression falseExpr = value("false");

        /**
         * Creates a unary minus expression.
         *
         * @param expression the expression to negate.
         * @return a binary expression representing the negation of the given expression.
         */
        public static Expression minus(Expression expression) {
            return BinaryExpressions.mul(value("-1"), expression);
        }
    }

    /**
     * Provides factory methods for creating cell reference expressions.
     * <p>
     * This class is not meant to be instantiated.
     */
    public static class Cells {
        private Cells() { }

        private static Expression cell(String identifier) throws CellReferenceException {
            return new CellReferenceExpression(CellReference.of(identifier));
        }

        /**
         * Creates a cell reference expression from a {@code CellReference}.
         *
         * @param reference the cell reference.
         * @return a cell reference expression representing the given reference.
         */
        public static Expression cell(CellReference reference) {
            return new CellReferenceExpression(reference);
        }

        /**
         * Creates a cell reference expression for cell A1.
         *
         * @return a cell reference expression for A1.
         * @throws CellReferenceException if there is an issue creating the cell reference expression.
         */
        public static Expression a1() throws CellReferenceException {
            return cell("A1");
        }

        /**
         * Creates a cell reference expression for cell B1.
         *
         * @return a cell reference expression for B1.
         * @throws CellReferenceException if there is an issue creating the cell reference expression.
         */
        public static Expression b1() throws CellReferenceException {
            return cell("B1");
        }

        /**
         * Creates a cell reference expression for cell B2.
         *
         * @return a cell reference expression for B2.
         * @throws CellReferenceException if there is an issue creating the cell reference expression.
         */
        public static Expression b2() throws CellReferenceException {
            return cell("B2");
        }

        /**
         * Creates a cell reference expression for cell C2.
         *
         * @return a cell reference expression for C2.
         * @throws CellReferenceException if there is an issue creating the cell reference expression.
         */
        public static Expression c2() throws CellReferenceException {
            return cell("C2");
        }
    }

    /**
     * Provides factory methods for creating range expressions.
     * <p>
     * This class is not meant to be instantiated.
     */
    public static class Ranges {
        private Ranges() { }

        /**
         * Creates a range expression representing a range from a start cell to an end cell.
         *
         * @param start the starting cell reference.
         * @param end   the ending cell reference.
         * @return a range expression representing the range from start to end.
         * @throws CellReferenceException if there is an issue creating the cell references.
         * @throws RangeParsingException  if there is an issue creating the range expression.
         */
        public static Expression range(String start, String end) throws CellReferenceException, RangeParsingException {
            return new RangeExpression(CellReference.of(start), CellReference.of(end));
        }
    }
}
