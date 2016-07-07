package NLP.ParamFinders;

import Brain.Command;
import Brain.DomainOperationPair;

import java.util.Collection;

/**
 * Common interface for classes that can detect more than one type of parameter from a text
 * TODO This interface should recive only parameter type, return values
 * TODO And then another class should fullfill domainOperationPairs with values found in the sentence
 */
public interface IParametersFinder {
    /**
     * Looks for parameters and relative values in the given text and gives back Commands filled with founded ParamValuePair
     *
     * @param domainOperationPairs Collection of DomainOperationPairs representing which kind of parameters should be found in the sentence
     * @param text                 Sentence where parameters and values are searched
     * @return Collection of Command built with given domains and operation, filled with parameters found in the text
     */
    Collection<Command> findParameters(Collection<DomainOperationPair> domainOperationPairs, String text);
}
