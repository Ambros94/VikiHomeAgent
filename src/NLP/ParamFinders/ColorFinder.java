package NLP.ParamFinders;

import NLP.Params.Color;
import NLP.Params.Value;
import Things.ParameterType;
import Utility.Config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Color finder used to retrieve Colors. It looks for ColorNames and returns #RRGGBB in hex format
 */
class ColorFinder implements ITypeFinder {
    /**
     * Map with names as key and hex RGBValue of the corresponding color
     */
    private static Map<String, String> colors = new HashMap<>();

    /*
     * Loads colors from file
     */
    static {
        String path = Config.getConfig().getColorPath();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split("\t");
                colors.put(tokens[0].trim().toLowerCase(), tokens[1].trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public ParameterType getAssociatedType() {
        return ParameterType.COLOR;
    }

    @Override
    public Value find(ParameterType parameterType, String sentence) {
       /*
         * Variables to be returned
         */
        final Value[] pair = new Value[1];
        final int[] strLength = {Integer.MIN_VALUE};
        /*
         * All colors are lower case, so we avoid problem with sentences that are not
         */
        String finalSentence = sentence.toLowerCase();
        /*
         * Choose the longest string colors (e.g. "Sandy brown" is always longer than "Brown")
         */
        colors.forEach((name, hex) -> {
            if (finalSentence.contains(name) && (name.length() > strLength[0])) {
                pair[0] = new Color(hex);
                strLength[0] = name.length();
            }
        });
        return pair[0];
    }

}
