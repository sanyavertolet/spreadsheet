package com.sanyavertolet.interview.data.accessor;

import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.data.container.DataContainer;
import com.sanyavertolet.interview.math.CellReference;

public class ContainerBasedDataAccessor implements DataAccessor {
    private final DataContainer container;

    public ContainerBasedDataAccessor(DataContainer dataContainer) {
        container = dataContainer;
    }

    @Override
    public Data getData(CellReference reference) {
        return container.get(reference);
    }
}
