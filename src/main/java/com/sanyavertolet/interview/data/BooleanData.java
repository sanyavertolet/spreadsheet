package com.sanyavertolet.interview.data;

public class BooleanData extends Data {
    private final Boolean value;

    public BooleanData(String text) {
        super(text);
        if (text.trim().equalsIgnoreCase("true")) {
            this.value = true;
        } else if (text.trim().equalsIgnoreCase("false")) {
            this.value = false;
        } else {
            throw new IllegalArgumentException("Invalid boolean value: " + text);
        }
    }

    public BooleanData(String text, Boolean value) {
        super(text);
        this.value = value;
    }

    public Boolean getValue() {
        return value;
    }

    @Override
    public String getValueAsString() {
        return value.toString();
    }
}
