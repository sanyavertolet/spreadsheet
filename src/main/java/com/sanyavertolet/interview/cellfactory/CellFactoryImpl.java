package com.sanyavertolet.interview.cellfactory;

import com.sanyavertolet.interview.data.Cell;
import com.sanyavertolet.interview.data.TextCell;

public class CellFactoryImpl implements CellFactory {
    @Override
    public Cell create(String cellText) {
        return new TextCell(cellText);
    }
}
