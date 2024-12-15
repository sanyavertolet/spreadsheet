package io.github.sanyavertolet.interview.modals;

import io.github.sanyavertolet.interview.SpreadsheetMetadata;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * A utility class for presenting a UI that allows the user to select a spreadsheet from a list of metadata.
 *
 * <p>This class displays a modal window containing a list of {@link SpreadsheetMetadata} objects. The user can
 * select one spreadsheet, and the result is returned asynchronously via a {@link CompletableFuture}. The UI
 * includes a scrollable list of spreadsheet names and creators, and a "Select" button to confirm the user's choice.</p>
 */
public class SpreadsheetMetadataPicker {

    /**
     * Opens a selection dialog for choosing a spreadsheet from a list of {@link SpreadsheetMetadata}.
     *
     * <p>This method creates a Swing-based GUI that displays the provided list of spreadsheet metadata.
     * The user can select a spreadsheet from the list, and the result is returned as a {@link CompletableFuture}.
     * If the user does not select a spreadsheet and closes the window, the future will remain incomplete.</p>
     *
     * @param spreadsheetMetadataList the list of spreadsheet metadata to display.
     * @return a {@link CompletableFuture} that completes with the selected {@link SpreadsheetMetadata} when
     *         the user makes a selection.
     */
    public static CompletableFuture<SpreadsheetMetadata> pick(List<SpreadsheetMetadata> spreadsheetMetadataList) {
        CompletableFuture<SpreadsheetMetadata> result = new CompletableFuture<>();

        JFrame frame = new JFrame("Select a Spreadsheet");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        DefaultListModel<SpreadsheetMetadata> listModel = new DefaultListModel<>();
        spreadsheetMetadataList.forEach(listModel::addElement);

        JList<SpreadsheetMetadata> list = new JList<>(listModel);
        list.setCellRenderer(new SpreadsheetMetadataCellRenderer());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(list);

        JButton selectButton = new JButton("Select");
        selectButton.addActionListener(e -> {
            SpreadsheetMetadata selectedMetadata = list.getSelectedValue();
            if (selectedMetadata != null) {
                frame.dispose();
                result.complete(selectedMetadata);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a spreadsheet!");
            }
        });

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(selectButton, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        return result;
    }

    /**
     * Custom cell renderer for displaying {@link SpreadsheetMetadata} in a {@link JList}.
     *
     * <p>This renderer customizes how each item in the list is displayed, showing the spreadsheet name
     * and creator in a user-friendly format.</p>
     */
    private static class SpreadsheetMetadataCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof SpreadsheetMetadata metadata) {
                setText(metadata.getName() + " (by " + metadata.getCreator() + ")");
            }
            return component;
        }
    }
}
