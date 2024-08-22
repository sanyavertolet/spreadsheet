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

public class SimpleDataWatcher implements DataWatcher {
    private final Logger logger = LoggerFactory.getLogger(SimpleDataWatcher.class);
    private final DataAccessor dataAccessor;
    private final ExpressionEvaluator expressionEvaluator;
    private final DependencyGraph dependencyGraph = new TopologicallySortedDependencyGraph();

    public SimpleDataWatcher(final DataAccessor dataAccessor, final ExpressionEvaluator expressionEvaluator) {
        this.dataAccessor = dataAccessor;
        this.expressionEvaluator = expressionEvaluator;
    }

    @Override
    public void update(Data data, CellReference reference) {
        logger.debug("Updating data with reference {}", reference);
        dependencyGraph.clearDependencies(reference);

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

    @Override
    public void clear() {
        logger.debug("Clearing dependencies...");
        dependencyGraph.clearAll();
    }

    private void recalculateInValidOrder(CellReference reference) throws DataDependencyException {
        List<CellReference> updateList = dependencyGraph.getUpdateList(reference);
        for (CellReference updateCellReference : updateList) {
            Data data = dataAccessor.getData(updateCellReference);
            logger.debug("Recalculating cell {}", updateCellReference);
            data.recalculateValue(expressionEvaluator);
        }
    }

    private void addNewDependenciesIfNeeded(Data cellData, CellReference reference) throws DataSelfReferenceException {
        if (cellData.getExpressionTree() == null) {
            return;
        }
        logger.debug("Adding new dependency for cell {}", reference);
        for (CellReference before : cellData.getExpressionTree().getCellReferences()) {
            dependencyGraph.addDependency(before, reference);
        }
    }

    private void markCellAsError(CellReference reference) {
        dataAccessor.getData(reference).markAsError();
    }

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

    private void markCyclicCellsAsError() {
        Set<CellReference> failedCells = dependencyGraph.getFailedCellReferences();
        for (CellReference failedCellReference : failedCells) {
            markCellAsError(failedCellReference);
        }
    }
}
