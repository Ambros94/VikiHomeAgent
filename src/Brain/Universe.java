package Brain;

import InstanceCreator.DomainInstanceCreator;
import InstanceCreator.OperationInstanceCreator;
import InstanceCreator.ParameterInstanceCreator;
import NLP.DomainOperationsFinders.DomainOperationFinder;
import NLP.ParamFinders.IParametersFinder;
import Things.Domain;
import Things.Operation;
import Things.Parameter;
import Utility.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represent a whole universe (e.g. a Home), containsOperation a list of domains
 */
public class Universe {
    /**
     * List of domains, representing the whole universe.
     */
    private Set<Domain> domains;
    private DomainOperationFinder domainOperationFinder;
    private IParametersFinder parametersFinder;
    private final double MIN_CONFIDENCE = Config.getConfig().getMinConficence();

    private Universe(Set<Domain> domains) {
        this.domains = domains;
    }

    public static Universe build(Set<Domain> domains) throws FileNotFoundException {
        return new Universe(domains);
    }

    List<Command> textCommand(String text) throws FileNotFoundException {
        /*
         * Transform the String received in input in a structure able to detect Domains, Operations
         */
        List<Command> commandList = new ArrayList<>();
        List<DomainOperationPair> domainOperationPairs = domainOperationFinder.find(text);
        /*
         * Remove DomainsOperations with too low confidence
         */
        if (domainOperationPairs.size() == 0)
            return commandList;

        domainOperationPairs = domainOperationPairs.stream()
                .filter(pair -> pair.getConfidence() > MIN_CONFIDENCE)
                .sorted((p1, p2) -> Double.compare(p2.getConfidence(), p1.getConfidence()))
                .collect(Collectors.toList());
        /*
         * Find params for high confidence operations and creates relative commands
         */
        Collection<Command> commands = parametersFinder.findParameters(domainOperationPairs, text);
        commandList.addAll(commands);
        return commandList;
    }

    Command bestCommand(String text) throws FileNotFoundException {
        List<Command> commands = textCommand(text);
        if (commands.size() == 0)
            return null;
        return commands.get(0);
    }

    Command findMissingParameters(String text, Command c) {
        /*
         * Command is yet fullFilled
         */
        if (c.isFullFilled())
            return c;
        ArrayList<Command> commands = new ArrayList<>(parametersFinder.findParameters(Collections.singletonList(new DomainOperationPair(c.getDomain(), c.getOperation(), c.getConfidence())), text));
        return commands.get(0);
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
        universe.getDomains().forEach(Domain::updateDomainSynonyms);
        return universe;
    }

    /*
     * Noise java methods
     */

    public Set<Domain> getDomains() {
        return domains;
    }

    public void setDomainOperationFinder(DomainOperationFinder domainOperationFinder) {
        this.domainOperationFinder = domainOperationFinder;
    }

    public void setParametersFinder(IParametersFinder parametersFinder) {
        this.parametersFinder = parametersFinder;
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
