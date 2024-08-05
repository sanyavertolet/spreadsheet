package com.sanyavertolet.interview.math;

import com.sanyavertolet.interview.exceptions.CellReferenceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CellReferenceTest {

    @Test
    void simpleCellReferenceTest() throws CellReferenceException {
        String expectedIdentifier = "A1";
        int expectedRow = 0;
        int expectedColumn = 1;
        CellReference reference = CellReference.of(expectedRow, expectedColumn);
        Assertions.assertEquals(expectedIdentifier, reference.identifier());
        Assertions.assertEquals(expectedRow, reference.row());
        Assertions.assertEquals(expectedColumn, reference.column());
    }

    @Test
    void simpleCellReferenceFromIdentifierTest() throws CellReferenceException {
        String expectedIdentifier = "A1";
        int expectedRow = 0;
        int expectedColumn = 1;
        CellReference reference = CellReference.of(expectedIdentifier);
        Assertions.assertEquals(expectedIdentifier, reference.identifier());
        Assertions.assertEquals(expectedRow, reference.row());
        Assertions.assertEquals(expectedColumn, reference.column());
    }

    @Test
    void multiSymRowCellReferenceTest() throws CellReferenceException {
        String expectedIdentifier = "P12";
        int expectedRow = 11;
        int expectedColumn = 16;
        CellReference reference = CellReference.of(expectedRow, expectedColumn);
        Assertions.assertEquals(expectedIdentifier, reference.identifier());
        Assertions.assertEquals(expectedRow, reference.row());
        Assertions.assertEquals(expectedColumn, reference.column());
    }

    @Test
    void multiSymRowCellReferenceFromIdentifierTest() throws CellReferenceException {
        String expectedIdentifier = "P12";
        int expectedRow = 11;
        int expectedColumn = 16;
        CellReference reference = CellReference.of(expectedIdentifier);
        Assertions.assertEquals(expectedIdentifier, reference.identifier());
        Assertions.assertEquals(expectedRow, reference.row());
        Assertions.assertEquals(expectedColumn, reference.column());
    }

    @Test
    void multiSymColumnCellReferenceTest() throws CellReferenceException{
        String expectedIdentifier = "AB7";
        int expectedRow = 6;
        int expectedColumn = 28;
        CellReference reference = CellReference.of(expectedRow, expectedColumn);
        Assertions.assertEquals(expectedIdentifier, reference.identifier());
        Assertions.assertEquals(expectedRow, reference.row());
        Assertions.assertEquals(expectedColumn, reference.column());
    }

    @Test
    void multiSymColumnCellReferenceFromIdentifierTest() throws CellReferenceException{
        String expectedIdentifier = "AA7";
        int expectedRow = 6;
        int expectedColumn = 27;
        CellReference reference = CellReference.of(expectedIdentifier);
        Assertions.assertEquals(expectedIdentifier, reference.identifier());
        Assertions.assertEquals(expectedRow, reference.row());
        Assertions.assertEquals(expectedColumn, reference.column());
    }

    @Test
    void negativeRowNumberCellReferenceTest() {
        Assertions.assertThrows(CellReferenceException.class, () -> CellReference.of(-1, 1));
    }

    @Test
    void negativeColumnNumberCellReferenceTest() {
        Assertions.assertThrows(CellReferenceException.class, () -> CellReference.of(1, -1));
    }

    @Test
    void negativeRowNumberInIdentifierCellReferenceTest() {
        Assertions.assertThrows(CellReferenceException.class, () -> CellReference.of("A-1"));
    }

    @Test
    void emptyIdentifierCellReferenceTest() {
        Assertions.assertThrows(CellReferenceException.class, () -> CellReference.of(""));
    }

    @Test
    void nullIdentifierCellReferenceTest() {
        Assertions.assertThrows(CellReferenceException.class, () -> CellReference.of(null));
    }
}
