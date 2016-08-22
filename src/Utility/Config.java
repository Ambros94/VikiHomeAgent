package Utility;


import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Singleton Object representing Config loaded from file, with methods to retrieve configurations that can be modified without touching code
 */
public class Config {
    /**
     * Singleton instance
     */
    private static Config instance;
    /**
     * Properties
     */
    private String wrongFilePath;
    private String memoryPath;
    private String rightFilePath;
    private String textCommandMessage;
    private String vikiAddress;
    private String dictionaryPath;
    private String colorPath;
    private String vikiGetUrl;
    private String vikiFilePath;
    private String commandReceiverAddress;
    private String commandEventMessage;
    private double learningRate = 0.6d;
    private double minConfidence = 0.65d;
    private int commandReceiverPort;
    private String miscellaneousPath;
    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger(Config.class);

    /**
     * Build a config, loading config.properties.
     * If some properties are not found in the file defaults are used (On the log)
     */
    private Config() {
        this("resources/config.properties");
    }

    /**
     * Build a config, loading config.properties.
     * If some properties are not found in the file defaults are used (On the log)
     */
    private Config(String configPath) {
        try {
            FileInputStream file = new FileInputStream(new File(configPath));

            Properties props = new Properties();
            props.load(file);
            wrongFilePath = props.getProperty("wrongFilePath");
            if (wrongFilePath == null) {
                logger.info("No wrongFile path in the config file, using default [resources/commandLog/wrong.txt]");
                wrongFilePath = "resources/commandLog/wrong.txt";
            }
            if (miscellaneousPath == null) {
                logger.info("No miscellaneousPath path in the config file, using default [resources/commandLog/miscellaneous.txt]");
                miscellaneousPath = "resources/commandLog/miscellaneous.txt";
            }
            rightFilePath = props.getProperty("rightFilePath");
            if (rightFilePath == null) {
                logger.info("No rightFilePath path in the config file, using default [resources/commandLog/right.txt]");
                rightFilePath = "resources/commandLog/right.txt";
            }
            vikiAddress = props.getProperty("vikiAddress");
            if (vikiAddress == null) {
                logger.info("No vikiAddress path in the config file, using default [ws://localhost]");
                vikiAddress = "ws://localhost";
            }
            dictionaryPath = props.getProperty("dictionaryPath");
            if (dictionaryPath == null) {
                logger.info("No dictionaryPath path in the config file, using default [resources/dict]");
                dictionaryPath = "resources/dict";
            }
            colorPath = props.getProperty("colorPath");
            if (colorPath == null) {
                logger.info("No colorPath path in the config file, using default [resources/dict/colors.txt]");
                colorPath = "resources/dict/colors.txt";
            }
            vikiFilePath = props.getProperty("vikiFilePath");
            if (vikiFilePath == null) {
                logger.info("No vikiFilePath path in the config file, using default [resources/mock_up/viki.json]");
                vikiFilePath = "resources/mock_up/viki.json";
            }
            vikiGetUrl = props.getProperty("vikiGetUrl");
            if (vikiGetUrl == null) {
                logger.info("No vikiGetUrl path in the config file, using default [resources/mock_up/viki.json]");
                vikiGetUrl = "http://cose.cose:9000";
            }
            commandReceiverAddress = props.getProperty("commandReceiverAddress");
            if (commandReceiverAddress == null) {
                logger.info("No commandReceiverAddress path in the config file, using default [localhost]");
                commandReceiverAddress = "localhost";
            }
            try {
                commandReceiverPort = Integer.valueOf(props.getProperty("commandReceiverPort"));
            } catch (NumberFormatException e) {
                logger.info("No commandReceiverPort path in the config file, using default [9123]");
                commandReceiverPort = 9123;
            }
            commandEventMessage = props.getProperty("commandEventMessage");
            if (commandEventMessage == null) {
                logger.info("No commandEventMessage path in the config file, using default [command]");
                commandEventMessage = "command";
            }
            memoryPath = props.getProperty("memoryPath");
            if (memoryPath == null) {
                logger.info("No memoryPath path in the config file, using default [resources/memory/memory.mem]");
                memoryPath = "resources/memory/memory.mem";
            }
            textCommandMessage = props.getProperty("textCommandMessage");
            if (textCommandMessage == null) {
                logger.info("No textCommandMessage path in the config file, using default [textCommand]");
                textCommandMessage = "textCommand";
            }
            try {
                minConfidence = Double.valueOf(props.getProperty("minConfidence"));
            } catch (NumberFormatException e) {
                logger.info("No minConfidence path in the config file, using default [0.6]");
            }
            try {
                learningRate = Double.valueOf(props.getProperty("learningRate"));
            } catch (NumberFormatException e) {
                logger.info("No learningRate path in the config file, using default [0.6]");
            }
        } catch (Exception e) {
            logger.error("Cannot find config file ! Using default for everything");
        }
    }

    /**
     * Singleton getter
     *
     * @return Singleton instance of Config
     */
    public static Config getConfig() {
        if (instance == null)
            instance = new Config();
        return instance;
    }

    /**
     * Force config to load everything for a given file. It does not touch the singleton, it returns an instance
     *
     * @return Singleton instance of Config
     */
    public static Config getConfig(String configPath) {
        return new Config(configPath);
    }

    public String getWrongFilePath() {
        return wrongFilePath;
    }

    public String getRightFilePath() {
        return rightFilePath;
    }

    public String getVikiAddress() {
        return vikiAddress;
    }

    public String getDictionaryPath() {
        return dictionaryPath;
    }

    public String getTextCommandMessage() {
        return textCommandMessage;
    }

    public String getColorPath() {
        return colorPath;
    }

    public String getVikiGetUrl() {
        return vikiGetUrl;
    }

    public String getVikiFilePath() {
        return vikiFilePath;
    }

    public String getCommandReceiverAddress() {
        return commandReceiverAddress;
    }

    public int getCommandReceiverPort() {
        return commandReceiverPort;
    }

    public String getCommandEventMessage() {
        return commandEventMessage;
    }

    public double getMinConfidence() {
        return minConfidence;
    }

    public String getMemoryPath() {
        return memoryPath;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public String getMiscellaneousPath() {
        return miscellaneousPath;
    }
}
