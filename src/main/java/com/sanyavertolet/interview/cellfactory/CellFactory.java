package com.sanyavertolet.interview.cellfactory;

import com.sanyavertolet.interview.data.Cell;

public interface CellFactory {
    Cell create(String cellText);
}
