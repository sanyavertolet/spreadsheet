package com.sanyavertolet.interview.ui.files;

import com.sanyavertolet.interview.files.FileType;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A custom file chooser for the spreadsheet application, extending {@link JFileChooser}.
 * The {@code SpreadsheetFileChooser} class is configured to filter and allow only supported spreadsheet file types.
 */
public class SpreadsheetFileChooser extends JFileChooser {

    /**
     * Constructs a {@code SpreadsheetFileChooser} with a file filter applied to show only supported spreadsheet file types.
     * The supported file types are determined based on the available {@link FileType} enum values.
     */
    public SpreadsheetFileChooser() {
        List<String> supportedFileExtensions = Arrays.stream(FileType.values())
                .map(it -> it.name().toLowerCase())
                .toList();

        String description = supportedFileExtensions.stream()
                .map(it -> "." + it)
                .collect(Collectors.joining(",  ", "", " files"));

        FileNameExtensionFilter filter = new FileNameExtensionFilter(description, supportedFileExtensions.toArray(new String[0]));
        setFileFilter(filter);

        setMultiSelectionEnabled(false);
    }
}
