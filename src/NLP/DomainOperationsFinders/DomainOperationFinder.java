package NLP.DomainOperationsFinders;


import Brain.DomainOperationPair;

import java.util.List;

public interface DomainOperationFinder {
    List<DomainOperationPair> find(String text);
}
