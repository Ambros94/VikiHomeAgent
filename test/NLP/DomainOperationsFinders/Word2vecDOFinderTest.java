package NLP.DomainOperationsFinders;

import Brain.Universe;
import Comunication.UniverseLoader;
import org.deeplearning4j.arbiter.util.ClassPathResource;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.junit.BeforeClass;
import org.junit.Test;

public class Word2vecDOFinderTest {

    private static DomainOperationFinder finder;

    @BeforeClass
    public static void build() throws Exception {
        String json = new UniverseLoader().loadFromFile();

        Universe universe = Universe.fromJson(json);
        ClassPathResource resource = new ClassPathResource("word2vec/GoogleNews-vectors-negative300.bin");
        WordVectors wordVectors = WordVectorSerializer.loadGoogleModel(resource.getFile(), true, false);
        finder = Word2vecDOFinder.build(universe.getDomains(),wordVectors);
    }

    @Test
    public void find() throws Exception {
        finder.find("turn on the light");
    }

}