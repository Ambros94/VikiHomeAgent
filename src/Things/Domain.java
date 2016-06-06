package Things;

import NLP.Synonyms;

import java.util.HashSet;
import java.util.Set;

/**
 * A Domain linked to operations that can be performed
 * Can be a sensor, an actuator or a generic API as the weather is
 */

public class Domain extends Synonyms {
    /**
     * A domain can have friendly names (A lamp can be called "Palla")
     */
    private final Set<String> friendlyNames;
    /**
     * Collection of operation that can be performed in this domain
     */
    Set<Operation> operations;

    public Domain(String id, Set<String> words) {
        super(id, words);
        friendlyNames = new HashSet<>();
        operations = new HashSet<>();
    }

    public Domain(String id, Set<String> words, Set<String> friendlyNames) {
        super(id, words);
        this.friendlyNames = friendlyNames;
    }

    public void setOperations(Set<Operation> operations) {
        this.operations = operations;
    }

    public Set<Operation> getOperations() {
        return operations;
    }
}
