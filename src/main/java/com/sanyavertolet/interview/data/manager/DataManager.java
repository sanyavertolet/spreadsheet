package com.sanyavertolet.interview.data.manager;

import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.data.container.DataExporter;

public interface DataManager {
    void setData(int row, int column, String text);

    int getRowCount();

    Data getData(int row, int column);

    int getColumnCount();

    DataExporter getDataExporter();
}
