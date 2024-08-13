package com.sanyavertolet.interview.files.adapters;

import com.sanyavertolet.interview.data.manager.DataManager;
import com.sanyavertolet.interview.exceptions.files.FileReadException;
import com.sanyavertolet.interview.exceptions.files.FileWriteException;

import java.io.File;

public interface FileAdapter {
    void save(File file, DataManager data) throws FileWriteException;

    void load(File file, DataManager data) throws FileReadException;
}
