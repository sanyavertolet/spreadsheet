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

public class SpreadsheetTable extends JTable {
    public SpreadsheetTable() {
        super(new SpreadsheetTableModel(50, 26));
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
    }

    public CellReference getSelectedCell() throws CellReferenceException {
        int row = getSelectedRow();
        int col = getSelectedColumn();
        if (row < 0 || col < 1) {
            return null;
        }
        return CellReference.of(getSelectedRow(), getSelectedColumn());
    }

    public Data getValueAt(CellReference cellReference) throws DataAccessException {
        Object data = getValueAt(cellReference.row(), cellReference.column());
        if (data instanceof Data cell) {
            return cell;
        }
        throw new DataAccessException("Cannot access cell " + cellReference.identifier() + " due to its type.");
    }

    public DataManager getDataManager() {
        SpreadsheetTableModel tableModel = (SpreadsheetTableModel) getModel();
        return tableModel.getDataManager();
    }
}
