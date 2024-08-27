package com.sanyavertolet.interview.ui;

import com.sanyavertolet.interview.exceptions.CellReferenceException;
import com.sanyavertolet.interview.exceptions.data.DataAccessException;
import com.sanyavertolet.interview.math.CellReference;
import com.sanyavertolet.interview.ui.table.SpreadsheetTable;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * A utility class for configuring key bindings for a {@link SpreadsheetTable}.
 * This class sets up keyboard shortcuts for common operations such as copy, cut, and paste.
 * The key bindings are associated with the following actions:
 * <ul>
 *     <li><code>Ctrl+C</code> for copying the selected cell's content to the system clipboard.</li>
 *     <li><code>Ctrl+X</code> for cutting the selected cell's content to the system clipboard and clearing the cell's content.</li>
 *     <li><code>Ctrl+V</code> for pasting the content from the system clipboard into the selected cell.</li>
 * </ul>
 */
public class KeyBindingsConfigurator {
    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private KeyBindingsConfigurator() { }

    /**
     * Configures key bindings for the given {@link SpreadsheetTable}.
     * This method sets up the following key bindings:
     * <ul>
     *     <li><code>Ctrl+C</code> to copy the content of the selected cell to the system clipboard.</li>
     *     <li><code>Ctrl+X</code> to cut the content of the selected cell to the system clipboard and clear the cell's content.</li>
     *     <li><code>Ctrl+V</code> to paste the content from the system clipboard into the selected cell.</li>
     * </ul>
     *
     * @param table The {@link SpreadsheetTable} to configure key bindings for.
     *              The table must be able to return the selected cell and its contents, and to set values in cells.
     */
    public static void configure(SpreadsheetTable table) {
        InputMap inputMap = table.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap actionMap = table.getActionMap();
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        inputMap.put(KeyStroke.getKeyStroke("control C"), "copy");
        inputMap.put(KeyStroke.getKeyStroke("control X"), "cut");
        inputMap.put(KeyStroke.getKeyStroke("control V"), "paste");

        actionMap.put("copy", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    copyToClipboard(table, clipboard);
                } catch (CellReferenceException | DataAccessException ignored) { }
            }
        });

        actionMap.put("cut", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    CellReference selectedCell = table.getSelectedCell();
                    copyToClipboard(table, clipboard);
                    table.setValueAt("", selectedCell.row(), selectedCell.column());
                } catch (CellReferenceException | DataAccessException ignored) { }
            }
        });

        actionMap.put("paste", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Transferable transferable = clipboard.getContents(null);
                if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    try {
                        String text = (String) transferable.getTransferData(DataFlavor.stringFlavor);
                        CellReference selectedReference = table.getSelectedCell();
                        if (selectedReference == null) {
                            return;
                        }
                        table.setValueAt(text, selectedReference.row(), selectedReference.column());
                    } catch (UnsupportedFlavorException | IOException | CellReferenceException ignored) { }
                }
            }
        });
    }

    /**
     * Copies the content of the selected cell to the system clipboard.
     * This method retrieves the content of the selected cell, wraps it in a {@link StringSelection},
     * and sets it on the system clipboard.
     *
     * @param table The {@link SpreadsheetTable} from which the content is copied.
     * @param clipboard The system clipboard where the content is stored.
     * @throws CellReferenceException If there is an issue retrieving the selected cell reference.
     * @throws DataAccessException If there is an issue accessing the cell's data.
     */
    private static void copyToClipboard(SpreadsheetTable table, Clipboard clipboard) throws CellReferenceException, DataAccessException {
        CellReference selectedReference = table.getSelectedCell();
        if (selectedReference == null) {
            return;
        }
        String copiedText = table.getValueAt(selectedReference).getText();
        if (copiedText == null) {
            return;
        }
        StringSelection selection = new StringSelection(copiedText);
        clipboard.setContents(selection, selection);
    }
}
