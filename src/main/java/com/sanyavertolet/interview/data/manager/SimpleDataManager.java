package com.sanyavertolet.interview.data.manager;

import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.data.accessor.ContainerBasedDataAccessor;
import com.sanyavertolet.interview.data.accessor.DataAccessor;
import com.sanyavertolet.interview.data.container.DataContainer;
import com.sanyavertolet.interview.data.factory.DataFactory;
import com.sanyavertolet.interview.data.factory.SimpleDataFactory;
import com.sanyavertolet.interview.data.watcher.DataWatcher;
import com.sanyavertolet.interview.data.watcher.SimpleDataWatcher;
import com.sanyavertolet.interview.exceptions.CellReferenceException;
import com.sanyavertolet.interview.math.CellReference;

public class SimpleDataManager implements DataManager {
    private final DataContainer container;
    private final DataAccessor accessor;
    private final DataFactory dataFactory;
    private final DataWatcher dataWatcher;

    public SimpleDataManager(int rows, int columns) {
        container = new DataContainer(rows, columns);
        accessor = new ContainerBasedDataAccessor(container);
        dataFactory = new SimpleDataFactory(accessor);
        dataWatcher = new SimpleDataWatcher(accessor);
    }

    @Override
    public void setData(int row, int column, String text) {
        CellReference reference = reference(row, column);

        Data data = dataFactory.create(text);
        container.put(reference, data);
        dataWatcher.update(data, reference);
    }

    @Override
    public Data getData(int row, int column) {
        CellReference reference = reference(row, column);
        return accessor.getData(reference);
    }

    @Override
    public int getRowCount() {
        return container.getRowCount();
    }

    @Override
    public int getColumnCount() {
        return container.getColumnCount();
    }

    private CellReference reference(int row, int column) {
        try {
            return CellReference.of(row, column);
        } catch (CellReferenceException exception) {
            throw new RuntimeException("Internal error: access to cell that does not exist.", exception);
        }
    }
}
