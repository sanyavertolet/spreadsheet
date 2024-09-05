package com.sanyavertolet.interview;

import com.sanyavertolet.interview.ui.MainFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The entry point of the entire application. This class is responsible for initializing the application
 * configuration and launching the main user interface.
 */
public class Application {
    private final static Logger logger = LoggerFactory.getLogger(Application.class);

    /**
     * Parses the command-line arguments to determine the application's configuration.
     * Specifically, it checks for a debug flag (`--debug` or `-d`) that indicates whether
     * the debug panel should be shown.
     *
     * @param args command-line arguments
     * @return a {@link Configuration} object representing the application's configuration settings.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
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

    /**
     * The main method that serves as the entry point of the application. It initializes the configuration
     * based on command-line arguments and launches the main user interface.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        logger.info("Starting application...");
        Configuration configuration = getConfiguration(args);
        logger.debug("Debug flag is set to be {}", configuration.isDebug());
        MainFrame mainFrame = new MainFrame(configuration);
        mainFrame.setVisible(true);
    }
}
