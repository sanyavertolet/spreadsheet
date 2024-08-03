package com.sanyavertolet.interview.data;

public class IntegerCell extends Cell {
    private final Integer value;

    public IntegerCell(String text) {
        super(text);
        try {
            value = Integer.valueOf(text);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid double value: " + text);
        }
    }

    public IntegerCell(String text, Integer value) {
        super(text);
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String getValueAsString() {
        return value.toString();
    }
}
