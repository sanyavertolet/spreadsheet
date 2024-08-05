package com.sanyavertolet.interview.ui;

import com.sanyavertolet.interview.cells.Cell;
import com.sanyavertolet.interview.cells.ExpressionCell;
import com.sanyavertolet.interview.math.CellReference;

import javax.swing.*;
import java.awt.*;

public class DebugPanel extends JPanel {
    private final JLabel coordinatesLabel;
    private final JLabel contentLabel;
    private final JLabel typeLabel;
    private final JTextArea treeTextArea;
    private Cell selectedCell;

    public DebugPanel() {
        setLayout(new GridLayout(4, 1));

        coordinatesLabel = new JLabel("Coordinates: ");
        contentLabel = new JLabel("Content: ");
        typeLabel = new JLabel("Type: ");
        treeTextArea = new JTextArea("Expression Tree: \n");
        treeTextArea.setEditable(false);

        add(coordinatesLabel);
        add(contentLabel);
        add(typeLabel);
        add(new JScrollPane(treeTextArea));
    }

    public void setSelectedCell(CellReference cellReference, Cell selectedCell) {
        this.selectedCell = selectedCell;
        updateDebugInfo(cellReference);
    }

    private void updateDebugInfo(CellReference cellReference) {
        if (selectedCell != null) {
            coordinatesLabel.setText("Coordinates: " + cellReference.identifier());
            contentLabel.setText("Content: " + selectedCell.getValueAsString());
            typeLabel.setText("Type: " + selectedCell.getClass().getSimpleName());

            if (selectedCell instanceof ExpressionCell) {
                treeTextArea.setText("Expression Tree: \n" + ((ExpressionCell) selectedCell).getPrettyPrintedExpressionTree());
            } else {
                treeTextArea.setText("Expression Tree: \n");
            }
        } else {
            coordinatesLabel.setText("Coordinates: ");
            contentLabel.setText("Content: ");
            typeLabel.setText("Type: ");
            treeTextArea.setText("Expression Tree: \n");
        }
    }
}
