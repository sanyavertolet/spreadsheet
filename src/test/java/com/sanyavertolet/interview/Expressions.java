package com.sanyavertolet.interview;

import com.sanyavertolet.interview.exceptions.CellReferenceException;
import com.sanyavertolet.interview.exceptions.FunctionArgumentException;
import com.sanyavertolet.interview.exceptions.expressions.RangeParsingException;
import com.sanyavertolet.interview.math.CellReference;
import com.sanyavertolet.interview.math.Function;
import com.sanyavertolet.interview.math.expressions.*;
import com.sanyavertolet.interview.math.operators.NonFunctionOperator;

import java.util.List;

public class Expressions {
    private Expressions() { }

    private static NonFunctionOperator operator(String operator) {
        return new NonFunctionOperator(operator);
    }

    public static class BinaryExpressions {
        private BinaryExpressions() { }

        private static Expression binary(Expression left, Expression right, NonFunctionOperator operator) {
            return new BinaryExpression(left, right, operator);
        }

        public static Expression plus(Expression left, Expression right) {
            return binary(left, right, operator("+"));
        }

        public static Expression minus(Expression left, Expression right) {
            return binary(left, right, operator("-"));
        }

        public static Expression mul(Expression left, Expression right) {
            return binary(left, right, operator("*"));
        }

        public static Expression div(Expression left, Expression right) {
            return binary(left, right, operator("/"));
        }

        public static Expression lt(Expression left, Expression right) { return binary(left, right, operator("<")); }

        public static Expression leq(Expression left, Expression right) { return binary(left, right, operator("<=")); }

        public static Expression gt(Expression left, Expression right) { return binary(left, right, operator(">")); }

        public static Expression geq(Expression left, Expression right) { return binary(left, right, operator(">=")); }

        public static Expression eq(Expression left, Expression right) { return binary(left, right, operator("==")); }

        public static Expression neq(Expression left, Expression right) { return binary(left, right, operator("!=")); }

        public static Expression powOp(Expression left, Expression right) {
            return binary(left, right, operator("^"));
        }
    }

    public static class Functions {

        private static Expression function(Function function) throws FunctionArgumentException {
            return new FunctionExpression(function, List.of());
        }

        public static Expression pi() throws FunctionArgumentException {
            return function(Function.PI);
        }

        public static Expression e() throws FunctionArgumentException {
            return function(Function.E);
        }

        public static Expression powF(Expression left, Expression right) throws FunctionArgumentException {
            return new FunctionExpression(Function.POW, List.of(left, right));
        }

        public static Expression contains(Expression string, Expression needle) throws FunctionArgumentException {
            return new FunctionExpression(Function.CONTAINS, List.of(string, needle));
        }

        public static Expression string(Expression expression) throws FunctionArgumentException {
            return new FunctionExpression(Function.STRING, List.of(expression));
        }

        public static Expression sum(Expression range) throws FunctionArgumentException {
            return new FunctionExpression(Function.SUM, List.of(range));
        }
    }

    public static class Values {
        private Values() { }

        public static Expression value(String string) {
            return new ValueExpression(string);
        }

        public static Expression one = value("1");

        public static Expression two = value("2");

        public static Expression three = value("3");

        public static Expression four = value("4");

        public static Expression five = value("5");

        public static Expression seven = value("7");

        public static Expression ten = value("10");

        public static Expression fortyTwo = value("42");

        public static Expression minus(Expression expression) {
            return BinaryExpressions.mul(value("-1"), expression);
        }
    }

    public static class Cells {
        private Cells() { }

        private static Expression cell(String identifier) throws CellReferenceException {
            return new CellReferenceExpression(CellReference.of(identifier));
        }

        public static Expression cell(CellReference reference) {
            return new CellReferenceExpression(reference);
        }

        public static Expression a1() throws CellReferenceException {
            return cell("A1");
        }

        public static Expression b1() throws CellReferenceException {
            return cell("B1");
        }

        public static Expression b2() throws CellReferenceException {
            return cell("B2");
        }

        public static Expression c2() throws CellReferenceException {
            return cell("C2");
        }
    }

    public static class Ranges {
        private Ranges() { }

        public static Expression range(String start, String end) throws CellReferenceException, RangeParsingException {
            return new RangeExpression(CellReference.of(start), CellReference.of(end));
        }
    }
}
