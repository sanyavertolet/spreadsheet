package com.sanyavertolet.interview.data.accessor;

import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.data.DoubleData;
import com.sanyavertolet.interview.data.ExpressionData;
import com.sanyavertolet.interview.data.container.DataContainer;
import com.sanyavertolet.interview.exceptions.data.DataAccessException;
import com.sanyavertolet.interview.math.CellReference;

public class ContainerBasedDataAccessor implements DataAccessor {
    private final DataContainer container;

    public ContainerBasedDataAccessor(DataContainer dataContainer) {
        container = dataContainer;
    }

    @Override
    public Double getDoubleCellValue(CellReference cellReference) throws DataAccessException {
        if (!hasCell(cellReference)) {
            throw new DataAccessException("Cell " + cellReference + " is not present");
        }
        Data data = container.getOrEmpty(cellReference);
        if (data instanceof DoubleData doubleData) {
            return doubleData.getValue();
        } else if (data instanceof ExpressionData expressionData) {
            return expressionData.getValue();
        }

        throw new DataAccessException("Cell " + cellReference + " has insufficient data type: " + data.getClass().getSimpleName());
    }

    @Override
    public Data getData(CellReference reference) {
        return container.getOrEmpty(reference);
    }

    @Override
    public boolean hasCell(CellReference cellReference) {
        return container.hasData(cellReference);
    }
}
