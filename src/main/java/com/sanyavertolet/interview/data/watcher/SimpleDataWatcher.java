package com.sanyavertolet.interview.data.watcher;

import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.data.dependencies.DependencyGraph;
import com.sanyavertolet.interview.data.dependencies.TopologicallySortedDependencyGraph;
import com.sanyavertolet.interview.data.accessor.DataAccessor;
import com.sanyavertolet.interview.exceptions.data.DataDependencyException;
import com.sanyavertolet.interview.exceptions.data.DataSelfReferenceException;
import com.sanyavertolet.interview.math.CellReference;
import com.sanyavertolet.interview.math.expressions.evaluator.ExpressionEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * A simple implementation of the {@link DataWatcher} interface that monitors and manages dependencies
 * between cells in a table-like structure. The {@code SimpleDataWatcher} uses a {@link DependencyGraph}
 * to track cell dependencies and ensures that data is recalculated in the correct order when updates occur.
 */
public class SimpleDataWatcher implements DataWatcher {
    private final static Logger logger = LoggerFactory.getLogger(SimpleDataWatcher.class);
    private final BiConsumer<Integer, Integer> fireTableCellUpdated;
    private final DataAccessor dataAccessor;
    private final ExpressionEvaluator expressionEvaluator;
    private final DependencyGraph dependencyGraph = new TopologicallySortedDependencyGraph();

    /**
     * Constructs a {@code SimpleDataWatcher} with the specified {@link DataAccessor} and {@link ExpressionEvaluator}.
     *
     * @param dataAccessor         the data accessor used to retrieve and update cell data.
     * @param expressionEvaluator  the evaluator used to recalculate cell values based on expressions.
     * @param fireTableCellUpdated callback that is used to update the table.
     */
    public SimpleDataWatcher(final DataAccessor dataAccessor, final ExpressionEvaluator expressionEvaluator, final BiConsumer<Integer, Integer> fireTableCellUpdated) {
        this.fireTableCellUpdated = fireTableCellUpdated;
        this.dataAccessor = dataAccessor;
        this.expressionEvaluator = expressionEvaluator;
    }

    /**
     * Updates the state of the data watcher with the new data and its corresponding cell reference.
     * This method clears existing dependencies for the updated cell, adds new dependencies if needed,
     * and then recalculates the affected cells in the correct order.
     *
     * @param data      the updated {@link Data} object.
     * @param reference the {@link CellReference} indicating the location of the updated data.
     */
    @Override
    public void update(Data data, CellReference reference) {
        logger.debug("Updating data with reference {}", reference);
        dependencyGraph.clearDependencies(reference);
        dependencyGraph.clearFailedCellReferences();

        try {
            addNewDependenciesIfNeeded(data, reference);
        } catch (DataSelfReferenceException exception) {
            logger.error(exception.getMessage(), exception);
            markCurrentAndNextCellsAsError(reference, data);
            return;
        }

        try {
            recalculateInValidOrder(reference);
        } catch (DataDependencyException e) {
            logger.error(e.getMessage(), e);
            markCyclicCellsAsError();
        }
    }

    /**
     * Clears all tracked dependencies and states in the data watcher.
     * This method is typically called when all data in the structure is being reset or cleared.
     */
    @Override
    public void clearAll() {
        logger.debug("Clearing dependencies...");
        dependencyGraph.clearAll();
    }

    /**
     * Clears all tracked dependencies and states in the data watcher.
     * This method is typically called when all data in the structure is being reset or cleared.
     */
    @Override
    public void clear(CellReference reference) {
        dependencyGraph.clearDependencies(reference);
    }

    /**
     * Recalculates the values of cells in the correct order, starting from the specified cell reference.
     *
     * @param reference the reference to the cell that was updated.
     * @throws DataDependencyException if a cyclic dependency is detected during recalculation.
     */
    private void recalculateInValidOrder(CellReference reference) throws DataDependencyException {
        List<CellReference> updateList = dependencyGraph.getUpdateList(reference);
        for (CellReference updateCellReference : updateList) {
            Data data = dataAccessor.getData(updateCellReference);
            logger.debug("Recalculating cell {}", updateCellReference);
            data.recalculateValue(expressionEvaluator);
            fireTableCellUpdated.accept(updateCellReference.row(), updateCellReference.column());
        }
    }

    /**
     * Adds new dependencies for the specified cell data if the data contains an expression.
     *
     * @param cellData  the data for the cell.
     * @param reference the reference to the cell being updated.
     * @throws DataSelfReferenceException if a self-referencing dependency is detected.
     */
    private void addNewDependenciesIfNeeded(Data cellData, CellReference reference) throws DataSelfReferenceException {
        if (cellData.getExpressionTree() == null) {
            return;
        }
        logger.debug("Adding new dependency for cell {}", reference);
        for (CellReference before : cellData.getExpressionTree().getCellReferences()) {
            dependencyGraph.addDependency(before, reference);
        }
    }

    /**
     * Marks the specified cell as containing an error.
     *
     * @param reference the reference to the cell to be marked as an error.
     */
    private void markCellAsError(CellReference reference) {
        dataAccessor.getData(reference).markAsError();
    }

    /**
     * Marks the current cell and all dependent cells as containing errors due to a self-reference or cyclic dependency.
     *
     * @param currentReference the reference to the current cell.
     * @param currentData      the data for the current cell.
     */
    private void markCurrentAndNextCellsAsError(CellReference currentReference, Data currentData) {
        currentData.markAsError();
        try {
            for (CellReference cellReference : dependencyGraph.getUpdateList(currentReference)) {
                markCellAsError(cellReference);
            }
        } catch (DataDependencyException exception) {
            markCyclicCellsAsError();
        }
    }

    /**
     * Marks all cells involved in a cyclic dependency as containing errors.
     */
    private void markCyclicCellsAsError() {
        Set<CellReference> failedCells = dependencyGraph.getFailedCellReferences();
        for (CellReference failedCellReference : failedCells) {
            markCellAsError(failedCellReference);
        }
    }
}
