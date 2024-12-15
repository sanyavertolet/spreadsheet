package io.github.sanyavertolet.interview.exim;

import io.github.sanyavertolet.interview.data.manager.DataManager;
import io.github.sanyavertolet.interview.exceptions.io.ExportException;
import io.github.sanyavertolet.interview.exceptions.io.ImportException;
import io.github.sanyavertolet.interview.exim.adapters.ExImAdapter;
import io.github.sanyavertolet.interview.exim.adapters.JacksonSerializationExImAdapter;
import io.github.sanyavertolet.interview.ui.files.SpreadsheetFileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * An implementation of the {@link ExImManager} interface that uses a {@link JFileChooser}
 * for selecting files to export and import data.
 *
 * <p>This class enables users to choose files through a graphical file chooser dialog
 * for exporting data to or importing data from a file. It uses an {@link ExImAdapter}
 * to serialize and deserialize the data in the chosen file format.</p>
 *
 * <p>The class determines the appropriate {@link ExImAdapter} based on the file type,
 * which is inferred from the file name or extension. Supported file types are handled
 * via {@link FileType}.</p>
 */
public class FileChooserBasedExImManager implements ExImManager {

    private final static Logger logger = LoggerFactory.getLogger(FileChooserBasedExImManager.class);
    private final JFileChooser fileChooser = new SpreadsheetFileChooser();

    /**
     * Exports data from the given {@link DataManager} to a user-selected file.
     *
     * <p>The method opens a {@link JFileChooser} dialog to let the user select a file
     * for saving the data. It uses the appropriate {@link ExImAdapter} to serialize
     * the data into a format supported by the file type. The serialized data is then
     * written to the selected file.</p>
     *
     * @param manager the {@link DataManager} containing the data to be exported.
     * @throws ExportException if an error occurs during the serialization or file writing process.
     */
    @Override
    public void exportData(DataManager manager) throws ExportException {
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                ExImAdapter adapter = getCorrespondingExImAdapter(file);
                logger.info("Saving file: {}", file.getName());
                String textData = adapter.exportData(manager);
                Files.writeString(file.toPath(), textData);
            } catch (IOException exception) {
                throw new ExportException("Error while serialization", exception);
            }
        }
    }

    /**
     * Imports data into the given {@link DataManager} from a user-selected file.
     *
     * <p>The method opens a {@link JFileChooser} dialog to let the user select a file
     * for loading the data. It uses the appropriate {@link ExImAdapter} to deserialize
     * the data from the file and populate the {@link DataManager} with it. Before
     * importing, the existing data in the manager is cleared.</p>
     *
     * @param manager the {@link DataManager} instance to populate with the imported data.
     * @throws ImportException if an error occurs during file reading or deserialization.
     */
    @Override
    public void importData(DataManager manager) throws ImportException {
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                ExImAdapter adapter = getCorrespondingExImAdapter(file);
                manager.clearData();
                logger.info("Loading file: {}", file.getName());
                String textData = Files.readString(file.toPath());
                adapter.importData(manager, textData);
            } catch (IOException exception) {
                throw new ImportException("Error while deserialization", exception);
            }
        }
    }

    /**
     * Determines the appropriate {@link ExImAdapter} for a given file.
     *
     * <p>This method uses the {@link FileType#of(String)} method to infer the file type
     * based on the file name or extension and returns the corresponding adapter.</p>
     *
     * @param file the file whose type is used to determine the corresponding adapter.
     * @return an {@link ExImAdapter} suitable for the file type.
     */
    private ExImAdapter getCorrespondingExImAdapter(File file) {
        return getCorrespondingExImAdapter(FileType.of(file.getName()));
    }

    /**
     * Determines the appropriate {@link ExImAdapter} for a given file type.
     *
     * @param fileType the {@link FileType} indicating the type of file.
     * @return an {@link ExImAdapter} suitable for the given file type.
     */
    private ExImAdapter getCorrespondingExImAdapter(FileType fileType) {
        return switch (fileType) {
            case SHEETS -> new JacksonSerializationExImAdapter();
        };
    }
}
