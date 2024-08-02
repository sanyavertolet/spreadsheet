package com.sanyavertolet.interview.valueprocessor;

import com.sanyavertolet.interview.data.CellValue;

public class SimpleCellValueProcessor implements CellValueProcessor {
    @Override
    public CellValue process(String cellText) {

        try {
            return new CellValue(CellValue.CellValueType.INT, Integer.valueOf(cellText).toString());
        } catch (NumberFormatException ignored) {

        }

        try {
            return new CellValue(CellValue.CellValueType.DOUBLE, Double.valueOf(cellText).toString());
        } catch (NumberFormatException ignored) {

        }

        if (cellText.trim().equalsIgnoreCase("true")) {
            return new CellValue(CellValue.CellValueType.BOOLEAN, "TRUE");
        } else if (cellText.trim().equalsIgnoreCase("false")) {
            return new CellValue(CellValue.CellValueType.BOOLEAN, "FALSE");
        }

        if (cellText.startsWith("=")) {
            return new CellValue(CellValue.CellValueType.EXPRESSION, cellText);
        }

        return new CellValue(CellValue.CellValueType.STRING, cellText);
    }
}
