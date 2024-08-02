package com.sanyavertolet.interview.ui.table;

import com.sanyavertolet.interview.data.Cell;

import javax.swing.*;
import java.awt.*;

public class CustomCellEditor extends DefaultCellEditor {
    private Cell cell;

    public CustomCellEditor() {
        super(new JTextField());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.cell = (Cell) value;
        JTextField textField = (JTextField) super.getTableCellEditorComponent(table, value, isSelected, row, column);
        textField.setText(cell.getText());
        return textField;
    }

    public Object getCellEditorValue() {
        JTextField textField = (JTextField) getComponent();
        cell.setText(textField.getText());
        return cell;
    }
}
