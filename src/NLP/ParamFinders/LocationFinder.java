package NLP.ParamFinders;

import NLP.Params.Location;
import NLP.Params.Value;
import Things.ParameterType;
import edu.stanford.nlp.simple.Sentence;

import java.util.List;

/**
 * Parameter finder used to retrieve Locations. It finds mainly Stated and Cities
 */
class LocationFinder implements ITypeFinder {

    @Override
    public ParameterType getAssociatedType() {
        return ParameterType.LOCATION;
    }

    @Override
    public Value find(ParameterType parameterType, String sentence) {
        Sentence s = new Sentence(sentence);
        List<String> words = s.words();
        List<String> nerTags = s.nerTags();
        String value = "";
        for (int i = 0; i < nerTags.size(); i++) {
            if (nerTags.get(i).equals("LOCATION"))
                value += words.get(i);
        }
        if (value.equals(""))
            return null;
        return new Location(value);
    }

}
