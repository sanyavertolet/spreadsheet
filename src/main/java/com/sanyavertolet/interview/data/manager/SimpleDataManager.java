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
import com.sanyavertolet.interview.math.expressions.evaluator.ExpressionEvaluator;
import com.sanyavertolet.interview.math.expressions.evaluator.SimpleExpressionEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * A simple implementation of the {@link DataManager} interface that manages data in a table-like structure.
 * The {@code SimpleDataManager} uses a {@link DataContainer} for storing data, a {@link DataFactory} for
 * creating data instances, and a {@link DataWatcher} for monitoring and reacting to changes in the data.
 */
public class SimpleDataManager implements DataManager {
    private final static Logger logger = LoggerFactory.getLogger(SimpleDataManager.class);
    private final DataContainer container;
    private final DataAccessor accessor;
    private final DataFactory dataFactory;
    private final DataWatcher dataWatcher;
    private final BiConsumer<Integer, Integer> fireTableCellUpdated;
    private final Runnable fireTableDataChanged;

    /**
     * Constructs a {@code SimpleDataManager} with the specified number of rows and columns.
     * Initializes the necessary components for data management, including data creation, access, and watching.
     *
     * @param rows the number of rows in the data structure.
     * @param columns the number of columns in the data structure.
     * @param fireTableCellUpdated callback that is used to notify the table that the cell data is updated.
     * @param fireTableDataChanged callback that is used to notify the table that all the table data is updated.
     */
    public SimpleDataManager(
            int rows,
            int columns,
            BiConsumer<Integer, Integer> fireTableCellUpdated,
            Runnable fireTableDataChanged
    ) {
        this.fireTableCellUpdated = fireTableCellUpdated;
        this.fireTableDataChanged = fireTableDataChanged;
        container = new DataContainer(rows, columns);
        accessor = new ContainerBasedDataAccessor(container);

        ExpressionEvaluator expressionEvaluator = new SimpleExpressionEvaluator(accessor);

        dataFactory = new SimpleDataFactory(expressionEvaluator);
        dataWatcher = new SimpleDataWatcher(accessor, expressionEvaluator, fireTableCellUpdated);
    }

    /**
     * Sets the data for a specified cell identified by its row and column indices.
     * The data is created using the {@link DataFactory} and stored in the underlying {@link DataContainer}.
     * The {@link DataWatcher} is then notified of the update.
     *
     * @param row the row index of the cell.
     * @param column the column index of the cell.
     * @param text the text representation of the data to be set in the cell.
     */
    @Override
    public void setData(int row, int column, String text) {
        CellReference reference = reference(row, column);
        if (text.isEmpty()) {
            container.remove(reference);
            dataWatcher.clear(reference);
        } else {
            Data data = dataFactory.create(text);
            container.put(reference, data);
            dataWatcher.update(data, reference);
        }
        fireTableCellUpdated.accept(row, column);
    }

    /**
     * Retrieves the data stored in a specified cell identified by its row and column indices.
     *
     * @param row the row index of the cell.
     * @param column the column index of the cell.
     * @return the {@link Data} stored in the specified cell.
     */
    @Override
    public Data getData(int row, int column) {
        CellReference reference = reference(row, column);
        return accessor.getData(reference);
    }

    /**
     * Retrieves the number of rows in the data structure.
     *
     * @return the number of rows.
     */
    @Override
    public int getRowCount() {
        return container.getRowCount();
    }

    /**
     * Retrieves the number of columns in the data structure.
     *
     * @return the number of columns.
     */
    @Override
    public int getColumnCount() {
        return container.getColumnCount();
    }

    /**
     * Exports the data stored in the structure as a list of {@link CellReference.WithText} objects,
     * each containing the reference and the associated text.
     *
     * @return a list of {@link CellReference.WithText} representing the exported data.
     */
    @Override
    public List<CellReference.WithText> exportData() {
        return container.exportDataMap();
    }

    /**
     * Clears all data from the structure, effectively resetting it.
     * Also clears any data-related monitoring handled by the {@link DataWatcher}.
     */
    @Override
    public void clearData() {
        logger.info("Clearing data...");
        container.clearDataMap();
        dataWatcher.clearAll();
        fireTableDataChanged.run();
    }

    /**
     * Converts the specified row and column indices into a {@link CellReference}.
     * This method handles any exceptions that occur during the creation of the reference.
     *
     * @param row the row index.
     * @param column the column index.
     * @return the {@link CellReference} corresponding to the specified row and column.
     */
    private CellReference reference(int row, int column) {
        try {
            return CellReference.of(row, column);
        } catch (CellReferenceException exception) {
            throw new RuntimeException("Internal error: access to cell that does not exist.", exception);
        }
    }
}
