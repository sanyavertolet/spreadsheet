package com.sanyavertolet.interview.data.accessor;

import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.math.CellReference;

public interface DataAccessor {
    Data getData(CellReference reference);
}
