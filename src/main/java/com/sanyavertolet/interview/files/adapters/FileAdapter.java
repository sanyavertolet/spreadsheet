package com.sanyavertolet.interview.files.adapters;

import com.sanyavertolet.interview.data.manager.DataManager;
import com.sanyavertolet.interview.exceptions.files.FileReadException;
import com.sanyavertolet.interview.exceptions.files.FileWriteException;

import java.io.File;

/**
 * Interface defining methods for saving and loading data to and from files.
 */
public interface FileAdapter {
    /**
     * Saves the provided data to the specified file.
     *
     * @param file the file to which data will be saved.
     * @param data the data to be saved.
     * @throws FileWriteException if an error occurs during the file writing process.
     */
    void save(File file, DataManager data) throws FileWriteException;

    /**
     * Loads data from the specified file into the provided DataManager.
     *
     * @param file the file from which data will be loaded.
     * @param data the DataManager instance where the loaded data will be managed.
     * @throws FileReadException if an error occurs during the file reading process.
     */
    void load(File file, DataManager data) throws FileReadException;
}
