package com.sanyavertolet.interview.data.container;

import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.data.TextData;
import com.sanyavertolet.interview.math.CellReference;

import java.util.*;

public class DataContainer {
    private final Map<CellReference, Data> container;
    private int maxRow;
    private int maxCol;

    public DataContainer(int row, int col) {
        maxRow = row;
        maxCol = col;
        this.container = new HashMap<>();
    }

    public Data get(CellReference cellReference) {
        return container.getOrDefault(cellReference, new TextData(""));
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
        return maxRow + 1;
    }

    public int getColumnCount() {
        return maxCol + 1;
    }

    public boolean hasData(CellReference reference) {
        return container.containsKey(reference);
    }

    public Data getOrEmpty(CellReference reference) {
        return container.computeIfAbsent(reference, (ref) -> new TextData(""));
    }
}
