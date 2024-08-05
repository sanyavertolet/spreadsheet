package com.sanyavertolet.interview.cells.cellmanager;

import com.sanyavertolet.interview.exceptions.CellAccessException;
import com.sanyavertolet.interview.math.CellReference;

public interface CellAccessor {
    Double getDoubleCellValue(CellReference cellReference) throws CellAccessException;

    boolean hasCell(CellReference cellReference);
}
