package io.github.sanyavertolet.interview.ui.menu.file;

import io.github.sanyavertolet.interview.data.manager.DataManager;
import io.github.sanyavertolet.interview.exceptions.io.ExportException;
import io.github.sanyavertolet.interview.exceptions.io.ImportException;
import io.github.sanyavertolet.interview.exim.ExImManager;
import io.github.sanyavertolet.interview.exim.FileChooserBasedExImManager;
import io.github.sanyavertolet.interview.exim.MongoCloudExImManager;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.Serial;

/**
 * A custom file menu for managing export and import operations in a spreadsheet application.
 *
 * <p>This {@link JMenu} subclass provides menu items for saving and loading data either locally or
 * in the cloud. It uses {@link ExImManager} implementations to handle the operations and shows
 * appropriate error messages if an exception occurs during the process.</p>
 */
public class FileMenu extends JMenu {
    @Serial
    private static final long serialVersionUID = 42L;

    private final ExImManager fileChooserBasedExImManager = new FileChooserBasedExImManager();
    private final ExImManager mongoCloudExImManager = new MongoCloudExImManager();

    /**
     * Constructs a new {@code FileMenu} with menu items for file operations.
     *
     * <p>The menu includes options to save and load data either locally or in the cloud, as well
     * as an option to clear all current data without saving. The provided {@link DataManager}
     * is used to manage the application's data during these operations.</p>
     *
     * @param dataManager the {@link DataManager} that manages the application's data.
     */
    public FileMenu(DataManager dataManager) {
        super("File");

        JMenuItem saveCloudMenuItem = new JMenuItem("Save to cloud");
        JMenuItem openCloudMenuItem = new JMenuItem("Load from cloud");
        JMenuItem saveLocallyMenuItem = new JMenuItem("Save to device");
        JMenuItem openLocallyMenuItem = new JMenuItem("Load from device");
        JMenuItem clearMenuItem = new JMenuItem("Close without saving");

        saveLocallyMenuItem.addActionListener(getExportActionListener(dataManager, true));
        openLocallyMenuItem.addActionListener(getImportActionListener(dataManager, true));
        saveCloudMenuItem.addActionListener(getExportActionListener(dataManager, false));
        openCloudMenuItem.addActionListener(getImportActionListener(dataManager, false));
        clearMenuItem.addActionListener(e -> dataManager.clearData());

        add(saveLocallyMenuItem);
        add(openLocallyMenuItem);
        add(saveCloudMenuItem);
        add(openCloudMenuItem);
        add(clearMenuItem);
    }

    /**
     * Creates an {@link ActionListener} for exporting data.
     *
     * <p>The action listener uses the appropriate {@link ExImManager} (local or cloud) to handle
     * the export operation. If an {@link ExportException} occurs, an error message is displayed
     * using a {@link JOptionPane} dialog.</p>
     *
     * @param dataManager the {@link DataManager} managing the application's data.
     * @param isLocal     {@code true} to use local export, {@code false} for cloud export.
     * @return an {@link ActionListener} for the export operation.
     */
    private ActionListener getExportActionListener(DataManager dataManager, boolean isLocal) {
        ExImManager exImManager = isLocal ? fileChooserBasedExImManager : mongoCloudExImManager;
        return (e) -> {
            try {
                exImManager.exportData(dataManager);
            } catch (ExportException exception) {
                JOptionPane.showMessageDialog(
                        new JFrame(),
                        exception.getMessage(),
                        "Export error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        };
    }

    /**
     * Creates an {@link ActionListener} for importing data.
     *
     * <p>The action listener uses the appropriate {@link ExImManager} (local or cloud) to handle
     * the import operation. If an {@link ImportException} occurs, an error message is displayed
     * using a {@link JOptionPane} dialog.</p>
     *
     * @param dataManager the {@link DataManager} managing the application's data.
     * @param isLocal     {@code true} to use local import, {@code false} for cloud import.
     * @return an {@link ActionListener} for the import operation.
     */
    private ActionListener getImportActionListener(DataManager dataManager, boolean isLocal) {
        ExImManager exImManager = isLocal ? fileChooserBasedExImManager : mongoCloudExImManager;
        return (e) -> {
            try {
                exImManager.importData(dataManager);
            } catch (ImportException exception) {
                JOptionPane.showMessageDialog(
                        new JFrame(),
                        exception.getMessage(),
                        "Import error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        };
    }
}
