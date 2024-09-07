package com.sanyavertolet.interview.files;

import com.sanyavertolet.interview.data.manager.DataManager;
import com.sanyavertolet.interview.exceptions.files.FileReadException;
import com.sanyavertolet.interview.exceptions.files.FileWriteException;
import com.sanyavertolet.interview.files.adapters.FileAdapter;
import com.sanyavertolet.interview.files.adapters.JacksonSerializationFileAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * An implementation of the {@code FileManager} interface that determines the appropriate file adapter based on the file extension.
 * This class supports saving and loading files with different formats by delegating the operations to the corresponding file adapter.
 */
public class ExtensionBasedFileManager implements FileManager {
    private final static Logger logger = LoggerFactory.getLogger(ExtensionBasedFileManager.class);

    /**
     * Saves the data managed by the {@code DataManager} to the specified file.
     * The file type is determined based on the file extension, and the corresponding {@code FileAdapter} is used for saving.
     *
     * @param file the file to which data will be saved.
     * @param manager the {@code DataManager} instance managing the data to be saved.
     * @throws FileWriteException if an error occurs during the file writing process.
     */
    @Override
    public void save(File file, DataManager manager) throws FileWriteException {
        FileAdapter adapter = getCorrespondingFileAdapter(file);
        logger.info("Saving file: {}", file.getName());
        adapter.save(file, manager);
    }

    /**
     * Loads data from the specified file into the {@code DataManager}.
     * The file type is determined based on the file extension, and the corresponding {@code FileAdapter} is used for loading.
     *
     * @param file the file from which data will be loaded.
     * @param manager the {@code DataManager} instance where the loaded data will be managed.
     * @throws FileReadException if an error occurs during the file reading process.
     */
    @Override
    public void load(File file, DataManager manager) throws FileReadException {
        FileAdapter adapter = getCorrespondingFileAdapter(file);
        manager.clearData();
        logger.info("Loading file: {}", file.getName());
        adapter.load(file, manager);
    }

    /**
     * Determines the appropriate {@code FileAdapter} based on the file's extension.
     * This method maps the file type to the corresponding file adapter.
     *
     * @param file the file whose type will be determined.
     * @return the {@code FileAdapter} corresponding to the file's type.
     */
    private FileAdapter getCorrespondingFileAdapter(File file) {
        return getCorrespondingFileAdapter(FileType.of(file.getName()));
    }

    /**
     * Determines the appropriate {@code FileAdapter} based on the file type.
     * This method is used internally to get the corresponding file adapter.
     *
     * @param fileType the type of file for which the file adapter is to be determined.
     * @return the {@code FileAdapter} corresponding to the file type.
     * @throws IllegalStateException if the file type is not supported by the current implementation.
     */
    private FileAdapter getCorrespondingFileAdapter(FileType fileType) {
        // todo: support more FileAdapters
        return switch (fileType) {
            case SHEETS -> new JacksonSerializationFileAdapter();
        };
    }
}
