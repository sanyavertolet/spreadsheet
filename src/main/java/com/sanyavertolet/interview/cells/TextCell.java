package com.sanyavertolet.interview.cells;

public class TextCell extends Cell {
    public TextCell(String text) {
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
