package NLP.DomainOperationsFinders;


import Brain.DomainOperationPair;

import java.util.List;

public interface DomainOperationFinder {

    /**
     * @param text String representing a Command, where operations and relative Domains are mentioned (even with synonyms of paraphrases)
     * @return List of DomainOperationPair founded, they contain confidence.
     */
    List<DomainOperationPair> find(String text);


}
