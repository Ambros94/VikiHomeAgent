package Brain;

import InstanceCreator.DomainInstanceCreator;
import InstanceCreator.OperationInstanceCreator;
import InstanceCreator.ParameterInstanceCreator;
import NLP.DomainOperationsFinders.DomainOperationFinder;
import NLP.ParamFinders.IParametersFinder;
import NLP.Params.Value;
import Things.Domain;
import Things.Operation;
import Things.Parameter;
import Things.ParameterType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.util.*;

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

    private Universe(Set<Domain> domains) {
        this.domains = domains;
    }

    public static Universe build(Set<Domain> domains) throws FileNotFoundException {

        return new Universe(domains);
    }

    public List<Command> textCommand(String text) throws FileNotFoundException {
        List<Command> commandList = new ArrayList<>();
        // Find domain and operations
        List<DomainOperationPair> domainOperationPairs = domainOperationFinder.find(text);
        if (domainOperationPairs.size() == 0)
            return commandList;
        /*
         * Find parameters
         */
        Map<ParameterType, Value> paramValues = parametersFinder.findParameters(domainOperationPairs, text);
        /*
         * Assign parameter to commands
         */
        for (DomainOperationPair pair : domainOperationPairs) {// Create a command for each domainOperationPar
            Command c = new Command(pair.getDomain(), pair.getOperation(), text,pair.getDomainConfidence(),pair.getOperationConfidence());
            for (ParameterType type : paramValues.keySet()) {// Add values to the command
                c.addParamValue(type, paramValues.get(type));
            }
            commandList.add(c);
        }
        /*
         * BOOST confidence
         * If a Color is found, command with Color as mandatory parameter are boosted
         * Pairs without that type of parameter are demoted
         */
        // I have a color. I look for command that has a color in his operations
        // e.g. I found a color and this params has a color
        // DEMOTE Confidence
        paramValues.keySet().stream().filter(type -> paramValues.get(type) != null).forEach(type -> {// I have a color. I look for command that has a color in his operations
            commandList.forEach(command -> {
                for (Parameter parameter : command.getOperation().getMandatoryParameters()) {
                    if (parameter.getType().equals(type)) {// e.g. I found a color and this params has a color
                        command.addBonusConfidence();
                        return;
                    }
                }
                // DEMOTE Confidence
                command.subBonusConfidence();
            });
        });
        return commandList;
    }


    Command findMissingParameters(String text, Command c) {
        if (c.isFullFilled() == null)
            return c;

        Map<ParameterType, Value> paramValues = parametersFinder.findParameters(Collections.singletonList(new DomainOperationPair(c.getDomain(), c.getDomainConfidence(), c.getOperation(), c.getOperationConfidence())), text);
        for (ParameterType type : paramValues.keySet()) // Add values to the command
            c.addParamValue(type, paramValues.get(type));
        return c;
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

    public DomainOperationFinder getDomainOperationFinder() {
        return domainOperationFinder;
    }
}
