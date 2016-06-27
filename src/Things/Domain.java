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
    private final Set<String> friendlyNames;
    /**
     * Collection of operation that can be performed in this domain
     */
    private Set<Operation> operations;

    public Domain(String id, Set<String> words, Set<Operation> operations) {
        super(id, words);
        friendlyNames = new HashSet<>();
        this.operations = operations;
    }

    public Domain(String id, Set<String> words) {
        super(id, words);
        friendlyNames = new HashSet<>();
        this.operations = new HashSet<>();
    }

    public void setOperations(Set<Operation> operations) {
        this.operations = operations;
    }

    public Set<Operation> getOperations() {
        return operations;
    }

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
                "friendlyNames=" + friendlyNames +
                ", operations=" + operations +
                '}';
    }

    public void updateDomainSynonyms() {
        super.updateSynonyms();
        getOperations().forEach(Synonyms::updateSynonyms);

    }
}
