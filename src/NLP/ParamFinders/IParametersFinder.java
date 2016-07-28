package NLP.ParamFinders;

import Brain.DomainOperationPair;
import NLP.Params.Value;
import Things.ParameterType;

import java.util.Collection;
import java.util.Map;

/**
 * Common interface for classes that can detect more than one type of parameter from a text
 */
public interface IParametersFinder {
    /**
     * Looks for parameters and relative values in the given text and gives back Commands filled with founded ParamValue
     *
     * @param domainOperationPairs Collection of DomainOperationPairs representing which kind of parameters should be found in the sentence
     * @param text                 Sentence where parameters and values are searched
     * @return Collection of Command built with given domains and operation, filled with parameters found in the text
     */
    Map<ParameterType, Value> findParameters(Collection<DomainOperationPair> domainOperationPairs, String text);
}
