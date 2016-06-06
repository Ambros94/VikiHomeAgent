package Brain;

import NLP.Graph;
import NLP.StanfordNLPGraph;
import Things.Domain;
import Things.Operation;
import Things.Parameter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represent a whole home, containsOperation a list of domains, on which operations can be requested
 */
public class Home {

    private final Set<Domain> domains;

    public Home(Set<Domain> domains) {
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
}
