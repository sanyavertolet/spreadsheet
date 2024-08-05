package com.sanyavertolet.interview.ui.table;

import com.sanyavertolet.interview.cells.Cell;
import com.sanyavertolet.interview.exceptions.CellAccessException;
import com.sanyavertolet.interview.exceptions.CellReferenceException;
import com.sanyavertolet.interview.math.CellReference;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;

public class SpreadsheetTable extends JTable {
    public SpreadsheetTable() {
        super(new SpreadsheetTableModel(50, 50));
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

    public Cell getValueAt(CellReference cellReference) throws CellAccessException {
        Object data = getValueAt(cellReference.row(), cellReference.column());
        if (data instanceof Cell cell) {
            return cell;
        }
        throw new CellAccessException("Cannot access cell " + cellReference.identifier() + " due to its type.");
    }
}
