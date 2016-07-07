package NLP.ParamFinders;


import Brain.ParamValuePair;
import Things.Parameter;
import Things.ParameterType;

/**
 * Generic interface for classes that are able to detect a specific type of parameter.
 */
interface ITypeFinder {
    /**
     * Get the type of Parameter that this finder is able to find
     *
     * @return Type of Parameter that this finder is able to find
     */
    ParameterType getAssociatedType();

    /**
     * @param parameter Represent the parameter, with the relative type and his is, that has to be found in the sentence
     * @param sentence  Sentence where the parameter value is hidden
     * @return ParamValuePair with the given parameter and the value found in the sentence, null if nothing has been found,
     * a random one if there more than one parameter of that type (You can see more details on the log)
     */
    ParamValuePair find(Parameter parameter, String sentence);
}
