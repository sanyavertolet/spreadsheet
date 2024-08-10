package com.sanyavertolet.interview.ui.table;

import com.sanyavertolet.interview.data.Data;

import javax.swing.*;
import java.awt.*;

public class CustomCellEditor extends DefaultCellEditor {
    private Data data;

    public CustomCellEditor() {
        super(new JTextField());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.data = (Data) value;
        JTextField textField = (JTextField) super.getTableCellEditorComponent(table, value, isSelected, row, column);
        textField.setText(data.getText());
        return textField;
    }

    public Object getCellEditorValue() {
        JTextField textField = (JTextField) getComponent();
        data.setText(textField.getText());
        return data;
    }
}
