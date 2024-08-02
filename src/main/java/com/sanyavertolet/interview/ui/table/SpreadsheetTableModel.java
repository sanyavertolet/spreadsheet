package com.sanyavertolet.interview.ui.table;

import com.sanyavertolet.interview.parser.Parser;
import com.sanyavertolet.interview.data.Cell;
import com.sanyavertolet.interview.parser.TreeParser;
import com.sanyavertolet.interview.valueprocessor.CellValueProcessor;
import com.sanyavertolet.interview.valueprocessor.SimpleCellValueProcessor;

import javax.swing.table.AbstractTableModel;

public class SpreadsheetTableModel extends AbstractTableModel {
    private Cell[][] data;
    private final Parser parser;
    private final CellValueProcessor valueProcessor;

    public SpreadsheetTableModel(int rowCount, int columnCount) {
        data = new Cell[rowCount][columnCount];
        parser = new TreeParser();
        valueProcessor = new SimpleCellValueProcessor();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                data[i][j] = new Cell("", valueProcessor);
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
        data[row][col].setText(cell.getText());
        fireTableCellUpdated(row, col);
    }
}
