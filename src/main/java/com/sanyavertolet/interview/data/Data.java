package com.sanyavertolet.interview.data;

public abstract class Data {
    protected String text;

    public Data(String text) {
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
