package com.sanyavertolet.interview.math;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanyavertolet.interview.exceptions.CellReferenceException;

import java.util.List;

public record CellReference(int row, int column, String identifier) {
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

    public static CellReference of(String identifier) throws CellReferenceException {
        List<Integer> coordinates = identifierToCoordinates(identifier);
        return new CellReference(coordinates.get(0), coordinates.get(1), identifier);
    }

    public static CellReference of(int row, int column) throws CellReferenceException {
        return new CellReference(row, column, coordinatesToIdentifier(row, column));
    }

    @Override
    public String toString() {
        return identifier;
    }

    public record WithText(@JsonProperty("ref") CellReference reference, String text) { }
}
