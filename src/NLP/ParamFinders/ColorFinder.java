package NLP.ParamFinders;

import Brain.ParamValuePair;
import Things.Parameter;
import Things.ParameterType;


public class ColorFinder implements ITypeFinder {
    @Override
    public ParameterType getAssociatedType() {
        return ParameterType.COLOR;
    }

    @Override
    public ParamValuePair find(Parameter parameter, String sentence) {
        return null;
    }
}
