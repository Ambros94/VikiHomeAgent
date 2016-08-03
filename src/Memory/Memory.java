package Memory;

import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

public class Memory<V> {

    private Map<String, V> memory;
    private WordVectors vectors;


    public Memory(WordVectors vectors, String s) {
        this.memory = new HashMap<>();
        this.vectors = vectors;
    }

    public boolean remind(String sentence, V rightThing) {
        return memory.put(sentence, rightThing) == null;
    }

    public V isInMemory(String sentence) {
        System.err.println(memory);
        return memory.get(sentence);
    }

    double sentenceSimilarity(List<String> firstSentence, List<String> secondSentence) {
        double averageConfidence = 0;
        for (String split : secondSentence) {
            OptionalDouble value = firstSentence.stream().mapToDouble(word -> vectors.similarity(split, word)).max();
            averageConfidence += (value.isPresent()) ? value.getAsDouble() : 0d;
        }
        averageConfidence /= secondSentence.size();
        return averageConfidence;
    }
}
