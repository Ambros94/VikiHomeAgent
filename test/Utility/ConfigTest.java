package Utility;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConfigTest {

    private static Config config;

    @BeforeClass
    public static void init() {
        config = Config.getSingleton();
    }

    @Test
    public void getWrongFilePath() throws Exception {
        assertEquals("resources/commandLog/wrong.txt",config.getWrongFilePath());
    }

    @Test
    public void getRightFilePath() throws Exception {
        assertEquals("resources/commandLog/right.txt",config.getRightFilePath());
    }

}