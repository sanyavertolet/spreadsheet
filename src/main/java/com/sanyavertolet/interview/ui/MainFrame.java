package com.sanyavertolet.interview.ui;

import com.sanyavertolet.interview.Configuration;
import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.exceptions.data.DataAccessException;
import com.sanyavertolet.interview.exceptions.CellReferenceException;
import com.sanyavertolet.interview.math.CellReference;
import com.sanyavertolet.interview.ui.menu.MenuBar;
import com.sanyavertolet.interview.ui.table.SpreadsheetTable;

import javax.swing.*;
import java.awt.*;

/**
 * The main window for the spreadsheet application, extending {@link JFrame}. The {@code MainFrame} class
 * is responsible for initializing and displaying the spreadsheet table, as well as the optional debug panel
 * based on the provided {@link Configuration}.
 */
public class MainFrame extends JFrame {
    /**
     * Main excel {@link SpreadsheetTable} component
     */
    private final SpreadsheetTable table;

    /**
     * {@link DebugPanel} component
     */
    private final DebugPanel debugPanel;

    /**
     * Constructs a {@code MainFrame} with the specified configuration. The main frame includes a spreadsheet table
     * and optionally a debug panel, depending on the debug flag in the configuration.
     *
     * @param configuration the configuration settings that determine whether the debug panel is shown.
     */
    public MainFrame(Configuration configuration) {
        setTitle("Spreadsheets");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        table = new SpreadsheetTable();

        MenuBar menuBar = new MenuBar(table.getDataManager());

        JScrollPane scrollPane = new JScrollPane(
                table,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        debugPanel = new DebugPanel();

        setJMenuBar(menuBar);

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

    /**
     * Updates the debug panel with information about the currently selected cell in the spreadsheet.
     * If no cell is selected or an error occurs, the debug panel is cleared.
     */
    private void updateDebugInfo() {
        try {
            CellReference cellReference = table.getSelectedCell();
            if (cellReference != null) {
                Data data = table.getValueAt(cellReference);
                debugPanel.setSelectedCell(cellReference, data);
            } else {
                debugPanel.setSelectedCell(null, null);
            }
        } catch (CellReferenceException | DataAccessException ignored) { }
    }
}
