package Brain;

import InstanceCreator.DomainInstanceCreator;
import InstanceCreator.OperationInstanceCreator;
import InstanceCreator.ParameterInstanceCreator;
import NLP.DomainOperationsFinders.DomainOperationFinder;
import NLP.DomainOperationsFinders.SimilarityDOFinder;
import NLP.ParamFinders.FakeParametersFinder;
import NLP.ParamFinders.IParametersFinder;
import NLP.ParamFinders.ParametersFinder;
import Things.Domain;
import Things.Operation;
import Things.Parameter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represent a whole home, containsOperation a list of domains
 */
public class Universe {
    /**
     * Min Value of confidence for operations, operations with lower values will be discarded
     */
    private static final double MIN_CONFIDENCE_LEVEL = 0.8D;
    /**
     * List of domains, representing the whole universe.
     */
    private final Set<Domain> domains;


    public Universe(Set<Domain> domains) {
        this.domains = domains;
    }

    public List<Command> textCommand(String text) {
        /**
         * Transform the String received in input in a structure able to detect Domains, Operations
         */
        List<Command> commandList = new ArrayList<>();
        DomainOperationFinder domainOperationFinder = new SimilarityDOFinder();
        List<DomainOperationPair> domainOperationPairs = domainOperationFinder.find(domains, text);
        /**
         * Remove DomainsOperations with too low confidence
         */
        domainOperationPairs = domainOperationPairs.stream().filter(pair -> pair.getConfidence() > MIN_CONFIDENCE_LEVEL).collect(Collectors.toList());
        /**
         * Find params for high confidence operations and creates relative commands
         */
        IParametersFinder parametersFinder = new FakeParametersFinder();
        commandList.addAll(parametersFinder.findParameters(domainOperationPairs, text));
        return commandList;
    }

    /**
     * @param json Correct JSON that represent the whole universe. See documentation for details about json structure
     * @return Universe instance, hopefully the same ad indicated in the JSON
     */
    public static Universe fromJson(String json) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Parameter.class, new ParameterInstanceCreator());
        gsonBuilder.registerTypeAdapter(Operation.class, new OperationInstanceCreator());
        gsonBuilder.registerTypeAdapter(Domain.class, new DomainInstanceCreator());
        Gson gson = gsonBuilder.create();
        Universe universe = gson.fromJson(json, Universe.class);
        /**
         * Force structure to update Synonyms
         */
        universe.getDomains().forEach(Domain::updateDomainSynonyms);

        return universe;
    }

    /**
     * Noise java methods
     */

    public Set<Domain> getDomains() {
        return domains;
    }

    @Override
    public String toString() {
        return "Universe{" +
                "domains=" + domains +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Universe universe = (Universe) o;

        return domains != null ? domains.equals(universe.domains) : universe.domains == null;

    }

    @Override
    public int hashCode() {
        return domains != null ? domains.hashCode() : 0;
    }
}
