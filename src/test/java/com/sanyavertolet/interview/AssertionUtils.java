package com.sanyavertolet.interview;

import com.sanyavertolet.interview.math.CellReference;
import com.sanyavertolet.interview.math.expressions.*;
import org.junit.jupiter.api.Assertions;

import java.util.List;

import static org.junit.jupiter.api.AssertionFailureBuilder.assertionFailure;

/**
 * Utility class for testing purposes, providing methods to assert expected outcomes in tests.
 * <p>
 * This class contains methods for asserting the order of {@code CellReference} objects in a list,
 * and for checking the equality of various types of {@code Expression} objects.
 * <p>
 * This class is not meant to be instantiated.
 */
public class AssertionUtils {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private AssertionUtils() { }

    /**
     * Asserts that a {@code CellReference} appears before another {@code CellReference} in a list.
     *
     * @param before     the {@code CellReference} that should appear before {@code after}.
     * @param after      the {@code CellReference} that should appear after {@code before}.
     * @param references the list of {@code CellReference} objects.
     * @throws AssertionError if {@code before} is not present in the list, or if {@code after} is not present,
     *                        or if {@code before} appears after {@code after}.
     */
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public static void assertHappensBefore(CellReference before, CellReference after, List<CellReference> references) {
        int indexOfBefore = references.indexOf(before);
        if (indexOfBefore == -1) {
            assertionFailure().message(before.identifier() + " is not present in list.").buildAndThrow();
        }

        int indexOfAfter = references.indexOf(after);
        if (indexOfAfter == -1) {
            assertionFailure().message(after.identifier() + " is not present in list.").buildAndThrow();
        }

        if (indexOfBefore > indexOfAfter) {
            assertionFailure()
                    .message(before.identifier() + " should go before " + after.identifier())
                    .expected("references.indexOf(" + before.identifier() + ") = " + indexOfBefore + " <= " + "references.indexOf(" + after.identifier() + ") = " + indexOfAfter)
                    .actual("references.indexOf(" + before.identifier() + ") = " + indexOfBefore + " > " + "references.indexOf(" + after.identifier() + ") = " + indexOfAfter)
                    .buildAndThrow();
        }
    }

    /**
     * Asserts that two {@code ValueExpression} objects are equal.
     *
     * @param expected the expected {@code ValueExpression}.
     * @param actual   the actual {@code ValueExpression}.
     */
    private static void assertExpressionsEqual(ValueExpression expected, ValueExpression actual) {
        Assertions.assertEquals(expected.getValue(), actual.getValue());
    }

    /**
     * Asserts that two {@code CellReferenceExpression} objects are equal.
     *
     * @param expected the expected {@code CellReferenceExpression}.
     * @param actual   the actual {@code CellReferenceExpression}.
     */
    private static void assertExpressionsEqual(CellReferenceExpression expected, CellReferenceExpression actual) {
        Assertions.assertEquals(expected.getCellReference().identifier(), actual.getCellReference().identifier());
        Assertions.assertEquals(expected.getCellReference().row(), actual.getCellReference().row());
        Assertions.assertEquals(expected.getCellReference().column(), actual.getCellReference().column());
    }

    /**
     * Asserts that two {@code BinaryExpression} objects are equal.
     *
     * @param expected the expected {@code BinaryExpression}.
     * @param actual   the actual {@code BinaryExpression}.
     */
    private static void assertExpressionsEqual(BinaryExpression expected, BinaryExpression actual) {
        Assertions.assertEquals(expected.getOperator().getType(), actual.getOperator().getType());
        assertExpressionsEqual(expected.getLeft(), actual.getLeft());
        assertExpressionsEqual(expected.getRight(), actual.getRight());
    }

    /**
     * Asserts that two {@code FunctionExpression} objects are equal.
     *
     * @param expected the expected {@code FunctionExpression}.
     * @param actual   the actual {@code FunctionExpression}.
     */
    private static void assertExpressionsEqual(FunctionExpression expected, FunctionExpression actual) {
        Assertions.assertEquals(expected.getFunction(), actual.getFunction());
        Assertions.assertEquals(expected.getArguments().size(), actual.getArguments().size());
        for (int i = 0; i < expected.getArguments().size(); i++) {
            assertExpressionsEqual(expected.getArguments().get(i), actual.getArguments().get(i));
        }
    }

    /**
     * Asserts that two {@code RangeExpression} objects are equal.
     *
     * @param expected the expected {@code RangeExpression}.
     * @param actual   the actual {@code RangeExpression}.
     */
    private static void assertExpressionsEqual(RangeExpression expected, RangeExpression actual) {
        Assertions.assertEquals(expected.getFrom(), actual.getFrom());
        Assertions.assertEquals(expected.getTo(), actual.getTo());
    }

    /**
     * Asserts that two {@code Expression} objects are equal.
     * <p>
     * This method handles various types of {@code Expression} subclasses and performs appropriate equality checks.
     *
     * @param expected the expected {@code Expression}.
     * @param actual   the actual {@code Expression}.
     * @throws AssertionError if the classes of {@code expected} and {@code actual} do not match or if they are not equal.
     */
    public static void assertExpressionsEqual(Expression expected, Expression actual) {
        if (actual instanceof ValueExpression actualNumber && expected instanceof ValueExpression expectedNumber) {
            assertExpressionsEqual(expectedNumber, actualNumber);
        } else if (actual instanceof CellReferenceExpression actualCellReference && expected instanceof CellReferenceExpression expectedCellReference) {
            assertExpressionsEqual(expectedCellReference, actualCellReference);
        } else if (actual instanceof BinaryExpression actualBinary && expected instanceof BinaryExpression expectedBinary) {
            assertExpressionsEqual(expectedBinary, actualBinary);
        } else if (actual instanceof FunctionExpression actualFunction && expected instanceof FunctionExpression expectedFunction) {
            assertExpressionsEqual(expectedFunction, actualFunction);
        } else if (actual instanceof RangeExpression actualRange && expected instanceof RangeExpression expectedRange) {
            assertExpressionsEqual(expectedRange, actualRange);
        } else {
            assertionFailure().message("Classes do not match").expected(expected.getClass()).actual(actual.getClass()).buildAndThrow();
        }
    }
}
