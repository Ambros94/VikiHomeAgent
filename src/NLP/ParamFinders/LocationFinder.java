package NLP.ParamFinders;

import Brain.ParamValue;
import NLP.Params.Location;
import Things.Parameter;
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
    public ParamValue<Location> find(Parameter parameter, String sentence) {
        Sentence s = new Sentence(sentence);
        List<String> words = s.words();
        List<String> nerTags = s.nerTags();
        String value = "";
        for (int i = 0; i < nerTags.size(); i++) {
            if (nerTags.get(i).equals("LOCATION"))
                value += words.get(i);
        }
        return new ParamValue<>(parameter, new Location(value));
    }
}
