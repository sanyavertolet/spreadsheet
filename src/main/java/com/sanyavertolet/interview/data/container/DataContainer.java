package com.sanyavertolet.interview.data.container;

import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.math.CellReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * The {@code DataContainer} class manages a table-like structure for storing and retrieving data
 * associated with specific {@link CellReference} instances. It also tracks the maximum row and
 * column sizes dynamically as data is added.
 */
public class DataContainer {
    private final Logger logger = LoggerFactory.getLogger(DataContainer.class);
    private final Map<CellReference, Data> container;
    private int maxRow;
    private int maxCol;

    /**
     * Constructs a {@code DataContainer} with specified initial row and column sizes.
     *
     * @param row the initial maximum number of rows in the container.
     * @param col the initial maximum number of columns in the container.
     */
    public DataContainer(int row, int col) {
        maxRow = row;
        maxCol = col + 1;
        logger.debug("Creating container for {} x {} table", row, col);
        this.container = new HashMap<>();
    }

    /**
     * Retrieves the {@code Data} associated with the specified {@link CellReference}.
     * If no data exists for the provided reference, an empty {@code Data} object is returned.
     *
     * @param cellReference the reference of the cell to retrieve data from.
     * @return the {@code Data} associated with the given {@link CellReference}, or an empty {@code Data} object if none exists.
     */
    public Data get(CellReference cellReference) {
        return container.getOrDefault(cellReference, new Data(""));
    }

    /**
     * Stores the specified {@code Data} in the container, associated with the provided {@link CellReference}.
     * This method also updates the maximum row and column indices based on the provided cell reference.
     *
     * @param cellReference the reference of the cell to store data in.
     * @param data the {@code Data} to be stored.
     * @return the previous {@code Data} associated with the cell reference, or {@code null} if there was none.
     */
    @SuppressWarnings("UnusedReturnValue")
    public Data put(CellReference cellReference, Data data) {
        maxRow = Math.max(cellReference.row(), maxRow);
        maxCol = Math.max(cellReference.column(), maxCol);
        logger.trace("Updated maxRow: {} maxCol: {}", maxRow, maxCol);
        logger.trace("Added data {} for reference {}", data, cellReference);
        return container.put(cellReference, data);
    }

    /**
     * Removes the {@code Data} associated with the provided {@link CellReference} from the container.
     *
     * @param cellReference the reference of the cell to remove data from.
     * @return the removed {@code Data} associated with the cell reference, or {@code null} if there was none.
     */
    @SuppressWarnings("UnusedReturnValue")
    public Data remove(CellReference cellReference) {
        return container.remove(cellReference);
    }

    /**
     * Returns the number of entries in the container.
     *
     * @return the size of the container.
     */
    public int size() {
        return container.size();
    }

    /**
     * Returns the current maximum row index in the container.
     *
     * @return the maximum row index.
     */
    public int getRowCount() {
        return maxRow;
    }

    /**
     * Returns the current maximum column index in the container.
     *
     * @return the maximum column index.
     */
    public int getColumnCount() {
        return maxCol;
    }

    /**
     * Exports the data map as a list of {@link CellReference.WithText} objects, which contain
     * the cell reference and associated text. The export process prioritizes cells with primitive
     * data over those with expressions.
     *
     * @return a list of {@link CellReference.WithText} representing the exported data.
     */
    public List<CellReference.WithText> exportDataMap() {
        logger.debug("Exporting data map...");
        List<CellReference.WithText> dataList = new ArrayList<>();
        List<CellReference.WithText> primitiveDataList = new ArrayList<>();
        for (Map.Entry<CellReference, Data> entry : container.entrySet()) {
            CellReference.WithText referencedData = new CellReference.WithText(entry.getKey(), entry.getValue().getText());
            if (entry.getValue().getExpressionTree() == null) {
                dataList.add(referencedData);
            } else {
                primitiveDataList.add(referencedData);
            }
        }
        dataList.addAll(primitiveDataList);
        logger.debug("Exported {} records", dataList.size());
        return dataList;
    }

    /**
     * Clears all data from the container.
     */
    public void clearDataMap() {
        logger.debug("Clearing data map...");
        container.clear();
    }
}
