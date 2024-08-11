package com.sanyavertolet.interview.math;

import com.sanyavertolet.interview.exceptions.CellReferenceException;

import java.util.List;

public record CellReference(int row, int column, String identifier) {
    private static String coordinatesToReference(int row, int column) throws CellReferenceException {
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

    private static List<Integer> referenceToCoordinates(String reference) throws CellReferenceException {
        if (reference == null || reference.isEmpty()) {
            throw new CellReferenceException("Cell identifier cannot be null or empty");
        }

        int columnIndex = 0;
        int rowIndex = 0;
        int i = 0;

        while (i < reference.length() && Character.isLetter(reference.charAt(i))) {
            columnIndex = columnIndex * 26 + (Character.toUpperCase(reference.charAt(i)) - 'A' + 1);
            i++;
        }

        while (i < reference.length() && Character.isDigit(reference.charAt(i))) {
            rowIndex = rowIndex * 10 + (reference.charAt(i) - '0');
            i++;
        }

        if (i != reference.length() || columnIndex == 0 || rowIndex == 0) {
            throw new CellReferenceException("Invalid cell identifier: " + reference);
        }

        return List.of(rowIndex - 1, columnIndex);
    }

    public static CellReference of(String reference) throws CellReferenceException {
        List<Integer> coordinates = referenceToCoordinates(reference);
        return new CellReference(coordinates.get(0), coordinates.get(1), reference);
    }

    public static CellReference of(int row, int column) throws CellReferenceException {
        return new CellReference(row, column, coordinatesToReference(row, column));
    }

    @Override
    public String toString() {
        return identifier;
    }
}
