package com.sanyavertolet.interview.ui;

import com.sanyavertolet.interview.data.Cell;

import javax.swing.*;
import java.awt.*;

public class DebugPanel extends JPanel {
    private JLabel coordinatesLabel;
    private JLabel contentLabel;
    private JLabel typeLabel;
    private JTextArea treeTextArea;
    private Cell selectedCell;

    public DebugPanel() {
        setLayout(new GridLayout(4, 1)); // 4 rows, 1 column

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

    public void setSelectedCell(Cell selectedCell, int row, int col) {
        this.selectedCell = selectedCell;
        updateDebugInfo(row, col);
    }

    private void updateDebugInfo(int row, int col) {
        if (selectedCell != null) {
            coordinatesLabel.setText("Coordinates: (" + row + ", " + col + ")");
            contentLabel.setText("Content: " + selectedCell.getValue().asString());
            typeLabel.setText("Type: " + selectedCell.getClass().getSimpleName());

//            if (selectedCell instanceof ExpressionCell) {
//                treeTextArea.setText("Expression Tree: \n" + getPrettyPrintedTree((ExpressionCell) selectedCell));
//            } else {
//                treeTextArea.setText("Expression Tree: \n");
//            }
        } else {
            coordinatesLabel.setText("Coordinates: ");
            contentLabel.setText("Content: ");
            typeLabel.setText("Type: ");
            treeTextArea.setText("Expression Tree: \n");
        }
    }
}
