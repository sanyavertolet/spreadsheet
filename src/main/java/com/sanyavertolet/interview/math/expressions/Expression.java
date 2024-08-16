package com.sanyavertolet.interview.math.expressions;

import com.sanyavertolet.interview.math.CellReference;

import java.util.List;

public abstract class Expression {
    public abstract String prettyPrint(int shift);

    public abstract List<CellReference> getCellReferences();
}
