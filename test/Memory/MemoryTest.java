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
        memory = new Memory<String>(wordVectors, "resources/memory/memory.mem");

    }

    @Test
    public void isInMemory() throws Exception {
        System.out.println("THERE SHOULD ME SOMETHING -->" + memory.isInMemory("key"));
        // Remember the same exact thing
        memory.remind("key", "This is in the memory");
        assertEquals("This is in the memory", memory.isInMemory("key"));

        //Remember a similar thing
        memory.remind("it is dark in here", "Turn on the light");
        assertEquals("Turn on the light", memory.isInMemory("it is dark"));

        assertNull(memory.isInMemory("any other key"));
        memory.persistMemory();
    }

    @Test
    public void sentenceSimilarity() throws Exception {
        Sentence firstSentence = new Sentence("it's dark");
        Sentence secondSentence = new Sentence("it is so fucking dark");
        assertEquals(0.70d, memory.sentenceSimilarity(secondSentence.words(), firstSentence.words()), 0.05d);
    }

}