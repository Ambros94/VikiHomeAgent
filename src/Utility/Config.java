package Utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
     * Right and Wrong command log files
     */
    private String wrongFilePath;
    private String rightFilePath;
    private String vikiAddress;
    private String dictionaryPath;
    /**
     * Logger
     */
    private static Logger logger = LoggerFactory.getLogger(Config.class);

    private Config() {
        try {
            InputStream file = new FileInputStream(new File("resources/config.properties"));
            Properties props = new Properties();
            props.load(file);
            wrongFilePath = props.getProperty("wrongFilePath");
            if (wrongFilePath == null) {
                logger.info("No wrongFile path in the config file, using default [resources/commandLog/wrong.txt]");
                wrongFilePath = "resources/commandLog/wrong.txt";
            }
            rightFilePath = props.getProperty("rightFilePath");
            if (rightFilePath == null) {
                logger.info("No rightFilePath path in the config file, using default [resources/commandLog/right.txt]");
                rightFilePath = "resources/commandLog/right.txt";
            }
            vikiAddress = props.getProperty("vikiAddress");
            if (vikiAddress == null) {
                logger.info("No vikiAddress path in the config file, using default [localhost:1234]");
                vikiAddress = "localhost:1234";
            }
            dictionaryPath = props.getProperty("dictionaryPath");
            if (dictionaryPath == null) {
                logger.info("No dictionaryPath path in the config file, using default [resources/dict]");
                dictionaryPath = "resources/dict";
            }
        } catch (Exception e) {
            System.out.println("error" + e);
        }
    }

    /**
     * Singleton getter
     *
     * @return Singleton instance of Config
     */
    public static Config getSingleton() {
        if (instance == null)
            instance = new Config();
        return instance;
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
}
