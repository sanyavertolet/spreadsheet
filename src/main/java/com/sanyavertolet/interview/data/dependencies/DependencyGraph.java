package com.sanyavertolet.interview.data.dependencies;

import com.sanyavertolet.interview.exceptions.data.DataDependencyException;
import com.sanyavertolet.interview.exceptions.data.DataSelfReferenceException;
import com.sanyavertolet.interview.math.CellReference;

import java.util.List;
import java.util.Set;

/**
 * Interface for managing dependencies between {@link CellReference} instances in a data graph.
 * This interface allows the addition, retrieval, and clearing of dependencies, as well as
 * handling cycles and failed cell references within the graph.
 */
public interface DependencyGraph {

    /**
     * Adds a dependency between two {@link CellReference} instances. The dependency indicates that
     * changes to the cell identified by {@code before} may affect the cell identified by {@code after}.
     *
     * @param before the reference to the cell that other cells depend on.
     * @param after the reference to the cell that depends on {@code before}.
     * @throws DataSelfReferenceException if a cell is found to depend on itself.
     */
    void addDependency(CellReference before, CellReference after) throws DataSelfReferenceException;

    /**
     * Clears all dependencies related to the specified {@link CellReference}.
     *
     * @param cellReference the reference to the cell whose dependencies should be cleared.
     */
    void clearDependencies(CellReference cellReference);

    /**
     * Retrieves a list of {@link CellReference} instances that need to be updated when the specified
     * cell is modified. The order of the returned list represents the sequence in which updates should occur.
     *
     * @param cellReference the reference to the cell that was modified.
     * @return a list of {@link CellReference} instances that should be updated.
     * @throws DataDependencyException if a circular dependency or other error is detected in the graph.
     */
    List<CellReference> getUpdateList(CellReference cellReference) throws DataDependencyException;

    /**
     * Retrieves a set of {@link CellReference} instances that have failed due to dependency issues, such as cycles.
     *
     * @return a set of {@link CellReference} instances that have failed.
     */
    Set<CellReference> getFailedCellReferences();

    /**
     * Clears all dependencies and resets the entire dependency graph.
     */
    void clearAll();
}
