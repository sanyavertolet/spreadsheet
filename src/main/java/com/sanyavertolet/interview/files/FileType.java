package com.sanyavertolet.interview.files;

import java.io.File;

/**
 * Enum representing different file types used in the application.
 */
public enum FileType {
    /**
     * Represents a Sheets file type.
     */
    SHEETS,
    ;

    /**
     * Determines the {@code FileType} based on the file name.
     *
     * @param fileName the name of the file including its extension.
     * @return the {@code FileType} corresponding to the file extension.
     * @throws IllegalArgumentException if the file extension is not recognized.
     */
    public static FileType of(String fileName) {
        String fileExtension = getExtension(fileName);
        //noinspection SwitchStatementWithTooFewBranches
        return switch (fileExtension) {
            case "sheets" -> SHEETS;
            default -> throw new IllegalArgumentException("Invalid file extension: " + fileExtension);
        };
    }

    /**
     * Determines the {@code FileType} based on the file object.
     *
     * @param file the {@code File} object whose name will be used to determine the file type.
     * @return the {@code FileType} corresponding to the file extension.
     * @throws IllegalArgumentException if the file extension is not recognized.
     */
    public static FileType of(File file) {
        return of(file.getName());
    }

    /**
     * Extracts the file extension from the file name.
     *
     * @param fileName the name of the file including its extension.
     * @return the file extension, or an empty string if the file name does not have an extension.
     */
    public static String getExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        return index == -1 ? "" : fileName.substring(index + 1);
    }
}
