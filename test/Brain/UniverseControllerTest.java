package Brain;

import Comunication.UniverseLoader;
import NLP.DomainOperationsFinders.Word2vecDOFinder;
import NLP.ParamFinders.ParametersFinder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class UniverseControllerTest {

    private static UniverseController controller;

    @BeforeClass
    public static void init() throws IOException {
        String universeJson = new UniverseLoader().loadFromFile("resources/mock_up/vikiTest.json");
        Universe universe = Universe.fromJson(universeJson);
        universe.setDomainOperationFinder(Word2vecDOFinder.build(universe.getDomains()));
        universe.setParametersFinder(ParametersFinder.build());

        // Create the controller
        controller = new UniverseController(universe);
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
        assertEquals(CommandStatus.MISSING_PARAMETERS, emptySetColor.getStatus());
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
    public void getterSetter() throws Exception {
    }
}