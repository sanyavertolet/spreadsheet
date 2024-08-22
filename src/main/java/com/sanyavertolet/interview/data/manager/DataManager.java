package com.sanyavertolet.interview.data.manager;

import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.math.CellReference;

import java.util.List;

public interface DataManager {
    void setData(int row, int column, String text);

    int getRowCount();

    Data getData(int row, int column);

    int getColumnCount();

    List<CellReference.WithText> exportData();

    void clearData();
}
