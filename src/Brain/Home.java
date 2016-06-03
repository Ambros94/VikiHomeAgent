package Brain;

import Things.Domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represent a whole home, containsOperation a list of domains, on which operations can be requested
 */
public class Home {

    private final Set<Domain> domains;
    private final Brain brain;

    public Home(Set<Domain> domains) {
        this.domains = domains;
        brain = new Brain(domains);
    }

    public Home(String jsonThings) {
        this.domains = new HashSet<>();//TODO Parse the JSON
        brain = new Brain(domains);
    }

    public List<Command> textCommand(String text) throws Exception {
        return brain.textCommand(text);
    }
}
