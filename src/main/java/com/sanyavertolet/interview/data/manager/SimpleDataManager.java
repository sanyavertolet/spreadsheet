package com.sanyavertolet.interview.data.manager;

import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.data.DoubleData;
import com.sanyavertolet.interview.data.ExpressionData;
import com.sanyavertolet.interview.data.TextData;
import com.sanyavertolet.interview.data.factory.DataFactory;
import com.sanyavertolet.interview.data.factory.SimpleDataFactory;
import com.sanyavertolet.interview.exceptions.DataAccessException;
import com.sanyavertolet.interview.math.CellReference;

public class SimpleDataManager implements DataManager {
    private final DataFactory dataFactory = new SimpleDataFactory(this);
    private final Data[][] data;

    public SimpleDataManager(int rows, int columns) {
        data = new Data[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                data[i][j] = new TextData("");
            }
        }
    }

    @Override
    public void setData(int row, int column, String text) {
        data[row][column] = dataFactory.create(text);
    }

    @Override
    public Data getData(int row, int column) {
        return data[row][column];
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return data.length > 0 ? data[0].length + 1 : 0;
    }

    @Override
    public Double getDoubleCellValue(CellReference cellReference) throws DataAccessException {
        if (!hasCell(cellReference)) {
            throw new DataAccessException("Cell " + cellReference + " is not present");
        }
        Data data = this.data[cellReference.row()][cellReference.column()];
        if (data instanceof DoubleData doubleData) {
            return doubleData.getValue();
        } else if (data instanceof ExpressionData expressionData) {
            return expressionData.getValue();
        }

        throw new DataAccessException("Cell " + cellReference + " has insufficient data type: " + data.getClass().getSimpleName());
    }

    @Override
    public boolean hasCell(CellReference cellReference) {
        return cellReference.row() < data.length && cellReference.column() < data[cellReference.row()].length;
    }
}
