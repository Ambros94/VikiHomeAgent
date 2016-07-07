package NLP.ParamFinders;

import Things.ParameterType;

/**
 * Custom exception thrown when there is not a finder able to detect a type of parameter
 * e.g. ParameterType.ERROR has no associated Finder, obviously
 */
class MissingFinderException extends RuntimeException {
    /**
     * Exception constructor
     *
     * @param pt Type of the parameter which finder is missing
     */
    MissingFinderException(ParameterType pt) {
        super("There is no finders for  " + pt + "ParameterType");
    }
}
