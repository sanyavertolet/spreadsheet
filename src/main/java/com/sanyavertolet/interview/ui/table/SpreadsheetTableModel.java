package com.sanyavertolet.interview.ui.table;

import com.sanyavertolet.interview.cellfactory.SimpleCellFactory;
import com.sanyavertolet.interview.data.TextCell;
import com.sanyavertolet.interview.data.Cell;
import com.sanyavertolet.interview.cellfactory.CellFactory;

import javax.swing.table.AbstractTableModel;

public class SpreadsheetTableModel extends AbstractTableModel {
    private final Cell[][] data;
    private final CellFactory cellFactory;

    public SpreadsheetTableModel(int rowCount, int columnCount) {
        data = new Cell[rowCount][columnCount];
        cellFactory = new SimpleCellFactory();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                data[i][j] = new TextCell("");
            }
        }
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return data.length > 0 ? data[0].length : 0;
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        Cell cell = (Cell) value;
        data[row][col] = cellFactory.create(cell.getText());
        fireTableCellUpdated(row, col);
    }
}
