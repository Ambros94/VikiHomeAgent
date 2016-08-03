package Memory;

import edu.stanford.nlp.simple.Sentence;
import org.deeplearning4j.arbiter.util.ClassPathResource;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class MemoryTest {

    private static Memory<String> memory;

    @BeforeClass
    public static void init() throws IOException {
        ClassPathResource resource = new ClassPathResource("word2vec/GoogleNews-vectors-negative300.bin");
        WordVectors wordVectors = WordVectorSerializer.loadGoogleModel(resource.getFile(), true, false);
        memory = new Memory<String>(wordVectors, "");
    }

    @Test
    public void isInMemory() throws Exception {
        memory.remind("key", "This is 'the memory'");
        assertEquals("This is 'the memory'", memory.isInMemory("key"));
        assertNull(memory.isInMemory("any other key"));
    }

    @Test
    public void sentenceSimilarity() throws Exception {
        Sentence firstSentence = new Sentence("it's dark");
        Sentence secondSentence = new Sentence("it is so fucking dark");

        //assertEquals(0.90d, memory.sentenceSimilarity(firstSentence.words(), secondSentence.words()), 0.01d);
        assertEquals(0.90d, memory.sentenceSimilarity(secondSentence.words(), firstSentence.words()), 0.01d);

    }

}