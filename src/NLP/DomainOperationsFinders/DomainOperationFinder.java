package NLP.DomainOperationsFinders;


import Brain.DomainOperationPair;
import Things.Domain;

import java.util.List;
import java.util.Set;

public interface DomainOperationFinder {
    List<DomainOperationPair> find(Set<Domain> domains, String text);
}
