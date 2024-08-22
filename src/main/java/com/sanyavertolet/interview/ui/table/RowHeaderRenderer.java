package com.sanyavertolet.interview.ui.table;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

/**
 * A custom cell renderer for the row headers of a {@link JTable}.
 * The {@code RowHeaderRenderer} class extends {@link DefaultTableCellRenderer} to provide
 * consistent styling with the table's header.
 */
public class RowHeaderRenderer extends DefaultTableCellRenderer {

    /**
     * Returns the component used to render the row header cell.
     * The renderer sets the foreground, background, and font to match the table header's style.
     *
     * @param table the {@link JTable} that uses this renderer.
     * @param value the value to assign to the cell at {@code [row, column]}.
     * @param isSelected true if the cell is selected.
     * @param hasFocus true if the cell has focus.
     * @param row the row index of the cell being drawn.
     * @param column the column index of the cell being drawn.
     * @return the {@link Component} that the caller should add to the row header.
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (table != null) {
            JTableHeader header = table.getTableHeader();

            if (header != null) {
                setForeground(header.getForeground());
                setBackground(header.getBackground());
                setFont(header.getFont());
            }
        }

        setValue(value);
        return this;
    }
}
