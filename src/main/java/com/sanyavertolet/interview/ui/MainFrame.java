package com.sanyavertolet.interview.ui;

import com.sanyavertolet.interview.ui.table.SpreadsheetTable;

import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Spreadsheets");
        setSize(800, 600);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setJMenuBar(new MenuBar());
        add(new JScrollPane(new SpreadsheetTable()));

        setVisible(true);
    }
}
