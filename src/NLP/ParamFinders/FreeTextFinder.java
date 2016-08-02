package NLP.ParamFinders;

import NLP.Params.Value;
import Things.ParameterType;

class FreeTextFinder implements ITypeFinder {
    @Override
    public ParameterType getAssociatedType() {
        return ParameterType.FREE_TEXT;
    }

    @Override
    public Value find(ParameterType parameterType, String sentence) {
        /*
         * TODO Do something smarter, needs additional parameter, maybe that case can be slightly different from the others
         * Discuss it with product owners
         */
        return null;

    }

}
