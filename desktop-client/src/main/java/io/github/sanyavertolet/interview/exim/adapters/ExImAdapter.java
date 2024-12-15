package io.github.sanyavertolet.interview.exim.adapters;

import io.github.sanyavertolet.interview.data.manager.DataManager;
import io.github.sanyavertolet.interview.exceptions.io.ExportException;
import io.github.sanyavertolet.interview.exceptions.io.ImportException;

/**
 * Adapter interface responsible for serializing and deserializing data using a {@code DataManager}.
 *
 * <p>The implementing class should handle the process of taking a {@code DataManager} instance,
 * extracting its data, and converting it into a serialized string representation with
 * {@link #exportData(DataManager)}. Conversely, it should be able to take a serialized
 * string input and use it to restore or update a {@code DataManager}'s state
 * via {@link #importData(DataManager, String)}.</p>
 */
public interface ExImAdapter {

    /**
     * Serializes the state of the given {@code DataManager} into a string representation.
     *
     * <p>Implementations should extract all relevant data from the provided {@code DataManager},
     * convert it into a suitable format (e.g., JSON, XML, CSV), and then return that
     * serialized string. If any issue arises during serialization (such as incompatible data
     * types or I/O failures), an {@link ExportException} should be thrown.</p>
     *
     * @param data the {@code DataManager} whose data will be serialized.
     * @return a string containing the serialized representation of the {@code DataManager}'s data.
     * @throws ExportException if the data cannot be properly exported.
     */
    String exportData(DataManager data) throws ExportException;

    /**
     * Deserializes the given string and updates or populates the {@code DataManager} with its data.
     *
     * <p>This method takes a serialized string (created by {@link #exportData(DataManager)})
     * and interprets it to restore the internal state of the provided {@code DataManager}.
     * If the text data is malformed, incompatible, or cannot be properly parsed,
     * an {@link ImportException} should be thrown.</p>
     *
     * @param data     the {@code DataManager} instance to be populated or updated from the given string.
     * @param textData the serialized string representation of the data.
     * @throws ImportException if the input string cannot be properly imported.
     */
    void importData(DataManager data, String textData) throws ImportException;
}
