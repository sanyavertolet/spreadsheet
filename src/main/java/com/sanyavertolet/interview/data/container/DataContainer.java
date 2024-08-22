package com.sanyavertolet.interview.data.container;

import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.math.CellReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class DataContainer {
    private final Logger logger = LoggerFactory.getLogger(DataContainer.class);
    private final Map<CellReference, Data> container;
    private int maxRow;
    private int maxCol;

    public DataContainer(int row, int col) {
        maxRow = row;
        maxCol = col + 1;
        logger.debug("Creating container for {} x {} table", row, col);
        this.container = new HashMap<>();
    }

    public Data get(CellReference cellReference) {
        return container.getOrDefault(cellReference, new Data(""));
    }

    @SuppressWarnings("UnusedReturnValue")
    public Data put(CellReference cellReference, Data data) {
        maxRow = Math.max(cellReference.row(), maxRow);
        maxCol = Math.max(cellReference.column(), maxCol);
        logger.trace("Updated maxRow: {} maxCol: {}", maxRow, maxCol);
        logger.trace("Added data {} for reference {}", data, cellReference);
        return container.put(cellReference, data);
    }

    public int size() {
        return container.size();
    }

    public int getRowCount() {
        return maxRow;
    }

    public int getColumnCount() {
        return maxCol;
    }

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

    public void clearDataMap() {
        logger.debug("Clearing data map...");
        container.clear();
    }
}
