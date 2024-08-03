package com.sanyavertolet.interview.ui;

import com.sanyavertolet.interview.cells.Cell;
import com.sanyavertolet.interview.cells.ExpressionCell;

import javax.swing.*;
import java.awt.*;

public class DebugPanel extends JPanel {
    private final JLabel coordinatesLabel;
    private final JLabel contentLabel;
    private final JLabel typeLabel;
    private final JTextArea treeTextArea;
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
            coordinatesLabel.setText("Coordinates: " + colIndexToLetter(col) + (row + 1));
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
    
    private String colIndexToLetter(int col) {
        StringBuilder sb = new StringBuilder();
        while (col > 0) {
            sb.append((char) ('A' + col % 26));
            col /= 26;
        }
        String result = sb.reverse().toString();
        return result.isEmpty() ? "A" : result;
    }
}
