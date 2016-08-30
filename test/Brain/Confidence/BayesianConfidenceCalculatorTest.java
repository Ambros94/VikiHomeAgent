package Brain.Confidence;

import org.deeplearning4j.arbiter.util.ClassPathResource;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.junit.Test;

public class BayesianConfidenceCalculatorTest {
    @Test
    public void computeConfidence() throws Exception {
        ClassPathResource resource = new ClassPathResource("word2vec/GoogleNews-vectors-negative300.bin");
        WordVectors wordVectors = WordVectorSerializer.loadGoogleModel(resource.getFile(), true, false);
        BayesianConfidenceCalculator calculator = ConfidenceCalculatorBuilder.getBayesian(wordVectors);

    }

}