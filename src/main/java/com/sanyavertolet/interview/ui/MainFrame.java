package com.sanyavertolet.interview.ui;

import com.sanyavertolet.interview.cells.Cell;
import com.sanyavertolet.interview.ui.table.SpreadsheetTable;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final SpreadsheetTable table;
    private final DebugPanel debugPanel;

    public MainFrame() {
        setTitle("Spreadsheets");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setJMenuBar(new MenuBar());

        table = new SpreadsheetTable();

        JScrollPane scrollPane = new JScrollPane(
                table,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        add(scrollPane, BorderLayout.CENTER);

        debugPanel = new DebugPanel();
        add(debugPanel, BorderLayout.SOUTH);

        table.getSelectionModel().addListSelectionListener(event -> updateDebugInfo());
        table.getColumnModel().getSelectionModel().addListSelectionListener(event -> updateDebugInfo());

        setVisible(true);
    }

    private void updateDebugInfo() {
        int row = table.getSelectedRow();
        int column = table.getSelectedColumn();
        if (row != -1 && column > 0) {
            Cell cell = (Cell) table.getValueAt(row, column);
            debugPanel.setSelectedCell(cell, row, column);
        } else {
            debugPanel.setSelectedCell(null, -1, -1);
        }
    }
}
