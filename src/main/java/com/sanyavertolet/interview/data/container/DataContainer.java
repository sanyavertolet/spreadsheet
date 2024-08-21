package com.sanyavertolet.interview.data.container;

import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.math.CellReference;

import java.util.*;

public class DataContainer implements DataExporter {
    private final Map<CellReference, Data> container;
    private int maxRow;
    private int maxCol;

    public DataContainer(int row, int col) {
        maxRow = row;
        maxCol = col + 1;
        this.container = new HashMap<>();
    }

    public Data get(CellReference cellReference) {
        return container.getOrDefault(cellReference, new Data(""));
    }

    @SuppressWarnings("UnusedReturnValue")
    public Data put(CellReference cellReference, Data data) {
        maxRow = Math.max(cellReference.row(), maxRow);
        maxCol = Math.max(cellReference.column(), maxCol);
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

    @Override
    public List<CellReference.WithText> exportDataMap() {
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
        return dataList;
    }

    @Override
    public void clearDataMap() {
        container.clear();
    }
}
