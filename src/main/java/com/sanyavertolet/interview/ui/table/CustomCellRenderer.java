package com.sanyavertolet.interview.ui.table;

import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.data.value.BooleanValue;
import com.sanyavertolet.interview.data.value.DoubleValue;
import com.sanyavertolet.interview.data.value.IntegerValue;
import com.sanyavertolet.interview.data.value.Value;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.Serial;

import static com.sanyavertolet.interview.utils.Constants.CELL_ERROR_TEXT;

/**
 * A custom cell renderer for the cells of a {@link JTable} that represents a spreadsheet.
 * The {@code CustomCellRenderer} class extends {@link DefaultTableCellRenderer} to provide
 * specialized rendering for cells that display {@link Data} objects, including handling selected
 * cell borders and error states.
 */
public class CustomCellRenderer extends DefaultTableCellRenderer {
    @Serial
    private static final long serialVersionUID = 42L;

    private final Border selectedCellBorder = BorderFactory.createLineBorder(Color.BLUE);

    /**
     * Returns the component used to render the cell. This renderer customizes the cell's appearance
     * based on its selection state and the content type. It applies a blue border to selected cells
     * and displays red-colored {@code CELL_ERROR_TEXT} for cells containing data with an error.
     *
     * @param table the {@link JTable} that uses this renderer.
     * @param value the value to assign to the cell at {@code [row, column]}.
     * @param isSelected true if the cell is selected.
     * @param hasFocus true if the cell has focus.
     * @param row the row index of the cell being drawn.
     * @param column the column index of the cell being drawn.
     * @return the {@link Component} used to render the cell.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JComponent c = (JComponent) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (isSelected) {
            setBorder(selectedCellBorder);
        }

        setBackground(table.getBackground());
        setForeground(table.getForeground());

        if (value instanceof Data data) {
            Value val = data.getValue();
            if (isSelected) {
                setText(data.getText());
                setHorizontalAlignment(JLabel.CENTER);
            } else if (val == null) {
                setText(CELL_ERROR_TEXT);
                setForeground(Color.RED);
                setHorizontalAlignment(JLabel.CENTER);
            } else {
                if (val instanceof DoubleValue || val instanceof IntegerValue) {
                    setHorizontalAlignment(JLabel.RIGHT);
                } else if (val instanceof BooleanValue) {
                    setHorizontalAlignment(JLabel.CENTER);
                } else {
                    setHorizontalAlignment(JLabel.LEFT);
                }
                setText(val.toString());
            }
        }

        return c;
    }
}
