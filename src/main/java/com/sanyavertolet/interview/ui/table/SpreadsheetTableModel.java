package com.sanyavertolet.interview.ui.table;

import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.data.manager.DataManager;
import com.sanyavertolet.interview.data.manager.SimpleDataManager;

import javax.swing.table.AbstractTableModel;

public class SpreadsheetTableModel extends AbstractTableModel {
    private final DataManager dataManager;

    public SpreadsheetTableModel(int rowCount, int columnCount) {
        dataManager = new SimpleDataManager(rowCount, columnCount);
    }

    @Override
    public int getRowCount() {
        return dataManager.getRowCount();
    }

    @Override
    public int getColumnCount() {
        return dataManager.getColumnCount();
    }

    @Override
    public Object getValueAt(int row, int col) {
        return col == 0 ? row + 1 : dataManager.getData(row, col - 1);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        Data data = (Data) value;
        dataManager.setData(row, col - 1, data.getText());
        fireTableCellUpdated(row, col);
    }

    @Override
    public String getColumnName(int col) {
        return col != 0 ? super.getColumnName(col - 1) : "";
    }
}
