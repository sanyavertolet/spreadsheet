package com.sanyavertolet.interview.ui.menu;

import com.sanyavertolet.interview.data.manager.DataManager;
import com.sanyavertolet.interview.ui.menu.file.FileMenu;

import javax.swing.*;

public class MenuBar extends JMenuBar {
    public MenuBar(DataManager dataManager) {
        JMenu fileMenu = new FileMenu(dataManager);

        add(fileMenu);
    }
}
