package com.sanyavertolet.interview.ui.files;

import com.sanyavertolet.interview.files.FileType;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SpreadsheetFileChooser extends JFileChooser {
    public SpreadsheetFileChooser() {
        List<String> supportedFileExtensions = Arrays.stream(FileType.values()).map(it -> it.name().toLowerCase()).toList();

        String description = supportedFileExtensions.stream().map(it -> "." + it).collect(Collectors.joining(",  ", "", " files"));

        FileNameExtensionFilter filter = new FileNameExtensionFilter(description, supportedFileExtensions.toArray(new String[0]));
        setFileFilter(filter);

        setMultiSelectionEnabled(false);;
    }
}
