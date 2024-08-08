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
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        coordinatesLabel = new JLabel("Coordinates: ");
        contentLabel = new JLabel("Content: ");
        typeLabel = new JLabel("Type: ");
        treeTextArea = new JTextArea("Expression Tree: \n");
        treeTextArea.setEditable(false);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.add(treeTextArea);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        add(coordinatesLabel, gbc);

        gbc.gridy = 1;
        add(contentLabel, gbc);

        gbc.gridy = 2;
        add(typeLabel, gbc);

        gbc.gridy = 3;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);
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
