package io.github.sanyavertolet.interview.modals;

import io.github.sanyavertolet.interview.SpreadsheetMetadata;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * A utility class for presenting options to the user when saving a spreadsheet.
 *
 * <p>This class provides a dialog that asks the user whether they want to create a new spreadsheet
 * or override an existing one. Based on the user's choice, additional dialogs are shown to collect
 * or select the required metadata. The final result is returned asynchronously as a {@link CompletableFuture}.</p>
 */
public class SaveOptionDialog {
    /**
     * Asks the user whether to save to a new spreadsheet or override an existing one.
     *
     * <p>This method displays a {@link JOptionPane} dialog with three options:
     * "Create New Save", "Override Existing", and "Cancel". Depending on the user's choice:
     * <ul>
     *   <li>If "Create New Save" is selected, the method opens a form for the user to manually
     *       enter metadata.</li>
     *   <li>If "Override Existing" is selected, the method opens a dialog to select existing
     *       metadata from a provided list.</li>
     *   <li>If "Cancel" is selected, the returned {@link CompletableFuture} remains incomplete.</li>
     * </ul>
     * </p>
     *
     * @param spreadsheetMetadataList the list of existing metadata to choose from when overriding.
     * @return a {@link CompletableFuture} that completes with the selected or entered {@link SpreadsheetMetadata}.
     */
    public static CompletableFuture<SpreadsheetMetadata> askUserForMetadata(List<SpreadsheetMetadata> spreadsheetMetadataList) {
        String message = "Do you want to save to a new spreadsheet or override existing?";
        String title = "Save Options";
        String[] options = { "Create New Save", "Override Existing", "Cancel" };

        int choice = JOptionPane.showOptionDialog(
                null,
                message,
                title,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        return switch (choice) {
            case 0 -> getMetadataManually();
            case 1 -> getMetadataFromExisting(spreadsheetMetadataList);
            default -> new CompletableFuture<>(); // Cancel or close dialog
        };
    }

    /**
     * Opens a selection dialog for the user to choose existing spreadsheet metadata.
     *
     * <p>This method calls {@link SpreadsheetMetadataPicker#pick(List)} to allow the user to
     * select one of the provided metadata entries. The result is returned as a
     * {@link CompletableFuture}.</p>
     *
     * @param spreadsheetMetadataList the list of existing spreadsheet metadata.
     * @return a {@link CompletableFuture} that completes with the selected {@link SpreadsheetMetadata}.
     */
    private static CompletableFuture<SpreadsheetMetadata> getMetadataFromExisting(List<SpreadsheetMetadata> spreadsheetMetadataList) {
        return SpreadsheetMetadataPicker.pick(spreadsheetMetadataList);
    }

    /**
     * Opens a form for the user to manually enter new spreadsheet metadata.
     *
     * <p>This method calls {@link SpreadsheetMetadataInputForm#askUserToEnterMetadata()} to allow the user
     * to input the name and creator for the new spreadsheet. The result is returned as a
     * {@link CompletableFuture}.</p>
     *
     * @return a {@link CompletableFuture} that completes with the entered {@link SpreadsheetMetadata}.
     */
    private static CompletableFuture<SpreadsheetMetadata> getMetadataManually() {
        return SpreadsheetMetadataInputForm.askUserToEnterMetadata();
    }
}
