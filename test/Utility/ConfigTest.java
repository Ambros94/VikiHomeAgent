package Utility;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConfigTest {
    @Test
    public void getNormal() throws Exception {
        Config config = Config.getConfig();
        assertEquals("resources/commandLog/wrong.txt", config.getWrongFilePath());
    }

    @Test
    public void getFromFile() throws Exception {
        Config testConfig = Config.getConfig("resources/configTest.properties");

        assertEquals("a", testConfig.getWrongFilePath());
        assertEquals("b", testConfig.getRightFilePath());
        assertEquals("c", testConfig.getVikiAddress());
        assertEquals("d", testConfig.getDictionaryPath());
        assertEquals("e", testConfig.getColorPath());
        assertEquals("f", testConfig.getVikiFilePath());
        assertEquals("g", testConfig.getVikiGetUrl());
    }


    @Test
    public void rotto() {
        Config.getConfig("path");
    }

    @Test
    public void getDefaults() throws Exception {
        Config defaultConfig = Config.getConfig("resources/emptyConfig.properties");

        assertEquals("resources/commandLog/wrong.txt", defaultConfig.getWrongFilePath());
        assertEquals("resources/commandLog/miscellaneous.txt", defaultConfig.getMiscellaneousPath());
        assertEquals("resources/commandLog/right.txt", defaultConfig.getRightFilePath());
        assertEquals("ws://localhost", defaultConfig.getVikiAddress());
        assertEquals("resources/dict", defaultConfig.getDictionaryPath());
        assertEquals("resources/dict/colors.txt", defaultConfig.getColorPath());
        assertEquals("resources/mock_up/viki.json", defaultConfig.getVikiFilePath());
        assertEquals("http://cose.cose:9000", defaultConfig.getVikiGetUrl());
        assertEquals("localhost", defaultConfig.getCommandReceiverAddress());
        assertEquals(9123, defaultConfig.getCommandReceiverPort());
        assertEquals("command", defaultConfig.getCommandEventMessage());
        assertEquals("textCommand", defaultConfig.getTextCommandMessage());
        assertEquals(0.65d, defaultConfig.getMinConfidence(), 0.001d);
    }


}