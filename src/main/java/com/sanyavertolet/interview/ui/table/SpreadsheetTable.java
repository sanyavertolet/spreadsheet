package com.sanyavertolet.interview.ui.table;

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
}
