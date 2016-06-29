package NLP.ParamFinders;


import Brain.ParamValuePair;
import Things.Parameter;
import Things.ParameterType;

interface ITypeFinder {

    ParameterType getAssociatedType();

    ParamValuePair find(Parameter parameter, String sentence);
}
