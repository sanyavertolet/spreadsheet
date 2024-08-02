package com.sanyavertolet.interview.data;

import com.sanyavertolet.interview.valueprocessor.CellValueProcessor;

public class Cell {
    private String text;
    private CellValue value;
    private final CellValueProcessor valueProcessor;

    public Cell(String text, CellValueProcessor cellValueProcessor) {
        this.text = text;
        this.valueProcessor = cellValueProcessor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        value = valueProcessor.process(text);
    }
}
