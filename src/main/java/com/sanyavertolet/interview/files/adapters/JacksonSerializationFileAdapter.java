package com.sanyavertolet.interview.files.adapters;

import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanyavertolet.interview.data.manager.DataManager;
import com.sanyavertolet.interview.exceptions.files.FileReadException;
import com.sanyavertolet.interview.exceptions.files.FileWriteException;
import com.sanyavertolet.interview.math.CellReference;
import com.sanyavertolet.interview.serialization.JsonConfig;

import java.util.List;

import java.io.File;
import java.io.IOException;

/**
 * Implementation of {@code FileAdapter} for handling file operations using Jackson serialization.
 * Supports saving and loading data to and from files in JSON format.
 */
public class JacksonSerializationFileAdapter implements FileAdapter {

    /**
     * Saves the data managed by the {@code DataManager} to the specified file in JSON format.
     *
     * @param file the file to which data will be saved.
     * @param manager the {@code DataManager} instance managing the data to be saved.
     * @throws FileWriteException if an error occurs during the file writing process.
     */
    @Override
    public void save(File file, DataManager manager) throws FileWriteException {
        ObjectMapper objectMapper = JsonConfig.createObjectMapper();
        List<CellReference.WithText> referencedDataList = manager.exportData();
        try {
            objectMapper.writeValue(file, referencedDataList);
        } catch (IOException exception) {
            throw new FileWriteException("Could not save to file " + file.getAbsolutePath(), exception);
        }
    }

    /**
     * Loads data from the specified file in JSON format into the {@code DataManager}.
     *
     * @param file the file from which data will be loaded.
     * @param manager the {@code DataManager} instance where the loaded data will be managed.
     * @throws FileReadException if an error occurs during the file reading process.
     */
    @Override
    public void load(File file, DataManager manager) throws FileReadException {
        ObjectMapper objectMapper = JsonConfig.createObjectMapper();
        TypeReference<List<CellReference.WithText>> typeRef = new TypeReference<>() {};
        try {
            List<CellReference.WithText> referencedDataList = objectMapper.readValue(file, typeRef);
            for (CellReference.WithText referencedData : referencedDataList) {
                manager.setData(referencedData.reference().row(), referencedData.reference().column(), referencedData.text());
            }
        } catch (IOException exception) {
            throw new FileReadException("Could not read from file " + file.getAbsolutePath(), exception);
        }
    }
}
