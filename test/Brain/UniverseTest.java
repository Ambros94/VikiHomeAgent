package Brain;

import NLP.DomainOperationsFinders.Word2vecDOFinder;
import NLP.ParamFinders.ParametersFinder;
import Things.Domain;
import Things.Operation;
import org.deeplearning4j.arbiter.util.ClassPathResource;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class UniverseTest {

    private static Universe universe;

    @BeforeClass
    public static void homeBuilding() throws IOException {
        /*
         * Build the universe
         */
        universe = Universe.fromJson(new String(Files.readAllBytes(Paths.get("resources/mock_up/viki.json"))));
        universe.setParametersFinder(ParametersFinder.build());
        ClassPathResource resource = new ClassPathResource("word2vec/GoogleNews-vectors-negative300.bin");
        WordVectors wordVectors = WordVectorSerializer.loadGoogleModel(resource.getFile(), true, false);
        universe.setDomainOperationFinder(Word2vecDOFinder.build(universe.getDomains(),wordVectors));
        System.out.println("Loaded universe" + universe);
    }

    @Test
    public void fromJson() throws Exception {
        final String s = "{'domains':[" +
                "{'id': 'lampada'," +
                "'words': ['lamp']," +
                "'operations': [{'id': 'turn_off','words': ['turn_off']" +
                "}]}]}";
        Universe json = Universe.fromJson(s);
        /*
         * Build expected object
         */
        Domain domain = new Domain("lampada", Collections.singleton("lamp"));
        Operation turnoff = new Operation("turn_off", Collections.singleton("turn_off"));
        Set<Operation> operationList = new HashSet<>();
        operationList.add(turnoff);
        domain.setOperations(operationList);
        Universe expected = Universe.build(Collections.singleton(domain));
        /*
         * Assert
         */
        assertEquals(expected, json);
    }


}