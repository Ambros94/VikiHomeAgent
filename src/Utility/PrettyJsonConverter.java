package Utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


public class PrettyJsonConverter {

    public String convert(String uglyJsonString){
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