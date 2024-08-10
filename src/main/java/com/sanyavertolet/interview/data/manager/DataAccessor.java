package com.sanyavertolet.interview.data.manager;

import com.sanyavertolet.interview.exceptions.DataAccessException;
import com.sanyavertolet.interview.math.CellReference;

public interface DataAccessor {
    Double getDoubleCellValue(CellReference cellReference) throws DataAccessException;

    boolean hasCell(CellReference cellReference);
}
