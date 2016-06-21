package GUI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Utility {

    public static String prettyJsonString(String uglyJsonString) {
        System.out.println(uglyJsonString);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        try {
            JsonElement je = jp.parse(uglyJsonString);
            return gson.toJson(je);
        } catch (com.google.gson.JsonSyntaxException e) {
            System.out.println(uglyJsonString);
            return "Cannot parse this json";
        }
    }
}
