package NLP.DomainOperationsFinders;

import Brain.DomainOperationPair;
import Brain.Universe;
import org.deeplearning4j.arbiter.util.ClassPathResource;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;

import java.io.IOException;
import java.util.List;

public class Word2vecDOFinder implements DomainOperationFinder {

    private static Universe universe;

    private Word2vecDOFinder(Universe universe) {
        this.universe = universe;
    }

    public static Word2vecDOFinder build(Universe universe) {
        return new Word2vecDOFinder(universe);
    }

    static {

    }


    @Override
    public List<DomainOperationPair> find(String text) {
        try {
            System.out.println("Started google word2vec model (1.5Gb)");
            ClassPathResource resource = new ClassPathResource("word2vec/GoogleNews-vectors-negative300.bin");
            WordVectors wordVectors = WordVectorSerializer.loadGoogleModel(resource.getFile(), true, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Loading completed !");
        return null;
    }

}
