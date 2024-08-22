package com.sanyavertolet.interview.math;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanyavertolet.interview.exceptions.CellReferenceException;

import java.util.List;

/**
 * A record representing a cell reference in a spreadsheet, which includes the row, column, and a string identifier.
 * The {@code CellReference} class provides utilities to convert between coordinate-based references and identifier-based references.
 *
 * @param row        the zero-based row index of the cell.
 * @param column     the one-based column index of the cell (e.g., 1 for column A).
 * @param identifier the string identifier of the cell (e.g., "A1").
 */
public record CellReference(int row, int column, String identifier) {

    /**
     * Converts row and column indices into a cell identifier string (e.g., "A1").
     *
     * @param row    the zero-based row index.
     * @param column the one-based column index.
     * @return the cell identifier string.
     * @throws CellReferenceException if the row or column index is invalid.
     */
    private static String coordinatesToIdentifier(int row, int column) throws CellReferenceException {
        if (row < 0 || column < 0) {
            throw new CellReferenceException("Wrong cell coordinates: " + row + ", " + column);
        }
        StringBuilder sb = new StringBuilder();
        while (column > 0) {
            column--;
            sb.append((char) ('A' + column % 26));
            column /= 26;
        }

        return sb.reverse().toString() + (row + 1);
    }

    /**
     * Converts a cell identifier string into row and column indices.
     *
     * @param identifier the cell identifier string (e.g., "A1").
     * @return a list containing the zero-based row index and the one-based column index.
     * @throws CellReferenceException if the identifier is invalid or cannot be parsed.
     */
    private static List<Integer> identifierToCoordinates(String identifier) throws CellReferenceException {
        if (identifier == null || identifier.isEmpty()) {
            throw new CellReferenceException("Cell identifier cannot be null or empty");
        }

        int columnIndex = 0;
        int rowIndex = 0;
        int i = 0;

        while (i < identifier.length() && Character.isLetter(identifier.charAt(i))) {
            columnIndex = columnIndex * 26 + (Character.toUpperCase(identifier.charAt(i)) - 'A' + 1);
            i++;
        }

        while (i < identifier.length() && Character.isDigit(identifier.charAt(i))) {
            rowIndex = rowIndex * 10 + (identifier.charAt(i) - '0');
            i++;
        }

        if (i != identifier.length() || columnIndex == 0 || rowIndex == 0) {
            throw new CellReferenceException("Invalid cell identifier: " + identifier);
        }

        return List.of(rowIndex - 1, columnIndex);
    }

    /**
     * Creates a {@code CellReference} from a string identifier.
     *
     * @param identifier the cell identifier string (e.g., "A1").
     * @return a {@code CellReference} representing the cell.
     * @throws CellReferenceException if the identifier is invalid or cannot be parsed.
     */
    public static CellReference of(String identifier) throws CellReferenceException {
        List<Integer> coordinates = identifierToCoordinates(identifier);
        return new CellReference(coordinates.get(0), coordinates.get(1), identifier);
    }

    /**
     * Creates a {@code CellReference} from row and column indices.
     *
     * @param row    the zero-based row index.
     * @param column the one-based column index.
     * @return a {@code CellReference} representing the cell.
     * @throws CellReferenceException if the row or column index is invalid.
     */
    public static CellReference of(int row, int column) throws CellReferenceException {
        return new CellReference(row, column, coordinatesToIdentifier(row, column));
    }

    /**
     * Returns the cell identifier string for this {@code CellReference}.
     *
     * @return the cell identifier string (e.g., "A1").
     */
    @Override
    public String toString() {
        return identifier;
    }

    /**
     * A record representing a cell reference with associated text content.
     *
     * @param reference the {@link CellReference} of the cell.
     * @param text      the text content associated with the cell.
     */
    public record WithText(@JsonProperty("ref") CellReference reference, String text) { }
}
