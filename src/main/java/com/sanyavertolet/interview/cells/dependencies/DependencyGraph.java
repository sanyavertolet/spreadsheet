package com.sanyavertolet.interview.cells.dependencies;

import com.sanyavertolet.interview.exceptions.CellDependencyException;
import com.sanyavertolet.interview.math.CellReference;

import java.util.List;
import java.util.Set;

public interface DependencyGraph {
    void addDependency(CellReference dependsOn, CellReference dependantCell) throws CellDependencyException;

    Set<CellReference> getDependants(CellReference cellReference);

    List<CellReference> getUpdateList(CellReference cellReference) throws CellDependencyException;

    void clearDependencies(CellReference cellReference);
}
