package com.sanyavertolet.interview.data.dependencies;

import com.sanyavertolet.interview.exceptions.DataDependencyException;
import com.sanyavertolet.interview.math.CellReference;

import java.util.*;

public class SimpleDependencyGraph implements DependencyGraph {
    private final Map<CellReference, Set<CellReference>> dependencies = new HashMap<>();
    private final Map<CellReference, Set<CellReference>> dependants = new HashMap<>();

    @Override
    public void addDependency(CellReference dependsOn, CellReference dependantCell) throws DataDependencyException {
        if (dependantCell.identifier().equals(dependsOn.identifier())) {
            throw new DataDependencyException("Cell cannot depend on itself: " + dependantCell.identifier());
        }
        dependencies.computeIfAbsent(dependsOn, k -> new HashSet<>()).add(dependantCell);
        dependants.computeIfAbsent(dependsOn, k -> new HashSet<>()).add(dependantCell);
    }

    @Override
    public Set<CellReference> getDependants(CellReference cellReference) {
        return dependants.getOrDefault(cellReference, Collections.emptySet());
    }

    @Override
    public List<CellReference> getUpdateList(CellReference cellReference) throws DataDependencyException {
        List<CellReference> sorted = new ArrayList<>();
        Set<CellReference> visited = new HashSet<>();
        Set<CellReference> visiting = new HashSet<>();

        topologicalSort(cellReference, visited, visiting, sorted);

        return sorted;
    }

    @Override
    public void clearDependencies(CellReference cellReference) {
        Set<CellReference> cellDependencies = dependencies.remove(cellReference);
        if (cellDependencies != null) {
            for (CellReference dependant : cellDependencies) {
                dependants.get(dependant).remove(cellReference);
            }
        }
    }


    private void topologicalSort(CellReference current, Set<CellReference> visited, Set<CellReference> visiting, List<CellReference> sorted) throws DataDependencyException {
        if (visited.contains(current)) {
            return;
        }
        if (visiting.contains(current)) {
            throw new DataDependencyException("Cycle detected");
        }

        visiting.add(current);
        for (CellReference cellReference : getDependants(current)) {
            topologicalSort(cellReference, visited, visiting, sorted);
        }
        visiting.remove(current);
        visited.add(current);
        sorted.add(0, current);
    }
}
