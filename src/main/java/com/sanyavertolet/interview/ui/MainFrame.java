package com.sanyavertolet.interview.ui;

import com.sanyavertolet.interview.data.Cell;
import com.sanyavertolet.interview.ui.table.SpreadsheetTable;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private SpreadsheetTable table;
    private DebugPanel debugPanel;

    public MainFrame() {
        setTitle("Spreadsheets");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setJMenuBar(new MenuBar());

        table = new SpreadsheetTable();
        add(new JScrollPane(table), BorderLayout.CENTER);

        debugPanel = new DebugPanel();
        add(debugPanel, BorderLayout.SOUTH);

        table.getSelectionModel().addListSelectionListener(event -> updateDebugInfo());

        table.getColumnModel().getSelectionModel().addListSelectionListener(event -> updateDebugInfo());

        setVisible(true);
    }

    private void updateDebugInfo() {
        int row = table.getSelectedRow();
        int column = table.getSelectedColumn();
        if (row != -1 && column != -1) {
            Cell cell = (Cell) table.getValueAt(row, column);
            debugPanel.setSelectedCell(cell, row, column);
        } else {
            debugPanel.setSelectedCell(null, -1, -1);
        }
    }
}
