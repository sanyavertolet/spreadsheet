package com.sanyavertolet.interview.valueprocessor;

import com.sanyavertolet.interview.data.CellValue;

public class DummyCellValueProcessor implements CellValueProcessor {
    @Override
    public CellValue process(String cellText) {
        return new CellValue(CellValue.CellValueType.STRING, cellText);
    }
}
