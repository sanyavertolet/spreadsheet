package com.sanyavertolet.interview.files;

import com.sanyavertolet.interview.data.manager.DataManager;
import com.sanyavertolet.interview.exceptions.files.FileReadException;
import com.sanyavertolet.interview.exceptions.files.FileWriteException;
import com.sanyavertolet.interview.files.adapters.FileAdapter;
import com.sanyavertolet.interview.files.adapters.JacksonSerializationFileAdapter;

import java.io.File;

public class ExtensionBasedFileManager implements FileManager {
    @Override
    public void save(File file, DataManager manager) throws FileWriteException {
        FileAdapter adapter = getCorrespondingFileAdapter(file.getName());
        adapter.save(file, manager);
    }

    @Override
    public void load(File file, DataManager manager) throws FileReadException {
        FileAdapter adapter = getCorrespondingFileAdapter(file.getName());
        adapter.load(file, manager);
    }

    private FileAdapter getCorrespondingFileAdapter(String fileName) {
        // todo: support more FileAdapters
        return switch (getFileExtension(fileName)) {
            case "sheets" -> new JacksonSerializationFileAdapter();
            default -> throw new IllegalStateException("Not implemented yet");
        };
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
