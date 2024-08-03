package com.sanyavertolet.interview.cells;

public class DoubleCell extends Cell {
    private final Double value;

    public DoubleCell(String text) {
        super(text);
        try {
            value = Double.valueOf(text);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid double value: " + text);
        }
    }

    public DoubleCell(String text, Double value) {
        super(text);
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public String getValueAsString() {
        return value.toString();
    }
}
