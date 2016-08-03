package NLP.DomainOperationsFinders;


import Brain.DomainOperationPair;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;

import java.util.List;

/**
 * Interface for Classes able to determine if a domain and his operation are involved in a sentence.
 * And it gives a confidence value between 0 and 1 (can be a little more for double rounding)
 */
public interface DomainOperationFinder {

    WordVectors getWordVectors();

    /**
     * @param text String representing a Command, where operations and relative Domains are mentioned (even with synonyms of paraphrases)
     * @return List of DomainOperationPair founded, they contain confidence.
     */
    List<DomainOperationPair> find(String text);


}
