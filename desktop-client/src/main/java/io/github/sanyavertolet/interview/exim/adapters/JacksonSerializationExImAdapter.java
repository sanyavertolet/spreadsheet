package io.github.sanyavertolet.interview.exim.adapters;

import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.sanyavertolet.interview.data.manager.DataManager;
import io.github.sanyavertolet.interview.exceptions.io.ExportException;
import io.github.sanyavertolet.interview.exceptions.io.ImportException;
import io.github.sanyavertolet.interview.math.CellReference;
import io.github.sanyavertolet.interview.serialization.JsonConfig;

import java.util.List;

import java.io.IOException;

/**
 * A JSON-based implementation of the {@link ExImAdapter} interface that uses Jackson
 * for serialization and deserialization.
 *
 * <p>This class converts the data managed by a {@link DataManager} into a JSON string
 * via {@link #exportData(DataManager)}, and also takes a JSON string and applies it
 * back into a {@code DataManager} via {@link #importData(DataManager, String)}.</p>
 *
 * <p>Internally, it uses a Jackson {@link ObjectMapper} configured by
 * {@link JsonConfig#createObjectMapper()} to handle the conversion to and from JSON.
 * The data is represented as a list of {@link CellReference.WithText} objects, each
 * capturing the location (row and column) and the textual content of a cell.</p>
 */
public class JacksonSerializationExImAdapter implements ExImAdapter {

    /**
     * Serializes the {@code DataManager}'s data into a JSON string.
     *
     * <p>This method extracts the data from the given {@code DataManager} as a list of
     * {@link CellReference.WithText} instances, and uses Jackson to convert that list
     * into a JSON string. If any I/O error occurs during the serialization process,
     * an {@link ExportException} is thrown.</p>
     *
     * @param manager the {@code DataManager} containing the data to be serialized.
     * @return a JSON-formatted string representing the manager's data.
     * @throws ExportException if the data cannot be serialized.
     */
    @Override
    public String exportData(DataManager manager) throws ExportException {
        ObjectMapper objectMapper = JsonConfig.createObjectMapper();
        List<CellReference.WithText> referencedDataList = manager.exportData();
        try {
            return objectMapper.writeValueAsString(referencedDataList);
        } catch (IOException exception) {
            throw new ExportException("Could not serialize data.", exception);
        }
    }

    /**
     * Deserializes the given JSON string into data that is applied to the provided {@code DataManager}.
     *
     * <p>This method reads the input JSON string and converts it into a list of
     * {@link CellReference.WithText} objects. Each of these objects is then used to
     * set the corresponding cell data in the {@code DataManager}. If the JSON string
     * is malformed, contains incompatible data, or another I/O error occurs during
     * deserialization, an {@link ImportException} is thrown.</p>
     *
     * @param manager  the {@code DataManager} instance to be populated with the deserialized data.
     * @param textData a JSON-formatted string representing the manager's data.
     * @throws ImportException if the data cannot be deserialized or applied to the manager.
     */
    @Override
    public void importData(DataManager manager, String textData) throws ImportException {
        ObjectMapper objectMapper = JsonConfig.createObjectMapper();
        TypeReference<List<CellReference.WithText>> typeRef = new TypeReference<>() {};
        try {
            List<CellReference.WithText> referencedDataList = objectMapper.readValue(textData, typeRef);
            for (CellReference.WithText referencedData : referencedDataList) {
                manager.setData(
                        referencedData.reference().row(),
                        referencedData.reference().column(),
                        referencedData.text()
                );
            }
        } catch (IOException exception) {
            throw new ImportException("Could not deserialize data: " + textData, exception);
        }
    }
}
