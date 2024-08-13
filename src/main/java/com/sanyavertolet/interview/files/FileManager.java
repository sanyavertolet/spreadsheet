package com.sanyavertolet.interview.files;

import com.sanyavertolet.interview.data.manager.DataManager;
import com.sanyavertolet.interview.exceptions.files.FileReadException;
import com.sanyavertolet.interview.exceptions.files.FileWriteException;

import java.io.File;

public interface FileManager {
    void save(File file, DataManager manager) throws FileWriteException;

    void load(File file, DataManager manager) throws FileReadException;
}
