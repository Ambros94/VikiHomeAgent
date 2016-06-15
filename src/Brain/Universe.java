package Brain;

import InstanceCreator.DomainInstanceCreator;
import InstanceCreator.OperationInstanceCreator;
import InstanceCreator.ParameterInstanceCreator;
import NLP.Graph;
import NLP.StanfordNLPGraph;
import NLP.Synonyms;
import Things.Domain;
import Things.Operation;
import Things.Parameter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represent a whole home, containsOperation a list of domains, on which operations can be requested
 */
public class Universe {

    private final Set<Domain> domains;

    public Universe(Set<Domain> domains) {
        this.domains = domains;
    }

    public List<Command> textCommand(String text) throws Exception {
        /**
         * Transform the String received in input in a structure able to detect Domains, Operations and Parameters
         */
        Graph graph = new StanfordNLPGraph(text);
        List<Command> commandList = new ArrayList<>();
        int domainIndex, operationIndex;
        for (Domain domain : domains) {
            if ((domainIndex = graph.containsDomain(domain)) != -1) {
                /**
                 * Domains signals has been found in the graph, so we look for operations
                 */
                System.out.println("[INFO] Domain '" + domain.getId() + "' found");
                for (Operation operation : domain.getOperations()) {
                    System.out.println("[INFO] Operation '" + operation.getId());
                    if ((operationIndex = graph.containsOperation(operation, domain, domainIndex)) != -1) {
                        /**
                         * Operation signal in the right domain has been found, we create a command and we look for eventual params
                         */
                        System.out.println(" -> FOUND");
                        findParameters(graph, commandList, domainIndex, operationIndex, operation, domain);
                    } else {
                        System.out.println(" -> NOT found");
                    }
                }
            }
        }
        return commandList;
    }

    private void findParameters(Graph graph, List<Command> commands, int domainIndex, int operationIndex, Operation operation, Domain domain) throws Exception {
        Command c = new Command(domain, operation);
        Object value;
        for (Parameter p : operation.getMandatoryParameters()) {//Look for  mandatory parameters
            if ((value = graph.containsParameter(p, operationIndex, domainIndex)) != null) {
                c.addParamValue(new ParamValuePair(p, value));
                throw new Exception("Mandatory parameter missing");
            }
        }
        for (Parameter p : operation.getOptionalParameters()) {//Look for  mandatory parameters
            if ((value = graph.containsParameter(p, operationIndex, domainIndex)) != null) {
                c.addParamValue(new ParamValuePair(p, value));
                throw new Exception("Optional parameter missing");
            }
        }
        commands.add(c);
    }

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
