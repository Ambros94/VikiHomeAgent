package NLP;

import Things.Operation;
import Things.Parameter;
import Things.Domain;

/**
 * Interface for structures that let you check if Domains, Operations and Parameters are in the given structure.
 */
public interface Graph {

    int containsDomain(Domain t);

    int containsOperation(Operation operation, Domain domain, int domainIndex);

    Object containsParameter(Parameter p, int operationIndex, int domainIndex);

}
