package com.sanyavertolet.interview.data.watcher;

import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.math.CellReference;

/**
 * Interface for monitoring and responding to changes in data within a table or similar structure.
 * Implementations of the {@code DataWatcher} interface can perform specific actions when data is updated
 * or when the data needs to be cleared.
 */
public interface DataWatcher {

    /**
     * Updates the state of the data watcher based on the new data and its corresponding cell reference.
     * This method is typically called when a cell's data is modified.
     *
     * @param data      the updated {@link Data} object.
     * @param reference the {@link CellReference} indicating the location of the updated data.
     */
    void update(Data data, CellReference reference);

    /**
     * Clears all tracked data or states in the data watcher. This method is typically called
     * when all data in the structure is being reset or cleared.
     */
    void clear();
}
