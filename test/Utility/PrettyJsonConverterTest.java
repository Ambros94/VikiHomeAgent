package Utility;

import org.junit.Test;

import static org.junit.Assert.*;


public class PrettyJsonConverterTest {
    @Test
    public void convert() throws Exception {
        String uglyJson = "{\n" +
                "\"pretty\":\"this is a test\",\"array\":[\"full\",\"of\",\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\"things\"]}";
        String prettyJson = new PrettyJsonConverter().convert(uglyJson);
        assertEquals("{\n" +
                "  \"pretty\": \"this is a test\",\n" +
                "  \"array\": [\n" +
                "    \"full\",\n" +
                "    \"of\",\n" +
                "    \"things\"\n" +
                "  ]\n" +
                "}", prettyJson);
    }

    @Test
    public void conversionError() throws Exception {
        String uglyJson = "this is not a json";
        String prettyJson = new PrettyJsonConverter().convert(uglyJson);
        assertEquals("{\"error\":\"Impossible to parse this JSON\"}", prettyJson);
    }

}