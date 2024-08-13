package com.sanyavertolet.interview.files;

import java.io.File;

public enum FileType {
    SHEETS,
    XLSX,
    ;

    public static FileType of(String fileName) {
        String fileExtension = getExtension(fileName);
        return switch (fileExtension) {
            case "sheets" -> SHEETS;
            case "xlsx" -> XLSX;
            default -> throw new IllegalArgumentException("Invalid fileExtension: " + fileExtension);
        };
    }

    public static FileType of(File file) {
        return of(file.getName());
    }

    public static String getExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        return index == -1 ? "" : fileName.substring(index + 1);
    }
}
