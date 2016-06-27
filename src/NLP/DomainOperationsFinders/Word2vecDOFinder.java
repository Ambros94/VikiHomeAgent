package NLP.DomainOperationsFinders;

import Brain.DomainOperationPair;
import NLP.Synonyms;
import Things.Domain;
import Things.Operation;
import edu.stanford.nlp.simple.Sentence;
import org.deeplearning4j.arbiter.util.ClassPathResource;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Set;

public class Word2VecDOFinder implements DomainOperationFinder {

    private Set<Domain> domains;
    private WordVectors wordVectors;
    private Logger logger = LoggerFactory.getLogger(Word2VecDOFinder.class);


    private Word2VecDOFinder(Set<Domain> domains) {
        this.domains = domains;
    }

    public static DomainOperationFinder build(Set<Domain> universe) throws IOException {
        Word2VecDOFinder finder = new Word2VecDOFinder(universe);
        finder.loadWordVectors();
        return finder;
    }

    /**
     * Loads WordVectors from file, that can take a while (even 10 minutes on large models)
     */
    private void loadWordVectors() throws IOException {
        logger.info("Started google word2vec model (~1.5Gb)");
        ClassPathResource resource = new ClassPathResource("word2vec/GoogleNews-vectors-negative300.bin");
        wordVectors = WordVectorSerializer.loadGoogleModel(resource.getFile(), true, false);
        logger.info("Loading completed !");
    }

    @Override
    public List<DomainOperationPair> find(String text) {
        List<DomainOperationPair> domainOperationPairs = new ArrayList<>();
        /**
         * Transform the sentence in a Standford Object
         * TODO Maybe remove most common words, is necessary? (Alan)
         */
        Sentence sentence = new Sentence(text);
        List<String> words = sentence.words();//TODO Check if words or lemmas is better ! (Alan)
        /**
         * For each  domain/operation couple find words in the sentence that has the max confidence level
         * Considered that the computational effort is limited every Operation for every Domain is calculated
         * If some performance problem will occur the best domain can be found and then search only on his operations
         */
        for (Domain domain : domains) {
            for (Operation operation : domain.getOperations()) {
                DomainOperationPair pair = new DomainOperationPair(domain, operation, Double.MIN_VALUE);
                logger.debug("[Domain] " + domain.getId() + " [Operation] " + operation.getId());
                for (String word : words) {
                    /**
                     * TotalConfidence = Domain confidence + Operation confidence
                     */
                    double confidence = findConfidence(domain, word) + findConfidence(operation, word);
                    if (confidence > pair.getConfidence())
                        pair.setConfidence(confidence);
                }
                logger.debug("Best confidence :" + pair);
                domainOperationPairs.add(pair);
            }
        }
        return domainOperationPairs;
    }


    /**
     * Find the highest similarity between object (That can have more than one word associated) with the given word
     *
     * @param object Object with words that can be compared with the given word
     * @param word   Word that you wanna find similarity with the given object
     * @return Highest confidence level for object and given word match (1 is perfect match, -1 no match)
     */
    private double findConfidence(Synonyms object, String word) {
        // Not lambda version
        double maxConfidence = Double.MIN_VALUE;
        String nearestWord = "ERROR";
        for (String domainWord : object.getWords()) {
            double confidence = wordVectors.similarity(domainWord, word);
            if (confidence > maxConfidence) {
                maxConfidence = confidence;
                nearestWord = domainWord;
            }
        }
        logger.debug(word + "->" + nearestWord + "\t:" + maxConfidence);
        return maxConfidence;
        /*OptionalDouble value = object.getWords().stream().mapToDouble(domainWord -> wordVectors.similarity(domainWord, word)).max();
        if (value.isPresent())
            return value.getAsDouble();
        else
            return -1d;*/
    }

}
