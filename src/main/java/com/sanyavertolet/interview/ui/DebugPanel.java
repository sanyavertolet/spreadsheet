package com.sanyavertolet.interview.ui;

import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.data.value.Value;
import com.sanyavertolet.interview.math.CellReference;

import javax.swing.*;
import java.awt.*;

/**
 * A panel that displays debugging information about the currently selected cell in the spreadsheet.
 * The {@code DebugPanel} shows the cell's coordinates, content, value type, and optionally the expression tree.
 */
public class DebugPanel extends JPanel {
    private final JLabel coordinatesLabel;
    private final JLabel contentLabel;
    private final JLabel typeLabel;
    private final JTextArea treeTextArea;
    private Data selectedData;

    /**
     * Constructs a {@code DebugPanel} and sets up the layout and components for displaying cell debugging information.
     */
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

    /**
     * Updates the debug panel with information about the selected cell. Displays the cell's coordinates, content,
     * value type, and expression tree (if applicable).
     *
     * @param cellReference the reference to the selected cell.
     * @param selectedData  the data of the selected cell.
     */
    public void setSelectedCell(CellReference cellReference, Data selectedData) {
        this.selectedData = selectedData;
        updateDebugInfo(cellReference);
    }

    /**
     * Updates the labels and text area with the relevant debugging information based on the selected cell.
     * Clears the debug panel if no data is available.
     *
     * @param cellReference the reference to the selected cell.
     */
    private void updateDebugInfo(CellReference cellReference) {
        if (selectedData != null) {
            Value val = selectedData.getValue();
            coordinatesLabel.setText("Coordinates: " + cellReference.identifier());
            contentLabel.setText("Content: " + selectedData.getValue());
            typeLabel.setText("Type: " + (val != null ? val.getClass().getSimpleName() : "ERR"));

            if (selectedData.getExpressionTree() != null) {
                treeTextArea.setText("Expression Tree: \n" + selectedData.prettyPrintExpression());
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
