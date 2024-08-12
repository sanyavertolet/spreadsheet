package com.sanyavertolet.interview.data.accessor;

import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.exceptions.DataAccessException;
import com.sanyavertolet.interview.math.CellReference;

public interface DataAccessor {
    Double getDoubleCellValue(CellReference cellReference) throws DataAccessException;

    Data getData(CellReference reference);

    boolean hasCell(CellReference cellReference);
}
