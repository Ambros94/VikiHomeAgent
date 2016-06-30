package NLP.ParamFinders;

import Brain.ParamValuePair;
import Things.Parameter;
import Things.ParameterType;
import edu.stanford.nlp.simple.Sentence;

import java.util.List;

class LocationFinder implements ITypeFinder {

    @Override
    public ParameterType getAssociatedType() {
        return ParameterType.LOCATION;
    }

    @Override
    public ParamValuePair find(Parameter parameter, String sentence) {
        Sentence s = new Sentence(sentence);
        List<String> words = s.words();
        List<String> nerTags = s.nerTags();
        String value = "";
        for (int i = 0; i < nerTags.size(); i++) {
            if (nerTags.get(i).equals("LOCATION"))
                value += words.get(i);
        }
        return new ParamValuePair(parameter, value);
    }
}
