package NLP.ParamFinders;

import Brain.ParamValuePair;
import Things.Parameter;
import Things.ParameterType;

public class FreeTextFinder implements ITypeFinder {
    @Override
    public ParameterType getAssociatedType() {
        return ParameterType.FREE_TEXT;
    }

    @Override
    public ParamValuePair find(Parameter parameter, String sentence) {
        return null;
    }
}