package com.sanyavertolet.interview.files;

import com.sanyavertolet.interview.data.manager.DataManager;
import com.sanyavertolet.interview.exceptions.files.FileReadException;
import com.sanyavertolet.interview.exceptions.files.FileWriteException;

import java.io.File;

/**
 * Interface for managing file operations related to data persistence.
 * Defines methods for saving and loading data to and from files.
 */
public interface FileManager {

    /**
     * Saves the data managed by the {@code DataManager} to the specified file.
     * The file format and the method of saving are determined by the file type.
     *
     * @param file the file to which data will be saved.
     * @param manager the {@code DataManager} instance managing the data to be saved.
     * @throws FileWriteException if an error occurs during the file writing process.
     */
    void save(File file, DataManager manager) throws FileWriteException;

    /**
     * Loads data from the specified file into the {@code DataManager}.
     * The file format and the method of loading are determined by the file type.
     *
     * @param file the file from which data will be loaded.
     * @param manager the {@code DataManager} instance where the loaded data will be managed.
     * @throws FileReadException if an error occurs during the file reading process.
     */
    void load(File file, DataManager manager) throws FileReadException;
}
