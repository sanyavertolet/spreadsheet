package com.sanyavertolet.interview.ui.table;

import com.sanyavertolet.interview.cells.cellmanager.CellManager;
import com.sanyavertolet.interview.cells.Cell;
import com.sanyavertolet.interview.cells.cellmanager.SimpleCellManager;

import javax.swing.table.AbstractTableModel;

public class SpreadsheetTableModel extends AbstractTableModel {
    private final CellManager cellManager;

    public SpreadsheetTableModel(int rowCount, int columnCount) {
        cellManager = new SimpleCellManager(rowCount, columnCount);
    }

    @Override
    public int getRowCount() {
        return cellManager.getRowCount();
    }

    @Override
    public int getColumnCount() {
        return cellManager.getColumnCount();
    }

    @Override
    public Object getValueAt(int row, int col) {
        return col == 0 ? row + 1 : cellManager.getCell(row, col);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        Cell cell = (Cell) value;
        cellManager.setCell(row, col, cell.getText());
        fireTableCellUpdated(row, col);
    }

    @Override
    public String getColumnName(int col) {
        return col != 0 ? super.getColumnName(col - 1) : "";
    }
}
