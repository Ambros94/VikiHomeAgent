package NLP.ParamFinders;

import Brain.ParamValue;
import NLP.Params.FreeText;
import Things.Parameter;
import Things.ParameterType;

class FreeTextFinder implements ITypeFinder {
    @Override
    public ParameterType getAssociatedType() {
        return ParameterType.FREE_TEXT;
    }

    @Override
    public ParamValue<FreeText> find(Parameter parameter, String sentence) {
        /*
         * TODO Do something smarter, needs additional parameter, maybe that case can be slightly different from the others
         * Discuss it with product owners
         */
        return new ParamValue<>(parameter, new FreeText("implemented soon"));
    }
}
