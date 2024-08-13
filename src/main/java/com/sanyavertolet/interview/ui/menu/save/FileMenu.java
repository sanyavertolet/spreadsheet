package com.sanyavertolet.interview.ui.menu.save;

import com.sanyavertolet.interview.data.container.DataExporter;
import com.sanyavertolet.interview.data.manager.DataManager;
import com.sanyavertolet.interview.exceptions.files.FileReadException;
import com.sanyavertolet.interview.exceptions.files.FileWriteException;
import com.sanyavertolet.interview.files.ExtensionBasedFileManager;
import com.sanyavertolet.interview.files.FileManager;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;

public class FileMenu extends JMenu {
    private final FileManager fileManager = new ExtensionBasedFileManager();
    private final JFileChooser fileChooser = new JFileChooser();

    public FileMenu(DataManager dataManager) {
        super("File");

        DataExporter dataExporter = dataManager.getDataExporter();
        ActionListener newActionListener = e -> dataExporter.clearDataMap();

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

        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem openMenuItem = new JMenuItem("Open");

        newMenuItem.addActionListener(newActionListener);
        saveMenuItem.addActionListener(saveActionListener);
        openMenuItem.addActionListener(loadActionListener);

        add(newMenuItem);
        add(openMenuItem);
        add(saveMenuItem);
    }
}
