package NLP.DomainOperationsFinders;

import Brain.DomainOperationPair;
import NLP.Synonyms;
import Things.Domain;
import Things.Operation;
import Utility.CamelCaseStringTokenizer;
import edu.stanford.nlp.simple.Sentence;
import org.apache.log4j.Logger;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Set;

/**
 * DomainFinder that uses word2vec + WordNet to determine similarities between the sentence and Domain,Operation
 * Sentence word order is not taken into account, the method is based on multiple word similarities
 */
public class Word2vecDOFinder implements DomainOperationFinder {

    private Set<Domain> domains;
    private WordVectors wordVectors;
    private Logger logger = Logger.getLogger(Word2vecDOFinder.class);


    private Word2vecDOFinder(Set<Domain> domains, WordVectors wordVectors) {
        this.domains = domains;
        this.wordVectors = wordVectors;
    }

    public static DomainOperationFinder build(Set<Domain> universe, WordVectors wordVectors) throws IOException {
        return new Word2vecDOFinder(universe, wordVectors);
    }


    @Override
    public List<DomainOperationPair> find(String text) {
        List<DomainOperationPair> domainOperationPairs = new ArrayList<>();
        /*
         * Transform the sentence in a Standford Object
         */
        Sentence sentence = new Sentence(text);
        List<String> words = sentence.lemmas();
        /*
         * For each  domain/operation couple find words in the sentence that has the max confidence level
         * Considered that the computational effort is limited every Operation for every Domain is calculated
         * If some performance problem will occur the best domain can be found and then search only on his operations
         */
        for (Domain domain : domains) {
            double domainConfidence = findMaxConfidence(domain, words);
            //logger.debug(String.format("Domain %s confidence %f", domain.getId(), domainConfidence));
            for (Operation operation : domain.getOperations()) {
                double operationConfidence = findMaxConfidence(operation, words);
                //logger.debug(String.format("Operation %s confidence %f", operation.getId(), operationConfidence));
                /*
                 * Highest confidence found for this DomainOperationPair
                 */
                domainOperationPairs.add(new DomainOperationPair(domain, domainConfidence, operation, operationConfidence));
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
        /*
         * Compare with words, noun synonyms and verb synonyms
         */
        Set<String> objWords = object.getWords();
        if (object instanceof Domain)
            objWords.addAll(((Domain) object).getFriendlyNames());
        /*
         * Find the max confidence
         */
        for (String objWord : objWords) {
            double confidence = findConfidence(objWord, sentenceWords);
            if (confidence > maxConfidence) {
                maxConfidence = confidence;
            }
        }
        //logger.debug(nearestWord + "\t:" + maxConfidence);
        return maxConfidence;

    }


    /**
     * Tokenize the input String and compute the average highest similarity using given sentenceWords
     * e.g. setColor, confidence is calculated using the average between highest set confidence + highest color confidence
     *
     * @param objWord       String that will be split on camelCase
     * @param sentenceWords Sentence where similarity is searched
     * @return Highest confidence level for object and given sentence match (1 is perfect match, 0 no match)
     */
    private double findConfidence(String objWord, List<String> sentenceWords) {
        String[] splitObjWord = new CamelCaseStringTokenizer().tokenize(objWord);
        double averageConfidence = 0;
        for (String split : splitObjWord) {
            OptionalDouble value = sentenceWords.stream().mapToDouble(word -> wordVectors.similarity(split, word)).max();
            averageConfidence += (value.isPresent()) ? value.getAsDouble() : 0d;
        }
        averageConfidence /= splitObjWord.length;
        return averageConfidence;
    }
}
