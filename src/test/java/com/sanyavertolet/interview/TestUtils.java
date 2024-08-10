package com.sanyavertolet.interview;

import com.sanyavertolet.interview.math.CellReference;

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
}
