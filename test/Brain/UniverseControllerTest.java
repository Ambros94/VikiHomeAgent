package Brain;

import Brain.Confidence.ConfidenceCalculatorBuilder;
import Comunication.UniverseLoader;
import Memory.Memory;
import NLP.DomainOperationsFinders.Word2vecDOFinder;
import NLP.ParamFinders.ParametersFinder;
import org.deeplearning4j.arbiter.util.ClassPathResource;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class UniverseControllerTest {

    private static UniverseController controller;

    @BeforeClass
    public static void init() throws IOException {
        String universeJson = new UniverseLoader().loadFromFile("resources/mock_up/vikiTest.json");
        Universe universe = Universe.fromJson(universeJson);
        ClassPathResource resource = new ClassPathResource("word2vec/GoogleNews-vectors-negative300.bin");
        WordVectors wordVectors = WordVectorSerializer.loadGoogleModel(resource.getFile(), true, false);
        universe.setDomainOperationFinder(Word2vecDOFinder.build(universe.getDomains(), wordVectors));
        universe.setParametersFinder(ParametersFinder.build());


        // Create the controller
        Memory<Command> memory = new Memory<Command>(wordVectors, "things");

        controller = new UniverseController(universe, memory, ConfidenceCalculatorBuilder.getStatic());
    }

    @Test
    public void successfulCommand() throws Exception {
        //Successful command
        Command turnOnLight = controller.submitText("turn on the sphere");
        assertTrue(turnOnLight.equalsIds("light1", "turnOn"));
        assertEquals(CommandStatus.OK, turnOnLight.getStatus());
    }

    @Test
    public void noCommand() throws Exception {
        //No command in this sentence
        Command nullCommand = controller.submitText("");
        assertNull(nullCommand);
    }

    @Test
    public void lowConfidence() throws Exception {
        //Only low confidence command
        Command lowConfidence = controller.submitText("Is there a command in here?");
        assertEquals(CommandStatus.LOW_CONFIDENCE, lowConfidence.getStatus());
    }

    @Test
    public void missingParameter() throws Exception {
        //Command with missing parameter
        Command emptySetColor = controller.submitText("Set the light color");
        assertTrue(emptySetColor.equalsIds("light1", "setColor"));
        assertEquals(CommandStatus.MISSING_COLOR, emptySetColor.getStatus());
        //Now it is fulfilled
        Command filledSetColor = controller.submitText("red");
        assertTrue(filledSetColor.equalsIds("light1", "setColor"));
        assertEquals(filledSetColor.getParamValue().size(), 1);
        assertEquals(CommandStatus.OK, filledSetColor.getStatus());
    }

    @Test
    public void boost() throws Exception {
        Command wayToBeRedLow = controller.submitText("I want my light to turn red");
        System.out.println(wayToBeRedLow.toJson());
        Command wayToBeRedHigh = controller.submitText("I want my light to turn red");
        System.out.println(wayToBeRedHigh.toJson());
    }

    @Test
    public void teach() throws Exception {
        Command lesson = controller.submitText("It's dark");
        System.out.println(lesson.toJson());
        assertEquals(lesson.getStatus(), CommandStatus.LOW_CONFIDENCE);

        Command teach = controller.submitText("I want to teach you something");
        System.out.println(teach.toJson());
        assertEquals(teach.getStatus(), CommandStatus.TEACH);

        Command turnOn = controller.submitText("switch on the light");
        System.out.println(turnOn.toJson());
        assertEquals(turnOn.getStatus(), CommandStatus.LEARNED);
        assertTrue(turnOn.equalsIds("light1", "turnOn"));

        Command lessonLearned = controller.submitText("It's dark");
        System.out.println(lessonLearned.toJson());
        assertEquals(lessonLearned.getStatus(), CommandStatus.OK);
        assertTrue(lessonLearned.equalsIds("light1", "turnOn"));

    }


}
