package com.sanyavertolet.interview.data;

public class DoubleData extends Data {
    private final Double value;

    public DoubleData(String text, Double value) {
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
