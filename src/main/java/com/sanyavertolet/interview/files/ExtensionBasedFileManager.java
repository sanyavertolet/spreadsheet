package com.sanyavertolet.interview.files;

import com.sanyavertolet.interview.data.manager.DataManager;
import com.sanyavertolet.interview.exceptions.files.FileReadException;
import com.sanyavertolet.interview.exceptions.files.FileWriteException;
import com.sanyavertolet.interview.files.adapters.FileAdapter;
import com.sanyavertolet.interview.files.adapters.JacksonSerializationFileAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ExtensionBasedFileManager implements FileManager {
    private final Logger logger = LoggerFactory.getLogger(ExtensionBasedFileManager.class);
    @Override
    public void save(File file, DataManager manager) throws FileWriteException {
        FileAdapter adapter = getCorrespondingFileAdapter(file);
        logger.info("Saving file: {}", file.getName());
        adapter.save(file, manager);
    }

    @Override
    public void load(File file, DataManager manager) throws FileReadException {
        FileAdapter adapter = getCorrespondingFileAdapter(file);
        manager.clearData();
        logger.info("Loading file: {}", file.getName());
        adapter.load(file, manager);
    }

    private FileAdapter getCorrespondingFileAdapter(File file) {
        return getCorrespondingFileAdapter(FileType.of(file.getName()));
    }

    private FileAdapter getCorrespondingFileAdapter(FileType fileType) {
        // todo: support more FileAdapters
        return switch (fileType) {
            case SHEETS -> new JacksonSerializationFileAdapter();
            case XLSX -> throw new IllegalStateException("Not implemented yet");
        };
    }
}
