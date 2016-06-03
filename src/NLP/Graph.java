package NLP;

import Things.Operation;
import Things.Parameter;
import Things.Domain;

/**
 * Interface for structures that let you check if Domains, Operations and Parameters are in the given structure.
 */
public interface Graph {

    int contains(Domain t);

    int contains(Operation o, int domainIndex);

    Object contains(Parameter p, int operationIndex, int domainIndex);

}
