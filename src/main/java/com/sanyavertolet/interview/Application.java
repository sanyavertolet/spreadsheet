package com.sanyavertolet.interview;

import com.sanyavertolet.interview.ui.MainFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Application {
    private final static Logger logger = LoggerFactory.getLogger(Application.class);
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
        logger.info("Starting application...");
        Configuration configuration = getConfiguration(args);
        logger.debug("Debug flag is set to be {}", configuration.isDebug());
        MainFrame mainFrame = new MainFrame(configuration);
        mainFrame.setVisible(true);
    }
}
