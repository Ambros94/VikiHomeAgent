package Things;

import InstanceCreator.OperationInstanceCreator;
import InstanceCreator.ParameterInstanceCreator;
import NLP.Synonyms;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
    private final Set<String> friendlyNames = new HashSet<>();
    /**
     * Collection of operation that can be performed in this domain
     */
    private Set<Operation> operations;

    public Domain(String id, Set<String> words, Set<Operation> operations) {
        super(id, words);
        this.operations = operations;
    }

    public Domain(String id, Set<String> words) {
        super(id, words);
        this.operations = new HashSet<>();
    }

    public void updateDomainSynonyms() {
        super.updateSynonyms();
        getOperations().forEach(Synonyms::updateSynonyms);
    }

    /**
     * Build a Domain from a JSON, if the JSON is not properly formatted the given object will have
     * id=NoDomain? and words={NoWords?}
     *
     * @param json String JSON formatted representing the Parameter
     * @return Instance of Domain built with data from the JSON
     */
    public static Domain fromJson(String json) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Parameter.class, new ParameterInstanceCreator());
        gsonBuilder.registerTypeAdapter(Operation.class, new OperationInstanceCreator());
        Gson gson = gsonBuilder.create();
        Domain domain = gson.fromJson(json, Domain.class);
        domain.updateSynonyms();
        domain.getOperations().forEach(Synonyms::updateSynonyms);
        return domain;
    }

    @Override
    public String toString() {
        return "Domain{" +
                "words" + getWords() +
                "\nfriendlyNames=" + friendlyNames +
                "\noperations=" + operations +
                '}';
    }

    /*
     * Noisy java methods
     */

    public void setOperations(Set<Operation> operations) {
        this.operations = operations;
    }

    public Set<Operation> getOperations() {
        return operations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Domain domain = (Domain) o;
        return friendlyNames != null ? friendlyNames.equals(domain.friendlyNames) : domain.friendlyNames == null && (operations != null ? operations.equals(domain.operations) : domain.operations == null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (friendlyNames != null ? friendlyNames.hashCode() : 0);
        result = 31 * result + (operations != null ? operations.hashCode() : 0);
        return result;
    }

    public Set<String> getFriendlyNames() {
        return friendlyNames;
    }
}
