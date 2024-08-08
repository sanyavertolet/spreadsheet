package com.sanyavertolet.interview.ui;

import com.sanyavertolet.interview.Configuration;
import com.sanyavertolet.interview.cells.Cell;
import com.sanyavertolet.interview.exceptions.CellAccessException;
import com.sanyavertolet.interview.exceptions.CellReferenceException;
import com.sanyavertolet.interview.math.CellReference;
import com.sanyavertolet.interview.ui.table.SpreadsheetTable;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final SpreadsheetTable table;
    private final DebugPanel debugPanel;

    public MainFrame(Configuration configuration) {
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
        debugPanel = new DebugPanel();
        if (configuration.isDebug()) {
            scrollPane.setMinimumSize(new Dimension(50, 50));
            debugPanel.setMinimumSize(new Dimension(50, 50));

            JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, debugPanel);
            splitPane.setResizeWeight(0.8);
            splitPane.setOneTouchExpandable(true);

            add(splitPane, BorderLayout.CENTER);

            table.getSelectionModel().addListSelectionListener(event -> updateDebugInfo());
            table.getColumnModel().getSelectionModel().addListSelectionListener(event -> updateDebugInfo());
        } else {
            add(scrollPane, BorderLayout.CENTER);
        }
    }

    private void updateDebugInfo() {
        try {
            CellReference cellReference = table.getSelectedCell();
            if (cellReference != null) {
                Cell cell = table.getValueAt(cellReference);
                debugPanel.setSelectedCell(cellReference, cell);
            } else {
                debugPanel.setSelectedCell(null, null);
            }
        } catch (CellReferenceException | CellAccessException ignored) {

        }
    }
}
