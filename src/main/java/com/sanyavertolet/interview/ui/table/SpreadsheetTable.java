package com.sanyavertolet.interview.ui.table;

import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.data.manager.DataManager;
import com.sanyavertolet.interview.exceptions.data.DataAccessException;
import com.sanyavertolet.interview.exceptions.CellReferenceException;
import com.sanyavertolet.interview.math.CellReference;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.Serial;

/**
 * A custom table component for displaying and interacting with a spreadsheet.
 * The {@code SpreadsheetTable} class extends {@link JTable} and is customized to handle
 * cell-specific rendering, editing, and data management for a spreadsheet application.
 */
public class SpreadsheetTable extends JTable {
    @Serial
    private static final long serialVersionUID = 42L;

    /**
     * Constructs a {@code SpreadsheetTable} with a default model consisting of 50 rows and 26 columns.
     * The table is configured with custom cell renderers, editors, and grid settings suitable for spreadsheet use.
     */
    public SpreadsheetTable() {
        super(new SpreadsheetTableModel(100, 26));
        TableCellRenderer renderer = new CustomCellRenderer();
        setDefaultRenderer(Object.class, renderer);

        TableCellEditor editor = new CustomCellEditor();
        setDefaultEditor(Object.class, editor);

        TableColumn rowHeaderColumn = getColumnModel().getColumn(0);
        RowHeaderRenderer rowHeaderRenderer = new RowHeaderRenderer();
        rowHeaderColumn.setCellRenderer(rowHeaderRenderer);
        rowHeaderColumn.setPreferredWidth(20);

        setCellSelectionEnabled(true);
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setGridColor(Color.LIGHT_GRAY);
        setShowGrid(true);
        getTableHeader().setReorderingAllowed(false);

        KeyBindingsConfigurator.configure(this);
    }

    /**
     * Returns the {@link CellReference} of the currently selected cell in the table.
     * If no valid cell is selected, it returns {@code null}.
     *
     * @return the {@link CellReference} of the selected cell, or {@code null} if no valid cell is selected.
     * @throws CellReferenceException if the selected cell coordinates are invalid.
     */
    public CellReference getSelectedCell() throws CellReferenceException {
        int row = getSelectedRow();
        int col = getSelectedColumn();
        if (row < 0 || col < 1) {
            return null;
        }
        return CellReference.of(getSelectedRow(), getSelectedColumn());
    }

    /**
     * Retrieves the {@link Data} object stored at the specified {@link CellReference} in the table.
     *
     * @param cellReference the reference to the cell whose data is to be retrieved.
     * @return the {@link Data} object stored at the specified cell.
     * @throws DataAccessException if the cell data cannot be accessed or is of an unexpected type.
     */
    public Data getValueAt(CellReference cellReference) throws DataAccessException {
        Object data = getValueAt(cellReference.row(), cellReference.column());
        if (data instanceof Data cell) {
            return cell;
        }
        throw new DataAccessException("Cannot access cell " + cellReference.identifier() + " due to its type.");
    }

    /**
     * Returns the {@link DataManager} associated with the table's model.
     *
     * @return the {@link DataManager} managing the table's data.
     */
    public DataManager getDataManager() {
        SpreadsheetTableModel tableModel = (SpreadsheetTableModel) getModel();
        return tableModel.getDataManager();
    }
}
