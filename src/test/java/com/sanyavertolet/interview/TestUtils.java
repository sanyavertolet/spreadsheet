package com.sanyavertolet.interview;

import com.sanyavertolet.interview.math.CellReference;
import com.sanyavertolet.interview.math.expressions.*;
import org.junit.jupiter.api.Assertions;

import java.util.List;

import static org.junit.jupiter.api.AssertionFailureBuilder.assertionFailure;

public class TestUtils {
    private TestUtils() { }

    public static void assertHappensBefore(CellReference before, CellReference after, List<CellReference> references) {
        int indexOfBefore = references.indexOf(before);
        int indexOfAfter = references.indexOf(after);

        if (indexOfBefore == -1) {
            assertionFailure().message(before.identifier() + " is not present in list.").buildAndThrow();
        } else if (indexOfAfter == -1) {
            assertionFailure().message(after.identifier() + " is not present in list.").buildAndThrow();
        } else if (indexOfBefore > indexOfAfter) {
            assertionFailure()
                    .message(before.identifier() + " should go before " + after.identifier())
                    .expected("references.indexOf(" + before.identifier() + ") = " + indexOfBefore + " <= " + "references.indexOf(" + after.identifier() + ") = " + indexOfAfter)
                    .actual("references.indexOf(" + before.identifier() + ") = " + indexOfBefore + " > " + "references.indexOf(" + after.identifier() + ") = " + indexOfAfter)
                    .buildAndThrow();
        }
    }

    private static void assertExpressionsEqual(NumberExpression expected, NumberExpression actual) {
        Assertions.assertEquals(expected.getNumber(), actual.getNumber());
    }

    private static void assertExpressionsEqual(CellReferenceExpression expected, CellReferenceExpression actual) {
        Assertions.assertEquals(expected.getCellReference().identifier(), actual.getCellReference().identifier());
        Assertions.assertEquals(expected.getCellReference().row(), actual.getCellReference().row());
        Assertions.assertEquals(expected.getCellReference().column(), actual.getCellReference().column());
    }

    private static void assertExpressionsEqual(BinaryExpression expected, BinaryExpression actual) {
        Assertions.assertEquals(expected.getOperator().type(), actual.getOperator().type());
        assertExpressionsEqual(expected.getLeft(), actual.getLeft());
        assertExpressionsEqual(expected.getRight(), actual.getRight());
    }

    private static void assertExpressionsEqual(FunctionExpression expected, FunctionExpression actual) {
        Assertions.assertEquals(expected.getFunction(), actual.getFunction());
        Assertions.assertEquals(expected.getArguments().size(), actual.getArguments().size());
        for (int i = 0; i < expected.getArguments().size(); i++) {
            assertExpressionsEqual(expected.getArguments().get(i), actual.getArguments().get(i));
        }
    }

    public static void assertExpressionsEqual(Expression expected, Expression actual) {
        if (actual instanceof NumberExpression actualNumber && expected instanceof NumberExpression expectedNumber) {
            assertExpressionsEqual(expectedNumber, actualNumber);
        } else if (actual instanceof CellReferenceExpression actualCellReference && expected instanceof CellReferenceExpression expectedCellReference) {
            assertExpressionsEqual(expectedCellReference, actualCellReference);
        } else if (actual instanceof BinaryExpression actualBinary && expected instanceof BinaryExpression expectedBinary) {
            assertExpressionsEqual(expectedBinary, actualBinary);
        } else if (actual instanceof FunctionExpression actualFunction && expected instanceof FunctionExpression expectedFunction) {
            assertExpressionsEqual(expectedFunction, actualFunction);
        } else {
            assertionFailure().message("Classes do not match").expected(expected.getClass()).actual(actual.getClass()).buildAndThrow();
        }
    }
}
