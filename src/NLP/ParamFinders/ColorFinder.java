package NLP.ParamFinders;

import Brain.ParamValuePair;
import Things.Parameter;
import Things.ParameterType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


class ColorFinder implements ITypeFinder {


    private static Map<String, String> colors = new HashMap<>();

    static {
        String path = "resources/dict/colors.txt";
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
    public ParamValuePair find(Parameter parameter, String sentence) {
        /**
         * Variables to be returned
         */
        final ParamValuePair[] pair = {null};
        final int[] strLength = {Integer.MIN_VALUE};
        /**
         * All colors are lower case, so we avoid problem with colors that are not
         */
        String finalSentence = sentence.toLowerCase();
        /**
         * Choose the longest string colors (Because "Sandy brown" is always longer than "Brown")
         */
        colors.forEach((name, hex) -> {
            if (finalSentence.contains(name) && (name.length() > strLength[0])) {
                pair[0] = new ParamValuePair(parameter, hex);
                strLength[0] = name.length();

            }
        });
        return pair[0];
    }
}
