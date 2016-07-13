package Utility;

import Comunication.VikiRemoteLoader;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class VikiRemoteLoaderTest {
    @Test(expected = IOException.class)
    public void loadFromRemote() throws Exception {
        new VikiRemoteLoader().loadFromRemote();
    }

    @Test
    public void loadFromFile() throws Exception {
        String fileStringer = "{\n" +
                "  \"domains\": [\n" +
                "    {\n" +
                "      \"id\": \"light1\",\n" +
                "      \"words\": [\n" +
                "        \"lamp\",\n" +
                "        \"light\"\n" +
                "      ],\n" +
                "      \"operations\": [\n" +
                "        {\n" +
                "          \"id\": \"turnOff\",\n" +
                "          \"textInvocation\": [\n" +
                "            \"Could you please turn off the light?\"\n" +
                "          ],\n" +
                "          \"words\": [\n" +
                "            \"turnOff\"\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": \"turnOn\",\n" +
                "          \"textInvocation\": [\n" +
                "            \"Could you please turn on the light?\"\n" +
                "          ],\n" +
                "          \"words\": [\n" +
                "            \"turnOn\"\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": \"isOn\",\n" +
                "          \"textInvocation\": [\n" +
                "            \"Is the light on?\"\n" +
                "          ],\n" +
                "          \"words\": [\n" +
                "            \"isOn\"\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": \"isOff\",\n" +
                "          \"textInvocation\": [\n" +
                "            \"Is the light off?\"\n" +
                "          ],\n" +
                "          \"words\": [\n" +
                "            \"isOff\"\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": \"setIntensity\",\n" +
                "          \"textInvocation\": [\n" +
                "            \"Set light intensity to 80%\"\n" +
                "          ],\n" +
                "          \"mandatoryParameters\": [\n" +
                "            {\n" +
                "              \"id\": \"intensity\",\n" +
                "              \"type\": \"NUMBER\"\n" +
                "            }\n" +
                "          ],\n" +
                "          \"words\": [\n" +
                "            \"setIntensity\"\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": \"setColor\",\n" +
                "          \"textInvocation\": [\n" +
                "            \"Set light color to red\"\n" +
                "          ],\n" +
                "          \"mandatoryParameters\": [\n" +
                "            {\n" +
                "              \"id\": \"color\",\n" +
                "              \"type\": \"COLOR\"\n" +
                "            }\n" +
                "          ],\n" +
                "          \"words\": [\n" +
                "            \"setColor\"\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": \"getColor\",\n" +
                "          \"textInvocation\": [\n" +
                "            \"Which color is the lamp\"\n" +
                "          ],\n" +
                "          \"words\": [\n" +
                "            \"getColor\"\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"heater1\",\n" +
                "      \"words\": [\n" +
                "        \"heater\"\n" +
                "      ],\n" +
                "      \"operations\": [\n" +
                "        {\n" +
                "          \"id\": \"turnOn\",\n" +
                "          \"textInvocation\": [\n" +
                "            \"Turn on the heater\"\n" +
                "          ],\n" +
                "          \"words\": [\n" +
                "            \"turnOn\"\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": \"turnOff\",\n" +
                "          \"textInvocation\": [\n" +
                "            \"Turn off the heater\"\n" +
                "          ],\n" +
                "          \"words\": [\n" +
                "            \"turnOff\"\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": \"getTemperature\",\n" +
                "          \"textInvocation\": [\n" +
                "            \"What's the temperature?\"\n" +
                "          ],\n" +
                "          \"words\": [\n" +
                "            \"getTemperature\"\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": \"setTemperature\",\n" +
                "          \"textInvocation\": [\n" +
                "            \"Set the heater temperature to 21\"\n" +
                "          ],\n" +
                "          \"mandatoryParameters\": [\n" +
                "            {\n" +
                "              \"id\": \"temperature\",\n" +
                "              \"type\": \"NUMBER\"\n" +
                "            }\n" +
                "          ],\n" +
                "          \"words\": [\n" +
                "            \"setTemperature\"\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"weatherAPI\",\n" +
                "      \"words\": [\n" +
                "        \"weather\"\n" +
                "      ],\n" +
                "      \"operations\": [\n" +
                "        {\n" +
                "          \"id\": \"getWeather\",\n" +
                "          \"textInvocation\": [\n" +
                "            \"What's the weather in London?\"\n" +
                "          ],\n" +
                "          \"optionalParameters\": [\n" +
                "            {\n" +
                "              \"id\": \"location\",\n" +
                "              \"type\": \"LOCATION\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"id\": \"date\",\n" +
                "              \"type\": \"DATETIME\"\n" +
                "            }\n" +
                "          ],\n" +
                "          \"words\": [\n" +
                "            \"getWeather\"\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        assertEquals(fileStringer, new VikiRemoteLoader().loadFromFile());
    }

}