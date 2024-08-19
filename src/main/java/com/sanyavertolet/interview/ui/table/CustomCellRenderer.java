package com.sanyavertolet.interview.ui.table;

import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.data.value.Value;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomCellRenderer extends DefaultTableCellRenderer {
    private final Border selectedCellBorder = BorderFactory.createLineBorder(Color.BLUE);

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
            } else if (val == null) {
                setText("ERR");
            } else {
                setText(val.asString());
            }
        }

        return c;
    }
}
