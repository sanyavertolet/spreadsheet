package com.sanyavertolet.interview.data.dependencies;

import com.sanyavertolet.interview.exceptions.data.DataDependencyException;
import com.sanyavertolet.interview.exceptions.data.DataSelfReferenceException;
import com.sanyavertolet.interview.math.CellReference;

import java.util.*;

/**
 * Implementation of the {@link DependencyGraph} interface that manages dependencies between {@link CellReference}
 * instances using a topological sorting approach. This implementation ensures that updates are processed in
 * a dependency-consistent order and detects cyclic dependencies.
 */
public class TopologicallySortedDependencyGraph implements DependencyGraph {
    private final Map<CellReference, Set<CellReference>> previous = new HashMap<>();
    private final Map<CellReference, Set<CellReference>> next = new HashMap<>();
    private final Set<CellReference> failedCellReferences = new HashSet<>();

    /**
     * Adds a dependency between two {@link CellReference} instances, ensuring that the cell identified by {@code before}
     * must be processed before the cell identified by {@code after}. Throws an exception if a cell is found to depend on itself.
     *
     * @param before the reference to the cell that must be processed first.
     * @param after the reference to the cell that depends on {@code before}.
     * @throws DataSelfReferenceException if {@code before} and {@code after} refer to the same cell, creating a self-dependency.
     */
    @Override
    public void addDependency(CellReference before, CellReference after) throws DataSelfReferenceException {
        if (before.identifier().equals(after.identifier())) {
            throw new DataSelfReferenceException(after);
        }
        previous.computeIfAbsent(after, k -> new HashSet<>()).add(before);
        next.computeIfAbsent(before, k -> new HashSet<>()).add(after);
    }

    /**
     * Clears all dependencies for the specified {@link CellReference}. This removes the cell from any dependency chains.
     *
     * @param reference the reference to the cell whose dependencies should be cleared.
     */
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    @Override
    public void clearDependencies(CellReference reference) {
        Set<CellReference> cellDependencies = previous.remove(reference);
        if (cellDependencies != null) {
            for (CellReference dependsOn : cellDependencies) {
                next.getOrDefault(dependsOn, new HashSet<>()).remove(reference);
            }
        }
    }

    /**
     * Clears all dependencies and resets the entire dependency graph, including any recorded failed cells.
     */
    @Override
    public void clearAll() {
        previous.clear();
        next.clear();
        failedCellReferences.clear();
    }

    /**
     * Retrieves a list of {@link CellReference} instances that need to be updated when the specified cell is modified,
     * sorted in topological order. If a cyclic dependency is detected, an exception is thrown.
     *
     * @param reference the reference to the cell that was modified.
     * @return a list of {@link CellReference} instances that should be updated, in topological order.
     * @throws DataDependencyException if a cyclic dependency is detected in the graph.
     */
    @Override
    public List<CellReference> getUpdateList(CellReference reference) throws DataDependencyException {
        List<CellReference> sorted = new ArrayList<>();
        Set<CellReference> visited = new HashSet<>();
        Set<CellReference> visiting = new HashSet<>();
        Stack<CellReference> visitingStack = new Stack<>();

        topologicalSort(reference, visited, visiting, visitingStack, sorted);
        sorted.remove(reference);
        return sorted;
    }

    /**
     * Retrieves a set of {@link CellReference} instances that have failed due to dependency issues, such as cycles.
     *
     * @return a set of {@link CellReference} instances that have failed.
     */
    @Override
    public Set<CellReference> getFailedCellReferences() {
        return failedCellReferences;
    }

    /**
     * Clears a set of {@link CellReference} instances that have failed due tp dependency issues, such as cycles.
     */
    @Override
    public void clearFailedCellReferences() {
        failedCellReferences.clear();
    }

    /**
     * Performs a topological sort starting from the specified {@link CellReference}. This method is used internally
     * to generate a sorted list of cells that should be updated. It also detects cyclic dependencies.
     *
     * @param current the current cell being processed.
     * @param visited the set of cells that have already been processed.
     * @param visiting the set of cells that are currently being visited.
     * @param visitingStack the stack of cells that are currently being visited, used for cycle detection.
     * @param sorted the list to which sorted cells are added.
     * @throws DataDependencyException if a cyclic dependency is detected.
     */
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

    /**
     * Updates the set of failed cell references when a cycle is detected during topological sorting.
     *
     * @param visitingStack the stack of cells that are currently being visited.
     */
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

    /**
     * Retrieves the set of cells that are directly dependent on the specified {@link CellReference}.
     *
     * @param cellReference the reference to the cell whose dependents are to be retrieved.
     * @return a set of {@link CellReference} instances that depend on the specified cell.
     */
    private Set<CellReference> getNext(CellReference cellReference) {
        return next.getOrDefault(cellReference, Collections.emptySet());
    }
}
