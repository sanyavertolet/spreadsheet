package com.sanyavertolet.interview.data.watcher;

import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.data.dependencies.DependencyGraph;
import com.sanyavertolet.interview.data.dependencies.TopologicallySortedDependencyGraph;
import com.sanyavertolet.interview.data.accessor.DataAccessor;
import com.sanyavertolet.interview.exceptions.data.DataDependencyException;
import com.sanyavertolet.interview.exceptions.data.DataSelfReferenceException;
import com.sanyavertolet.interview.math.CellReference;
import com.sanyavertolet.interview.math.expressions.evaluator.ExpressionEvaluator;

import java.util.List;
import java.util.Set;

public class SimpleDataWatcher implements DataWatcher {
    private final DataAccessor dataAccessor;
    private final ExpressionEvaluator expressionEvaluator;
    private final DependencyGraph dependencyGraph = new TopologicallySortedDependencyGraph();

    public SimpleDataWatcher(final DataAccessor dataAccessor, final ExpressionEvaluator expressionEvaluator) {
        this.dataAccessor = dataAccessor;
        this.expressionEvaluator = expressionEvaluator;
    }

    @Override
    public void update(Data data, CellReference reference) {
        dependencyGraph.clearDependencies(reference);

        try {
            addNewDependenciesIfNeeded(data, reference);
        } catch (DataSelfReferenceException exception) {
            markCurrentAndNextCellsAsError(reference);
            return;
        }

        try {
            recalculateInValidOrder(reference);
        } catch (DataDependencyException e) {
            markCyclicCellsAsError();
        }
    }

    private void recalculateInValidOrder(CellReference reference) throws DataDependencyException {
        List<CellReference> updateList = dependencyGraph.getUpdateList(reference);
        for (CellReference updateCellReference : updateList) {
            Data data = dataAccessor.getData(updateCellReference);
            data.recalculateValue(expressionEvaluator);
        }
    }

    private void addNewDependenciesIfNeeded(Data cellData, CellReference reference) throws DataSelfReferenceException {
        if (cellData.getExpressionTree() == null) {
            return;
        }
        for (CellReference before : cellData.getExpressionTree().getCellReferences()) {
            dependencyGraph.addDependency(before, reference);
        }
    }

    private void markCellAsError(CellReference reference) {
        dataAccessor.getData(reference).markAsError();
    }

    private void markCurrentAndNextCellsAsError(CellReference reference) {
        try {
            for (CellReference cellReference : dependencyGraph.getUpdateList(reference)) {
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
