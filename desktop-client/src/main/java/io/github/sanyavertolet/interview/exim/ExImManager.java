package io.github.sanyavertolet.interview.exim;

import io.github.sanyavertolet.interview.data.manager.DataManager;
import io.github.sanyavertolet.interview.exceptions.io.ImportException;
import io.github.sanyavertolet.interview.exceptions.io.ExportException;

/**
 * Interface defining methods for exporting and importing data using a given {@code DataManager}.
 *
 * <p>The implementing classes can handle different types of data sources and destinations,
 * such as files, databases, network connections, or in-memory structures. By throwing
 * {@link ExportException} and {@link ImportException}, they indicate problems that occur
 * during the write (export) or read (import) operations, respectively.</p>
 */
public interface ExImManager {

    /**
     * Exports data using the provided {@link DataManager}.
     *
     * <p>This method writes data out to some external destination, which might be a file,
     * network endpoint, or another form of output. If any issue arises that prevents data
     * from being successfully written, a {@link ExportException} is thrown.</p>
     *
     * @param manager the {@code DataManager} instance providing the data to be exported.
     * @throws ExportException if an error occurs during the data export operation.
     */
    void exportData(DataManager manager) throws ExportException;

    /**
     * Imports data using the provided {@link DataManager}.
     *
     * <p>This method reads data in from an external source, which could be a file,
     * network endpoint, or other form of input. In case of read failures, it throws
     * a {@link ImportException}, allowing callers to handle input-related issues.</p>
     *
     * @param manager the {@code DataManager} instance into which the data is to be imported.
     * @throws ImportException if an error occurs during the data import operation.
     */
    void importData(DataManager manager) throws ImportException;
}
