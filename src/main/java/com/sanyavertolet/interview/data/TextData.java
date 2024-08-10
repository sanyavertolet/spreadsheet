package com.sanyavertolet.interview.data;

public class TextData extends Data {
    public TextData(String text) {
        super(text);
    }

    public String getValue() {
        return text;
    }

    @Override
    public String getValueAsString() {
        return getText();
    }
}
