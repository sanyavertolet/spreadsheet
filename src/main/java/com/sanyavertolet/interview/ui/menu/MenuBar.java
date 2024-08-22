package com.sanyavertolet.interview.ui.menu;

import com.sanyavertolet.interview.data.manager.DataManager;
import com.sanyavertolet.interview.ui.menu.file.FileMenu;

import javax.swing.*;

/**
 * A custom menu bar for the spreadsheet application, extending {@link JMenuBar}.
 * The {@code MenuBar} class initializes and adds the necessary menus, such as the file menu,
 * to the menu bar.
 */
public class MenuBar extends JMenuBar {

    /**
     * Constructs a {@code MenuBar} with a file menu. The file menu is initialized using the provided
     * {@link DataManager} to manage file-related operations.
     *
     * @param dataManager the data manager used for managing the data in the spreadsheet, passed to the file menu.
     */
    public MenuBar(DataManager dataManager) {
        JMenu fileMenu = new FileMenu(dataManager);
        add(fileMenu);
    }
}
