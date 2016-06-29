package NLP.ParamFinders;


import Things.ParameterType;

class MissingFinderException extends RuntimeException {
    MissingFinderException(ParameterType pt) {
        super("There is no finders for  " + pt + "ParameterType");
    }
}
