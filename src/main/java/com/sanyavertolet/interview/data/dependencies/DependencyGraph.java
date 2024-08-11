package com.sanyavertolet.interview.data.dependencies;

import com.sanyavertolet.interview.exceptions.DataDependencyException;
import com.sanyavertolet.interview.exceptions.DataSelfReferenceException;
import com.sanyavertolet.interview.math.CellReference;

import java.util.List;
import java.util.Set;

public interface DependencyGraph {
    void addDependency(CellReference before, CellReference after) throws DataSelfReferenceException;

    void clearDependencies(CellReference cellReference);

    List<CellReference> getUpdateList(CellReference cellReference) throws DataDependencyException;

    Set<CellReference> getFailedCellReferences();
}
