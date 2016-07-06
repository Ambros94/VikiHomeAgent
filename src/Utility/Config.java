package Utility;

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

    private Config() {
        try {
            InputStream file = new FileInputStream(new File("resources/config.properties"));
            Properties props = new Properties();
            props.load(file);
            wrongFilePath = props.getProperty("wrongFilePath");
            rightFilePath = props.getProperty("rightFilePath");
            vikiAddress = props.getProperty("vikiAddress");
            dictionaryPath = props.getProperty("dictionaryPath");
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
