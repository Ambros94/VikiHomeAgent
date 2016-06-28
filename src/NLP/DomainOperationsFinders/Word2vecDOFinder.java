package NLP.DomainOperationsFinders;

import Brain.DomainOperationPair;
import GUI.Utility;
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
         * TODO Maybe remove most common words improve performance, is necessary? (Alan)
         */
        Sentence sentence = new Sentence(text);
        List<String> words = sentence.lemmas();//TODO Check if words or lemmas is better ! (Alan)
        /**
         * For each  domain/operation couple find words in the sentence that has the max confidence level
         * Considered that the computational effort is limited every Operation for every Domain is calculated
         * If some performance problem will occur the best domain can be found and then search only on his operations
         */
        for (Domain domain : domains) {
            for (Operation operation : domain.getOperations()) {
                logger.debug("[Domain] " + domain.getId() + " [Operation] " + operation.getId());
                /**
                 * TotalConfidence = Domain confidence + Operation confidence
                 */
                double confidence = findMaxConfidence(domain, words) + findMaxConfidence(operation, words);
                /**
                 * Normalize between -1 : 1
                 */
                confidence /= 2;
                /**
                 * Highest confidence found for this DomainOperationPair
                 */
                domainOperationPairs.add(new DomainOperationPair(domain, operation, confidence));
            }
        }
        return domainOperationPairs;
    }


    /**
     * Find the highest similarity between object (That can have more than one word associated) in the given sentence
     *
     * @param object        Object with words that can be compared with the given word
     * @param sentenceWords Sentence where you wanna find similarity with the given object
     * @return Highest confidence level for object and given sentence match (1 is perfect match, -1 no match)
     */
    private double findMaxConfidence(Synonyms object, List<String> sentenceWords) {
        double maxConfidence = Double.MIN_VALUE;
        String nearestWord = "";
        for (String objWord : object.getWords()) {
            double confidence = findConfidence(objWord, sentenceWords);
            if (confidence > maxConfidence) {
                maxConfidence = confidence;
                nearestWord = objWord;
            }
        }
        logger.debug(nearestWord + "\t:" + maxConfidence);
        return maxConfidence;

    }

    /**
     * Tokenize the input String and compute the average highest similarity using given sentenceWords
     * e.g. setColor, confidence is calculated using the average between highest set confidence + highest color confidence
     *
     * @param objWord       String that will be split on camelCase
     * @param sentenceWords Sentence where similarity is searched
     * @return Highest confidence level for object and given sentence match (1 is perfect match, -1 no match)
     */
    private double findConfidence(String objWord, List<String> sentenceWords) {
        String[] splitObjWord = Utility.tokenizeCamel(objWord);
        double averageConfidence = 0;
        for (String split : splitObjWord) {
            OptionalDouble value = sentenceWords.stream().mapToDouble(word -> wordVectors.similarity(split, word)).max();
            averageConfidence += (value.isPresent()) ? value.getAsDouble() : -1d;
        }
        averageConfidence /= splitObjWord.length;
        return averageConfidence;

    }

}
