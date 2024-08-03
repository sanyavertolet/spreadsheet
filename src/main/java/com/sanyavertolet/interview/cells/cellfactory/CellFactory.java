package com.sanyavertolet.interview.cells.cellfactory;

import com.sanyavertolet.interview.cells.Cell;

public interface CellFactory {
    Cell create(String cellText);
}
