package com.sanyavertolet.interview.files.adapters;

import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanyavertolet.interview.data.manager.DataManager;
import com.sanyavertolet.interview.exceptions.files.FileReadException;
import com.sanyavertolet.interview.exceptions.files.FileWriteException;
import com.sanyavertolet.interview.math.CellReference;
import com.sanyavertolet.interview.serialization.JsonConfig;

import java.io.*;
import java.util.List;

public class JacksonSerializationFileAdapter implements FileAdapter {

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
