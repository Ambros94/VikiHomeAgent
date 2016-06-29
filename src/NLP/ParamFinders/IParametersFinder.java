package NLP.ParamFinders;

import Brain.Command;
import Brain.DomainOperationPair;

import java.util.Collection;
import java.util.List;

public interface IParametersFinder {
    Collection<Command> findParameters(Collection<DomainOperationPair> domainOperationPairs, String text);
}
