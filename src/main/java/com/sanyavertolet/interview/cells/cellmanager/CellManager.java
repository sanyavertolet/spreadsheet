package com.sanyavertolet.interview.cells.cellmanager;

import com.sanyavertolet.interview.cells.Cell;

public interface CellManager extends CellAccessor {
    void setCell(int row, int column, String text);

    int getRowCount();

    Cell getCell(int row, int column);

    int getColumnCount();
}
