package com.sanyavertolet.interview.ui;

import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.exceptions.CellReferenceException;
import com.sanyavertolet.interview.exceptions.data.DataAccessException;
import com.sanyavertolet.interview.math.CellReference;
import com.sanyavertolet.interview.ui.table.SpreadsheetTable;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * A utility class for configuring key bindings for a {@link SpreadsheetTable}.
 * This class sets up keyboard shortcuts for common operations such as copy, cut, and paste.
 * The key bindings are associated with the following actions:
 * <ul>
 *     <li><code>Ctrl+C</code> for copying the selected cell's content</li>
 *     <li><code>Ctrl+X</code> for cutting the selected cell's content</li>
 *     <li><code>Ctrl+V</code> for pasting the copied content into the selected cell</li>
 * </ul>
 * <p>
 * Note: The copied content is temporarily stored in the static {@code copyPasteBuffer} and is used for paste operations.
 * </p>
 */
public class KeyBindingsConfigurator {
    private static String copyPasteBuffer;

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private KeyBindingsConfigurator() { }

    /**
     * Configures key bindings for the given {@link SpreadsheetTable}.
     * This method sets up the following key bindings:
     * <ul>
     *     <li><code>Ctrl+C</code> to copy the content of the selected cell to a buffer.</li>
     *     <li><code>Ctrl+X</code> to cut the content of the selected cell to a buffer and clear the cell's content.</li>
     *     <li><code>Ctrl+V</code> to paste the content from the buffer into the selected cell.</li>
     * </ul>
     *
     * @param table The {@link SpreadsheetTable} to configure key bindings for.
     *              The table must be able to return the selected cell and its contents, and to set values in cells.
     */
    public static void configure(SpreadsheetTable table) {
        InputMap inputMap = table.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap actionMap = table.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("control C"), "copy");
        inputMap.put(KeyStroke.getKeyStroke("control X"), "cut");
        inputMap.put(KeyStroke.getKeyStroke("control V"), "paste");

        actionMap.put("copy", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CellReference selectedReference;
                try {
                    selectedReference = table.getSelectedCell();
                    if (selectedReference == null) {
                        return;
                    }
                    Data copiedData = table.getValueAt(selectedReference);
                    copyPasteBuffer = copiedData.getText();
                } catch (CellReferenceException | DataAccessException ignored) { }
            }
        });

        actionMap.put("cut", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CellReference selectedReference;
                try {
                    selectedReference = table.getSelectedCell();
                    if (selectedReference == null) {
                        return;
                    }
                    Data copiedData = table.getValueAt(selectedReference);
                    copyPasteBuffer = copiedData.getText();
                    table.setValueAt("", selectedReference.row(), selectedReference.column());
                } catch (CellReferenceException | DataAccessException ignored) { }
            }
        });

        actionMap.put("paste", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (copyPasteBuffer == null) {
                    return;
                }
                CellReference selectedReference;
                try {
                    selectedReference = table.getSelectedCell();
                    if (selectedReference == null) {
                        return;
                    }
                    table.setValueAt(copyPasteBuffer, selectedReference.row(), selectedReference.column());
                } catch (CellReferenceException ignored) { }
            }
        });
    }
}
