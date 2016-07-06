package Utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Method Object used to convert a String representing a Json into another string property formatted following the json standard
 */
public class PrettyJsonConverter {
    /**
     * @param uglyJsonString String representing a Json
     * @return String representing the given Json, but property formatted,
     * If the json is cannot be converted because the input is not a valid json if will return a json with the error occurred {'error':'Impossible to parse this JSON'}
     */
    public String convert(String uglyJsonString) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        try {
            JsonElement je = jp.parse(uglyJsonString);
            return gson.toJson(je);
        } catch (com.google.gson.JsonSyntaxException e) {
            System.out.println(uglyJsonString);
            return "{\"error\":\"Impossible to parse this JSON\"}";
        }

    }
}
