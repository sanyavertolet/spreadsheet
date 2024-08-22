package com.sanyavertolet.interview.data.accessor;

import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.math.CellReference;

/**
 * Interface for accessing {@link Data} by a {@link CellReference}.
 */
public interface DataAccessor {

    /**
     * Retrieves the {@link Data} associated with the specified {@link CellReference}.
     *
     * @param reference the reference to the cell.
     * @return the {@link Data} at the cell identified by the provided reference.
     */
    Data getData(CellReference reference);
}
