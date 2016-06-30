package NLP.ParamFinders;

import Brain.ParamValuePair;
import Things.Parameter;
import Things.ParameterType;

public class NumberFinder implements ITypeFinder {
    @Override
    public ParameterType getAssociatedType() {
        return ParameterType.NUMBER;
    }

    @Override
    public ParamValuePair find(Parameter parameter, String sentence) {
        return null;
    }
}
