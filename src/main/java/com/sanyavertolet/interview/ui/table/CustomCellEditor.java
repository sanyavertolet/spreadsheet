package com.sanyavertolet.interview.ui.table;

import com.sanyavertolet.interview.data.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.Serial;

/**
 * A custom cell editor for editing {@link Data} objects in a {@link JTable} representing a spreadsheet.
 * The {@code CustomCellEditor} class extends {@link DefaultCellEditor} to provide a tailored editing
 * experience using a {@link JTextField} for input.
 */
public class CustomCellEditor extends DefaultCellEditor {
    @Serial
    private static final long serialVersionUID = 42L;

    /**
     * Constructs a {@code CustomCellEditor} with a {@link JTextField} as the editing component.
     */
    public CustomCellEditor() {
        super(new JTextField());
    }

    /**
     * Prepares the editor component for editing the specified value. This method is called when a cell
     * is being edited. The editor component is a {@link JTextField} that displays the current text of the
     * {@link Data} object being edited.
     *
     * @param table the {@link JTable} that uses this editor.
     * @param value the value to be edited, which should be a {@link Data} object.
     * @param isSelected true if the cell is selected.
     * @param row the row index of the cell being edited.
     * @param column the column index of the cell being edited.
     * @return the editor {@link Component} used for editing.
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        Data data = (Data) value;
        JTextField textField = (JTextField) super.getTableCellEditorComponent(table, value, isSelected, row, column);

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                int textLength = textField.getDocument().getLength();
                textField.select(textLength, textLength);
            }
        });

        textField.setText(data.getText());
        return textField;
    }

    /**
     * Returns the value contained in the editor when editing is complete. The edited text is set back to the
     * {@link Data} object before returning it.
     *
     * @return the edited {@link Data} object.
     */
    @Override
    public Object getCellEditorValue() {
        JTextField textField = (JTextField) getComponent();
        return new Data(textField.getText());
    }
}
