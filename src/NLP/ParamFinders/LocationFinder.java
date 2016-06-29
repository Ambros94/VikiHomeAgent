package NLP.ParamFinders;

import Brain.ParamValuePair;
import Things.Parameter;
import Things.ParameterType;

public class LocationFinder implements ITypeFinder {

    @Override
    public ParameterType getAssociatedType() {
        return ParameterType.LOCATION;
    }

    @Override
    public ParamValuePair find(Parameter parameter, String sentence) {
        return new ParamValuePair(parameter, "London");
    }
}
