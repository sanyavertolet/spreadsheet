package com.sanyavertolet.interview.cells.dependencies;

import com.sanyavertolet.interview.exceptions.CellDependencyException;
import com.sanyavertolet.interview.math.CellReference;

import java.util.*;

public class SimpleDependencyGraph implements DependencyGraph {
    private final Map<CellReference, Set<CellReference>> dependencies = new HashMap<>();
    private final Map<CellReference, Set<CellReference>> dependants = new HashMap<>();

    @Override
    public void addDependency(CellReference dependsOn, CellReference dependantCell) throws CellDependencyException {
        if (dependantCell.identifier().equals(dependsOn.identifier())) {
            throw new CellDependencyException("Cell cannot depend on itself: " + dependantCell.identifier());
        }
        dependencies.computeIfAbsent(dependsOn, k -> new HashSet<>()).add(dependantCell);
        dependants.computeIfAbsent(dependsOn, k -> new HashSet<>()).add(dependantCell);
    }

    @Override
    public Set<CellReference> getDependants(CellReference cellReference) {
        return dependants.getOrDefault(cellReference, Collections.emptySet());
    }

    @Override
    public List<CellReference> getUpdateList(CellReference cellReference) throws CellDependencyException {
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


    private void topologicalSort(CellReference current, Set<CellReference> visited, Set<CellReference> visiting, List<CellReference> sorted) throws CellDependencyException {
        if (visited.contains(current)) {
            return;
        }
        if (visiting.contains(current)) {
            throw new CellDependencyException("Cycle detected");
        }
        sorted.add(current);

        visiting.add(current);
        for (CellReference cellReference : getDependants(current)) {
            topologicalSort(cellReference, visited, visiting, sorted);
        }
        visiting.remove(current);
        visited.add(current);
    }
}
