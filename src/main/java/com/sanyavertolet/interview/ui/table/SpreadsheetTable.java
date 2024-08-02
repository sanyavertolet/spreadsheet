package com.sanyavertolet.interview.ui.table;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class SpreadsheetTable extends JTable {
    public SpreadsheetTable() {
        super(new SpreadsheetTableModel(20, 10));
        TableCellRenderer renderer = new BoarderCellRenderer();
        setDefaultRenderer(Object.class, renderer);

        setShowVerticalLines(false);
    }
}
