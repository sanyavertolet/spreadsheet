package io.github.sanyavertolet.interview.modals;

import io.github.sanyavertolet.interview.SpreadsheetMetadata;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CompletableFuture;

/**
 * A utility class for displaying a user input form to collect spreadsheet metadata.
 *
 * <p>This class presents a modal dialog with input fields for the spreadsheet's name and creator.
 * The user can submit the form to complete the metadata entry or cancel the operation. The result
 * is returned asynchronously via a {@link CompletableFuture}.</p>
 */
public class SpreadsheetMetadataInputForm {

    /**
     * Displays a modal form for the user to enter spreadsheet metadata.
     *
     * <p>The form consists of two input fields: one for the spreadsheet's name and another for the
     * creator's name. The dialog also includes "OK" and "Cancel" buttons. When the user clicks "OK",
     * the input is validated to ensure both fields are filled. If valid, the metadata is returned
     * as a {@link SpreadsheetMetadata} instance via a {@link CompletableFuture}. Clicking "Cancel"
     * closes the dialog without completing the future.</p>
     *
     * @return a {@link CompletableFuture} that completes with the entered {@link SpreadsheetMetadata}
     *         or remains incomplete if the dialog is closed without valid input.
     */
    public static CompletableFuture<SpreadsheetMetadata> askUserToEnterMetadata() {
        CompletableFuture<SpreadsheetMetadata> metadataFuture = new CompletableFuture<>();

        JDialog dialog = new JDialog();
        dialog.setTitle("Enter Spreadsheet Details");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(300, 200);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JTextField nameField = new JTextField();
        JTextField creatorField = new JTextField();

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Creator:"));
        inputPanel.add(creatorField);

        JPanel buttonPanel = getButtonPanel(nameField, creatorField, dialog, metadataFuture);

        dialog.add(inputPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);

        return metadataFuture;
    }

    /**
     * Creates a button panel with "OK" and "Cancel" buttons.
     *
     * <p>The "OK" button validates the input and, if valid, completes the {@link CompletableFuture}
     * with the entered {@link SpreadsheetMetadata}. If the input is invalid, an error message is
     * shown. The "Cancel" button closes the dialog without completing the future.</p>
     *
     * @param nameField the input field for the spreadsheet's name.
     * @param creatorField the input field for the spreadsheet's creator.
     * @param dialog the parent dialog to be closed after interaction.
     * @param metadataFuture the {@link CompletableFuture} to be completed with the user's input.
     * @return a {@link JPanel} containing the "OK" and "Cancel" buttons.
     */
    private static JPanel getButtonPanel(
            JTextField nameField,
            JTextField creatorField,
            JDialog dialog,
            CompletableFuture<SpreadsheetMetadata> metadataFuture
    ) {
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener((e) -> {
            String name = nameField.getText().trim();
            String creator = creatorField.getText().trim();

            if (name.isEmpty() || creator.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Both fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                dialog.dispose();
                metadataFuture.complete(new SpreadsheetMetadata("", name, creator));
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        return buttonPanel;
    }
}
