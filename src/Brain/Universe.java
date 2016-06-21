package Brain;

import InstanceCreator.DomainInstanceCreator;
import InstanceCreator.OperationInstanceCreator;
import InstanceCreator.ParameterInstanceCreator;
import NLP.DomainOperationsFinders.Doc2vecDOFinder;
import NLP.DomainOperationsFinders.DomainOperationFinder;
import NLP.ParamFinders.FakeParametersFinder;
import NLP.ParamFinders.IParametersFinder;
import Things.Domain;
import Things.Operation;
import Things.Parameter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
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
    private static final double MIN_CONFIDENCE_LEVEL = 0.5D;
    private static final int COMMAND_NUMBER = 10;
    /**
     * List of domains, representing the whole universe.
     */
    private Set<Domain> domains;
    private DomainOperationFinder domainOperationFinder;


    private Universe(Set<Domain> domains, DomainOperationFinder domainOperationFinder) {
        this.domainOperationFinder = domainOperationFinder;
        this.domains = domains;
    }

    public static Universe build(Set<Domain> domains) throws FileNotFoundException {
        DomainOperationFinder domainOperationFinder = Doc2vecDOFinder.build(domains);
        return new Universe(domains, domainOperationFinder);
    }

    public List<Command> textCommand(String text) throws FileNotFoundException {
        /**
         * Transform the String received in input in a structure able to detect Domains, Operations
         */
        List<Command> commandList = new ArrayList<>();
        List<DomainOperationPair> domainOperationPairs = domainOperationFinder.find(text);


        /**
         * Remove DomainsOperations with too low confidence, and returns COMMAND_NUMBER commands
         */
        if (domainOperationPairs.size() == 0)
            return commandList;

        domainOperationPairs = domainOperationPairs.stream()
                //.filter(pair -> pair.getConfidence() > MIN_CONFIDENCE_LEVEL)
                .sorted((p1, p2) -> Double.compare(p2.getConfidence(), p1.getConfidence()))
                .collect(Collectors.toList());
        //.subList(0, COMMAND_NUMBER);

        //TODO remove is only debug
        domainOperationPairs.forEach(System.out::println);
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
        try {
            universe.initFinders();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return universe;
    }

    private void initFinders() throws FileNotFoundException {
        domainOperationFinder = Doc2vecDOFinder.build(domains);
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
