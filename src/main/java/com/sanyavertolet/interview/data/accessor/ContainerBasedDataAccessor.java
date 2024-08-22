package com.sanyavertolet.interview.data.accessor;

import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.data.container.DataContainer;
import com.sanyavertolet.interview.math.CellReference;

/**
 * A simple implementation of the {@link DataAccessor} interface that acts as a wrapper around a {@link DataContainer}.
 * This class delegates the data retrieval operations to the underlying {@code DataContainer}.
 */
public class ContainerBasedDataAccessor implements DataAccessor {
    private final DataContainer container;

    /**
     * Constructs a new {@code ContainerBasedDataAccessor} that wraps the given {@link DataContainer}.
     *
     * @param dataContainer the {@link DataContainer} to be wrapped by this accessor.
     */
    public ContainerBasedDataAccessor(DataContainer dataContainer) {
        container = dataContainer;
    }

    /**
     * Retrieves the {@link Data} associated with the specified {@link CellReference} from the wrapped {@link DataContainer}.
     *
     * @param reference the reference to the cell.
     * @return the {@link Data} at the cell identified by the provided reference.
     */
    @Override
    public Data getData(CellReference reference) {
        return container.get(reference);
    }
}
