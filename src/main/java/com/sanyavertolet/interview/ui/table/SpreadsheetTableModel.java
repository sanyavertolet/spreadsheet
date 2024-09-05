package com.sanyavertolet.interview.ui.table;

import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.data.manager.DataManager;
import com.sanyavertolet.interview.data.manager.SimpleDataManager;

import javax.swing.table.AbstractTableModel;
import java.io.Serial;

/**
 * A table model for the spreadsheet application, extending {@link AbstractTableModel}.
 * The {@code SpreadsheetTableModel} class manages the data in the spreadsheet and interacts with the {@link DataManager}.
 */
public class SpreadsheetTableModel extends AbstractTableModel {
    @Serial
    private static final long serialVersionUID = 42L;
    private final DataManager dataManager;

    /**
     * Constructs a {@code SpreadsheetTableModel} with the specified number of rows and columns.
     * Initializes the model with a {@link SimpleDataManager} to manage the data.
     *
     * @param rowCount    the number of rows in the table.
     * @param columnCount the number of columns in the table.
     */
    public SpreadsheetTableModel(int rowCount, int columnCount) {
        dataManager = new SimpleDataManager(rowCount, columnCount, this::fireTableCellUpdated);
    }

    /**
     * Returns the number of rows in the table.
     *
     * @return the row count.
     */
    @Override
    public int getRowCount() {
        return dataManager.getRowCount();
    }

    /**
     * Returns the number of columns in the table.
     *
     * @return the column count.
     */
    @Override
    public int getColumnCount() {
        return dataManager.getColumnCount();
    }

    /**
     * Returns the value at the specified row and column in the table.
     * If the column is 0, the row number (1-based) is returned for row headers.
     * Otherwise, the {@link Data} object managed by the {@link DataManager} is returned.
     *
     * @param row the row index of the value to retrieve.
     * @param col the column index of the value to retrieve.
     * @return the value at the specified cell, or the row number if the column is 0.
     */
    @Override
    public Object getValueAt(int row, int col) {
        return col == 0 ? row + 1 : dataManager.getData(row, col);
    }

    /**
     * Returns whether the specified cell is editable. In this implementation, all cells are editable.
     *
     * @param row the row index of the cell.
     * @param col the column index of the cell.
     * @return {@code true}, as all cells are editable.
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    /**
     * Sets the value at the specified cell to the provided value and also creates a new data in the {@link DataManager}.
     * <p>
     * The value is expected to be either an instance of {@link Data} or an instance of {@link String}, otherwise nothing is done.
     *
     * @param value the new value to set.
     * @param row   the row index of the cell to update.
     * @param col   the column index of the cell to update.
     */
    @Override
    public void setValueAt(Object value, int row, int col) {
        String newValue;
        if (value instanceof Data data) {
            newValue = data.getText();
        } else if (value instanceof String string) {
            newValue = string;
        } else {
            return;
        }
        dataManager.setData(row, col, newValue);
    }

    /**
     * Returns the name of the specified column. The first column (index 0) is typically reserved for row headers and returns an empty string.
     * For other columns, the column name is returned based on the default column naming scheme.
     *
     * @param col the column index.
     * @return the name of the specified column, or an empty string for the first column.
     */
    @Override
    public String getColumnName(int col) {
        return col != 0 ? super.getColumnName(col - 1) : "";
    }

    /**
     * Returns the {@link DataManager} associated with this table model.
     *
     * @return the data manager managing the table's data.
     */
    public DataManager getDataManager() {
        return dataManager;
    }
}
