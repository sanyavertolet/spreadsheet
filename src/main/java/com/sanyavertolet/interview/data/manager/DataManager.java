package com.sanyavertolet.interview.data.manager;

import com.sanyavertolet.interview.data.Data;

public interface DataManager {
    void setData(int row, int column, String text);

    int getRowCount();

    Data getData(int row, int column);

    int getColumnCount();
}
