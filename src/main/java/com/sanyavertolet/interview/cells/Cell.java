package com.sanyavertolet.interview.cells;

public abstract class Cell {
    protected String text;

    public Cell(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    abstract public String getValueAsString();
}
