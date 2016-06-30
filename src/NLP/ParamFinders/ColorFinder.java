package NLP.ParamFinders;

import Brain.ParamValuePair;
import Things.Parameter;
import Things.ParameterType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        sentence = sentence.toLowerCase();
        List<ParamValuePair> pairs = new ArrayList<>();
        String finalSentence = sentence;
        colors.forEach((name, hex) -> {
            if (finalSentence.contains(name))
                pairs.add(new ParamValuePair(parameter, hex));
        });
        return (pairs.size() > 0) ? pairs.get(0) : null;
    }
}
