package NLP.ParamFinders;

import Brain.ParamValuePair;
import Things.Parameter;
import Things.ParameterType;

class FreeTextFinder implements ITypeFinder {
    @Override
    public ParameterType getAssociatedType() {
        return ParameterType.FREE_TEXT;
    }

    @Override
    public ParamValuePair find(Parameter parameter, String sentence) {
        /**
         * TODO Do something smarter, needs additional parameter, maybe that case can be slightly different from the others
         * Discuss it with product owners
         */
        return new ParamValuePair(parameter, sentence);
    }
}
