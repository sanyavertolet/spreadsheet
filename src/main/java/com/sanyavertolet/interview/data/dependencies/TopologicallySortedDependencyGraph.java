package com.sanyavertolet.interview.data.dependencies;

import com.sanyavertolet.interview.exceptions.data.DataDependencyException;
import com.sanyavertolet.interview.exceptions.data.DataSelfReferenceException;
import com.sanyavertolet.interview.math.CellReference;

import java.util.*;

public class TopologicallySortedDependencyGraph implements DependencyGraph {
    private final Map<CellReference, Set<CellReference>> previous = new HashMap<>();
    private final Map<CellReference, Set<CellReference>> next = new HashMap<>();
    private final Set<CellReference> failedCellReferences = new HashSet<>();

    @Override
    public void addDependency(CellReference before, CellReference after) throws DataSelfReferenceException {
        if (before.identifier().equals(after.identifier())) {
            throw new DataSelfReferenceException("Cell cannot depend on itself: " + after);
        }
        previous.computeIfAbsent(after, k -> new HashSet<>()).add(before);
        next.computeIfAbsent(before, k -> new HashSet<>()).add(after);
    }

    @Override
    public void clearDependencies(CellReference reference) {
        Set<CellReference> cellDependencies = previous.remove(reference);
        if (cellDependencies != null) {
            for (CellReference dependsOn : cellDependencies) {
                previous.getOrDefault(dependsOn, new HashSet<>()).remove(reference);
            }
        }
    }

    @Override
    public List<CellReference> getUpdateList(CellReference cellReference) throws DataDependencyException {
        List<CellReference> sorted = new ArrayList<>();
        Set<CellReference> visited = new HashSet<>();
        Set<CellReference> visiting = new HashSet<>();
        Stack<CellReference> visitingStack = new Stack<>();

        topologicalSort(cellReference, visited, visiting, visitingStack, sorted);

        return sorted;
    }

    @Override
    public Set<CellReference> getFailedCellReferences() {
        return failedCellReferences;
    }

    private void topologicalSort(
            CellReference current,
            Set<CellReference> visited,
            Set<CellReference> visiting,
            Stack<CellReference> visitingStack,
            List<CellReference> sorted
    ) throws DataDependencyException {
        if (visited.contains(current)) {
            return;
        }
        visitingStack.push(current);
        if (visiting.contains(current)) {
            updateFailedCellReferences(visitingStack);
            throw new DataDependencyException("Cycle detected");
        }

        visiting.add(current);
        for (CellReference cellReference : getNext(current)) {
            topologicalSort(cellReference, visited, visiting, visitingStack, sorted);
        }
        visiting.remove(current);
        visitingStack.pop();
        visited.add(current);
        sorted.add(0, current);
    }

    private void updateFailedCellReferences(Stack<CellReference> visitingStack) {
        failedCellReferences.clear();
        while (!visitingStack.isEmpty()) {
            CellReference reference = visitingStack.pop();
            if (failedCellReferences.contains(reference)) {
                return;
            }
            failedCellReferences.add(reference);
        }
    }

    private Set<CellReference> getNext(CellReference cellReference) {
        return next.getOrDefault(cellReference, Collections.emptySet());
    }
}
