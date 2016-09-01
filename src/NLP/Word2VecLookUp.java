package NLP;

import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;

import java.util.List;
import java.util.OptionalDouble;

public class Word2VecLookUp {

    private final WordVectors wordVectors;

    public Word2VecLookUp(WordVectors wordVectors) {
        this.wordVectors = wordVectors;
    }

    /**
     * @param firstSentenceSplitted  List of string, representing the first sentence, yet tokenized
     * @param secondSentenceSplitted List of string, representing the second sentence, yet tokenized
     * @return Calculates the max similarity for each word in the first list, with every word in the second list.
     * Returns the average max similarity for words in the first list
     */
    public double similarity(List<String> firstSentenceSplitted, List<String> secondSentenceSplitted) {
        double averageConfidence = 0;
        for (String split : firstSentenceSplitted) {
            OptionalDouble value = secondSentenceSplitted.stream().mapToDouble(word -> wordVectors.similarity(split.toLowerCase(), word.toLowerCase())).max();
            averageConfidence += (value.isPresent()) ? value.getAsDouble() : 0d;
        }
        averageConfidence /= firstSentenceSplitted.size();
        return averageConfidence;
    }

}
