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
    /**
     * Label that displays cell identifier
     */
    private final JLabel coordinatesLabel;

    /**
     * Label that displays cell content
     */
    private final JLabel contentLabel;

    /**
     * Label that displays cell value
     */
    private final JLabel valueLabel;

    /**
     * Label that displays type of cell {@code Value}
     */
    private final JLabel valueTypeLabel;

    /**
     * JTextArea that displays expression tree of a cell (if present)
     */
    private final JTextArea treeTextArea;

    /**
     * Title of {@code coordinatesLabel}
     */
    private final String coordinatesTitle = "Coordinates: ";

    /**
     * Title of {@code contentLabel}
     */
    private final String contentTitle = "Content: ";

    /**
     * Title of {@code valueLabel}
     */
    private final String valueTitle = "Value: ";

    /**
     * Title of {@code valueTypeLabel}
     */
    private final String valueTypeTitle = "Value type: ";

    /**
     * Title of {@code treeTextArea}
     */
    private final String treeTitle = "Expression tree:\n";

    /**
     * {@code Data} of currently selected cell
     */
    private Data selectedData;

    /**
     * Constructs a {@code DebugPanel} and sets up the layout and components for displaying cell debugging information.
     */
    public DebugPanel() {
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        coordinatesLabel = new JLabel(coordinatesTitle);
        contentLabel = new JLabel(contentTitle);
        valueLabel = new JLabel(valueTitle);
        valueTypeLabel = new JLabel(valueTypeTitle);
        treeTextArea = new JTextArea(treeTitle);
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
        add(valueLabel, gbc);

        gbc.gridy = 3;
        add(valueTypeLabel, gbc);

        gbc.gridy = 4;
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
            coordinatesLabel.setText(coordinatesTitle + cellReference.identifier());
            contentLabel.setText(contentTitle + selectedData.getText());
            valueLabel.setText(valueTitle + selectedData.getValue());
            valueTypeLabel.setText(valueTypeTitle + (val != null ? val.getClass().getSimpleName() : "ERR"));
            treeTextArea.setText(treeTitle + selectedData.prettyPrintExpression());
        } else {
            coordinatesLabel.setText(coordinatesTitle);
            contentLabel.setText(contentTitle);
            valueLabel.setText(valueTitle);
            valueTypeLabel.setText(valueTypeTitle);
            treeTextArea.setText(treeTitle);
        }
    }
}
