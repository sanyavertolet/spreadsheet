package com.sanyavertolet.interview.data.watcher;

import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.data.ExpressionData;
import com.sanyavertolet.interview.data.dependencies.DependencyGraph;
import com.sanyavertolet.interview.data.dependencies.SimpleDependencyGraph;
import com.sanyavertolet.interview.data.accessor.DataAccessor;
import com.sanyavertolet.interview.exceptions.DataDependencyException;
import com.sanyavertolet.interview.exceptions.DataSelfReferenceException;
import com.sanyavertolet.interview.math.CellReference;

import java.util.List;
import java.util.Set;

public class SimpleDataWatcher implements DataWatcher {
    private final DataAccessor dataAccessor;
    private final DependencyGraph dependencyGraph = new SimpleDependencyGraph();

    public SimpleDataWatcher(final DataAccessor dataAccessor) {
        this.dataAccessor = dataAccessor;
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
            if (data instanceof ExpressionData expressionData) {
                expressionData.updateValue();
            }
        }
    }

    private void addNewDependenciesIfNeeded(Data cellData, CellReference reference) throws DataSelfReferenceException {
        if (cellData instanceof ExpressionData expressionData) {
            for (CellReference before : expressionData.getCellReferences()) {
                dependencyGraph.addDependency(before, reference);
            }
        }
    }

    private void markCellAsError(CellReference reference) {
        Data data = dataAccessor.getData(reference);
        if (data instanceof ExpressionData expressionData) {
            expressionData.markAsError();
        }
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
