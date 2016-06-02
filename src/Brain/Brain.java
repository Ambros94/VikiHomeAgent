package Brain;

import NLP.Graph;
import NLP.NLPGraph;
import Things.Domain;
import Things.Operation;
import Things.Parameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Brain {

    private final Set<Domain> domains;

    public Brain(Set<Domain> domains) {
        this.domains = domains;
    }

    public List<Command> textCommand(String text) throws Exception {
        /**
         * Transform the String received in input in a structure able to detect Domains, Operations and Parameters
         */
        Graph graph = new NLPGraph(text);
        List<Command> commandList = new ArrayList<>();
        for (Domain domain : domains) {
            if (graph.contains(domain)) {
                /**
                 * Domains signals has been found in the graph, so we look for operations
                 */
                System.out.println("[INFO] Domain '" + domain.getId() + "' found");
                for (Operation o : domain.getOperations()) {
                    if (graph.contains(o, domain)) {
                        /**
                         * Operation signal in the right domain has been found, we create a command and we look for eventual params
                         */
                        System.out.println("[INFO] Operation '" + o.getId() + "' found");
                        findParameters(graph, commandList, domain, o);
                    }
                }
            }
        }
        return commandList;
    }

    private void findParameters(Graph graph, List<Command> commandList, Domain domain, Operation o) throws Exception {
        Command c = new Command(domain, o);
        Object value;
        for (Parameter p : o.getMandatoryParameters()) {//Look for  mandatory parameters
            if ((value = graph.contains(p, o, domain)) != null) {
                c.addMandatoryParameter(p, value);
                throw new Exception("Mandatory parameter missing");
            }
        }
        for (Parameter p : o.getOptionalParameters()) {//Look for  mandatory parameters
            if ((value = graph.contains(p, o, domain)) != null) {
                c.addOptionalParameters(p, value);
                throw new Exception("Optional parameter missing");
            }
        }
        commandList.add(c);
    }
}
