package NLP.DomainOperationsFinders;

import Brain.DomainOperationPair;
import Things.Domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class DebugDOFinder implements DomainOperationFinder {

    private final Set<Domain> domains;

    private DebugDOFinder(Set<Domain> domains) {
        this.domains = domains;
    }

    public static DomainOperationFinder build(Set<Domain> universe) throws IOException {
        return new DebugDOFinder(universe);
    }

    @Override
    public List<DomainOperationPair> find(String text) {
        List<DomainOperationPair> domainOperationPairs = new ArrayList<>();
        for (Domain domain : domains) {
            domainOperationPairs.addAll(domain.getOperations().stream().map(operation -> new DomainOperationPair(domain, operation, 1.0)).collect(Collectors.toList()));
        }
        return domainOperationPairs;
    }
}