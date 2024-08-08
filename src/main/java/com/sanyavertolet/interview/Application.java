package com.sanyavertolet.interview;

import com.sanyavertolet.interview.ui.MainFrame;

public class Application {

    public static Configuration getConfiguration(String[] args) {
        boolean isDebug = false;
        for (String arg : args) {
            if (arg.equals("--debug") || arg.equals("-d")) {
                isDebug = true;
                break;
            }
        }
        return new Configuration(isDebug);
    }

    public static void main(String[] args) {
        Configuration configuration = getConfiguration(args);
        MainFrame mainFrame = new MainFrame(configuration);
        mainFrame.setVisible(true);
    }
}
