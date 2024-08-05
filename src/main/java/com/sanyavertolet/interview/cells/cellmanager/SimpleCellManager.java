package com.sanyavertolet.interview.cells.cellmanager;

import com.sanyavertolet.interview.cells.Cell;
import com.sanyavertolet.interview.cells.DoubleCell;
import com.sanyavertolet.interview.cells.ExpressionCell;
import com.sanyavertolet.interview.cells.TextCell;
import com.sanyavertolet.interview.cells.cellfactory.CellFactory;
import com.sanyavertolet.interview.cells.cellfactory.SimpleCellFactory;
import com.sanyavertolet.interview.exceptions.CellAccessException;
import com.sanyavertolet.interview.math.CellReference;

public class SimpleCellManager implements CellManager {
    private final CellFactory cellFactory = new SimpleCellFactory(this);
    private final Cell[][] data;

    public SimpleCellManager(int rows, int columns) {
        data = new Cell[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                data[i][j] = new TextCell("");
            }
        }
    }

    @Override
    public void setCell(int row, int column, String text) {
        data[row][column] = cellFactory.create(text);
    }

    @Override
    public Cell getCell(int row, int column) {
        return data[row][column];
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return data.length > 0 ? data[0].length + 1 : 0;
    }

    @Override
    public Double getDoubleCellValue(CellReference cellReference) throws CellAccessException {
        if (!hasCell(cellReference)) {
            throw new CellAccessException("Cell " + cellReference + " is not present");
        }
        Cell cell = data[cellReference.row()][cellReference.column()];
        if (cell instanceof DoubleCell doubleCell) {
            return doubleCell.getValue();
        } else if (cell instanceof ExpressionCell expressionCell) {
            return expressionCell.getValue();
        }

        throw new CellAccessException("Cell " + cellReference + " has insufficient data type: " + cell.getClass().getSimpleName());
    }

    @Override
    public boolean hasCell(CellReference cellReference) {
        return cellReference.row() < data.length && cellReference.column() < data[cellReference.row()].length;
    }
}
