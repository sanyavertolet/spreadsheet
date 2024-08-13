package com.sanyavertolet.interview.data.container;

import com.sanyavertolet.interview.math.CellReference;

import java.util.List;

public interface DataExporter {
    List<CellReference.WithText> exportDataMap();

    void clearDataMap();
}
