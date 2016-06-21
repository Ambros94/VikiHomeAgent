package NLP.ParamFinders;

import Brain.Command;
import Brain.DomainOperationPair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Builds commands with no parameters
 * TODO Remove this class and implement something that works for real
 */
public class FakeParametersFinder implements IParametersFinder {
    @Override
    public Collection<? extends Command> findParameters(List<DomainOperationPair> domainOperationPairs, String text) {
        Collection<Command> commands = new ArrayList<>();
        domainOperationPairs.forEach(pair -> commands.add(new Command(pair.getDomain(), pair.getOperation(), text, 1.0)));
        return commands;
    }
}
