package com.sanyavertolet.interview.ui.table;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class BoarderCellRenderer extends DefaultTableCellRenderer {
    private final Border border = new LineBorder(Color.GRAY, 1);

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JComponent c = (JComponent) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        c.setBorder(border);
        return c;
    }
}
