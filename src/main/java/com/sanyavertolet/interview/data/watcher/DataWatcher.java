package com.sanyavertolet.interview.data.watcher;

import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.math.CellReference;

public interface DataWatcher {
    void update(Data data, CellReference reference);
}
