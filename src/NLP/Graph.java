package NLP;

import Things.Operation;
import Things.Parameter;
import Things.Domain;

/**
 * Interface for structures that let you check if Domains, Operations and Parameters are in the given structure.
 */
public interface Graph {

    boolean contains(Domain t);

    boolean contains(Operation o, Domain t);

    Object contains(Parameter p, Operation o, Domain t);

}
