package NLP;

import org.deeplearning4j.arbiter.util.ClassPathResource;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.junit.Test;

import java.util.Arrays;

public class Word2VecLookUpTest {
    @Test
    public void similarity() throws Exception {
        ClassPathResource resource = new ClassPathResource("word2vec/GoogleNews-vectors-negative300.bin");
        WordVectors wordVectors = WordVectorSerializer.loadGoogleModel(resource.getFile(), true, false);
        Word2VecLookUp lookUp = new Word2VecLookUp(wordVectors);
        System.out.println(lookUp.similarity(Arrays.asList("the","desk","lamp"),Arrays.asList("i","want","to","turn","on","the","desk","lamp")));
        System.out.println(lookUp.similarity(Arrays.asList("the","desk","lamp"),Arrays.asList("i","want","to","turn","on","the","desk","light")));
    }

}