package Memory;

import Utility.Config;
import edu.stanford.nlp.simple.Sentence;
import org.apache.log4j.Logger;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

public class Memory<V extends Serializable> {

    private Map<String, V> memory;
    private WordVectors vectors;
    private String path;

    private final Logger logger = Logger.getLogger(Memory.class);


    public Memory(WordVectors vectors, String s) {
        this.path = s;
        this.memory = new HashMap<>();
        try {
            readMemory(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.vectors = vectors;
    }

    @SuppressWarnings("unchecked")
    private void readMemory(String s) throws IOException {
        ObjectInputStream objectinputstream = null;
        try {
            FileInputStream streamIn = new FileInputStream(s);
            objectinputstream = new ObjectInputStream(streamIn);
            memory = (Map<String, V>) objectinputstream.readObject();
            logger.info("Memory loaded from file :" + memory);
        } catch (Exception e) {
            logger.warn("Empty memory or something bad occurred during memory loading");
        } finally {
            if (objectinputstream != null) {
                objectinputstream.close();
            }
        }
    }

    public boolean remind(String sentence, V rightThing) {
        return memory.put(sentence.toLowerCase(), rightThing) == null;
    }

    public V isInMemory(String sentence) {
        sentence = sentence.toLowerCase();
        double MIN_LEARNING_CONFIDENCE = Config.getConfig().getLearningRate();
        // Split the given sentence only once
        List<String> sentenceWords = new Sentence(sentence).words();
        // Find if a memory is similar to the given sentence
        for (String key : memory.keySet()) {
            List<String> keyWords = new Sentence(key).words();
            if (sentenceSimilarity(keyWords, sentenceWords) > MIN_LEARNING_CONFIDENCE) {
                return memory.get(key);
            }
        }
        // The system does not remember anything similar, return null
        return null;
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

    public void persistMemory() throws IOException {
        ObjectOutputStream oos = null;
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(path, true);
            oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(memory);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (oos != null) {
                oos.close();
            }
        }
    }
}
