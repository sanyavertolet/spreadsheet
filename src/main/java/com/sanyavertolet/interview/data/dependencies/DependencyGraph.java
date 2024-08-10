package com.sanyavertolet.interview.data.dependencies;

import com.sanyavertolet.interview.exceptions.DataDependencyException;
import com.sanyavertolet.interview.math.CellReference;

import java.util.List;
import java.util.Set;

public interface DependencyGraph {
    void addDependency(CellReference dependsOn, CellReference dependantCell) throws DataDependencyException;

    Set<CellReference> getDependants(CellReference cellReference);

    List<CellReference> getUpdateList(CellReference cellReference) throws DataDependencyException;

    void clearDependencies(CellReference cellReference);
}
