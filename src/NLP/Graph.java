package NLP;

import Things.Domain;
import Things.Operation;
import Things.Parameter;

/**
 * Interface for structures that let you check if Domains, Operations and Parameters are in the given structure.
 */
public interface Graph {

    /**
     * @param domain Domain to be found in the graph
     * @return Index if the world representing the domain in the given sentence, -1 if not present.
     */
    int containsDomain(Domain domain);

    /**
     * @param operation   Operation, related to the domain, in the given graph
     * @param domain      Domain to be found in the graph
     * @param domainIndex Index of the domain in the graph
     * @return Index of the world representing the operation associated with the domain (if verb + preposition returns verb index). -1 if not present.
     */
    int containsOperation(Operation operation, Domain domain, int domainIndex);

    Object containsParameter(Parameter p, int operationIndex, int domainIndex);

}
