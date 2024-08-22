package com.sanyavertolet.interview.ui.menu.file;

import com.sanyavertolet.interview.data.manager.DataManager;
import com.sanyavertolet.interview.exceptions.files.FileReadException;
import com.sanyavertolet.interview.exceptions.files.FileWriteException;
import com.sanyavertolet.interview.files.ExtensionBasedFileManager;
import com.sanyavertolet.interview.files.FileManager;
import com.sanyavertolet.interview.ui.files.SpreadsheetFileChooser;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * A custom file menu for the spreadsheet application, extending {@link JMenu}.
 * The {@code FileMenu} class provides options to save, open, and clear spreadsheet data using a {@link DataManager}.
 */
public class FileMenu extends JMenu {
    private final FileManager fileManager = new ExtensionBasedFileManager();
    private final JFileChooser fileChooser = new SpreadsheetFileChooser();

    /**
     * Constructs a {@code FileMenu} with the provided {@link DataManager} for managing file operations.
     * The menu includes options to save the current spreadsheet, open a saved spreadsheet, and clear the current data.
     *
     * @param dataManager the data manager used to manage the spreadsheet's data, passed to file operations.
     */
    public FileMenu(DataManager dataManager) {
        super("File");

        ActionListener clearActionListener = e -> dataManager.clearData();

        ActionListener saveActionListener = e -> {
            int result = fileChooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    fileManager.save(file, dataManager);
                } catch (FileWriteException exception) {
                    JOptionPane.showMessageDialog(new JFrame(), exception.getMessage(), "Export error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        ActionListener loadActionListener = e -> {
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    fileManager.load(file, dataManager);
                } catch (FileReadException exception) {
                    JOptionPane.showMessageDialog(new JFrame(), exception.getMessage(), "Import error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem clearMenuItem = new JMenuItem("Clear");

        saveMenuItem.addActionListener(saveActionListener);
        openMenuItem.addActionListener(loadActionListener);
        clearMenuItem.addActionListener(clearActionListener);

        add(saveMenuItem);
        add(openMenuItem);
        add(clearMenuItem);
    }
}
