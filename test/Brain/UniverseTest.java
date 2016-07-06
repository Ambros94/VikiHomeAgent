package Brain;

import NLP.DomainOperationsFinders.Word2vecDOFinder;
import NLP.ParamFinders.ParametersFinder;
import Things.Domain;
import Things.Operation;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UniverseTest {

    private static Universe universe;

    @BeforeClass
    public static void homeBuilding() throws IOException {
        /**
         * Build the universe
         */
        universe = Universe.fromJson(new String(Files.readAllBytes(Paths.get("resources/mock_up/viki.json"))));
        universe.setParametersFinder(ParametersFinder.build());
        universe.setDomainOperationFinder(Word2vecDOFinder.build(universe.getDomains()));
        System.out.println("Loaded universe" + universe);
    }

    /**
     * TODO this little test is awful and useless, will be improved when something real is implemented
     **/
    @Test
    public void textCommand() throws Exception {
        List<Command> commandList = universe.textCommand("Could you please turn on the light?");
        assertTrue(commandList.size() >= 1);
    }

    @Test
    public void fromJson() throws Exception {
        final String s = "{'domains':[" +
                "{'id': 'lampada'," +
                "'words': ['lamp']," +
                "'operations': [{'id': 'turn_off','words': ['turn_off']" +
                "}]}]}";
        Universe json = Universe.fromJson(s);
        /**
         * Build expected object
         */
        Domain domain = new Domain("lampada", Collections.singleton("lamp"));
        Operation turnoff = new Operation("turn_off", Collections.singleton("turn_off"));
        Set<Operation> operationList = new HashSet<>();
        operationList.add(turnoff);
        domain.setOperations(operationList);
        Universe expected = Universe.build(Collections.singleton(domain));
        /**
         * Assert
         */
        assertEquals(expected, json);
    }

    @Test
    public void fullFill() throws FileNotFoundException {
        Command c = universe.bestCommand("Could you please set the light intensity");
        assertEquals("Command{operation=Operation{id=setIntensitywords[setIntensity], optionalParameters=[], mandatoryParameters=[Parameter{id='intensity', type=NUMBER}], textInvocation=[Set light intensity to 80%]}, domain=Domain{words[lamp, light]friendlyNames=[], operations=[Operation{id=turnOffwords[turnOff], optionalParameters=[], mandatoryParameters=[], textInvocation=[Could you please turn off the light?]}, Operation{id=turnOnwords[turnOn], optionalParameters=[], mandatoryParameters=[], textInvocation=[Could you please turn on the light?]}, Operation{id=isOnwords[isOn], optionalParameters=[], mandatoryParameters=[], textInvocation=[Is the light on?]}, Operation{id=isOffwords[isOff], optionalParameters=[], mandatoryParameters=[], textInvocation=[Is the light off?]}, Operation{id=setIntensitywords[setIntensity], optionalParameters=[], mandatoryParameters=[Parameter{id='intensity', type=NUMBER}], textInvocation=[Set light intensity to 80%]}, Operation{id=setColorwords[setColor], optionalParameters=[], mandatoryParameters=[Parameter{id='color', type=COLOR}], textInvocation=[Set light color to red]}, Operation{id=getColorwords[getColor], optionalParameters=[], mandatoryParameters=[], textInvocation=[Which color is the lamp]}]}, pairs=[], confidence=0.8887559324502945}", c.toString());
        Command fullFilled = universe.findMissingParameters("I want the intensity at 80.4", c);
        assertEquals("Command{operation=Operation{id=setIntensitywords[setIntensity], optionalParameters=[], mandatoryParameters=[Parameter{id='intensity', type=NUMBER}], textInvocation=[Set light intensity to 80%]}, domain=Domain{words[lamp, light]friendlyNames=[], operations=[Operation{id=turnOffwords[turnOff], optionalParameters=[], mandatoryParameters=[], textInvocation=[Could you please turn off the light?]}, Operation{id=turnOnwords[turnOn], optionalParameters=[], mandatoryParameters=[], textInvocation=[Could you please turn on the light?]}, Operation{id=isOnwords[isOn], optionalParameters=[], mandatoryParameters=[], textInvocation=[Is the light on?]}, Operation{id=isOffwords[isOff], optionalParameters=[], mandatoryParameters=[], textInvocation=[Is the light off?]}, Operation{id=setIntensitywords[setIntensity], optionalParameters=[], mandatoryParameters=[Parameter{id='intensity', type=NUMBER}], textInvocation=[Set light intensity to 80%]}, Operation{id=setColorwords[setColor], optionalParameters=[], mandatoryParameters=[Parameter{id='color', type=COLOR}], textInvocation=[Set light color to red]}, Operation{id=getColorwords[getColor], optionalParameters=[], mandatoryParameters=[], textInvocation=[Which color is the lamp]}]}, pairs=[ParamValuePair{value=80, parameter=Parameter{id='intensity', type=NUMBER}}], confidence=0.8887559324502945}", fullFilled.toString());
    }


}