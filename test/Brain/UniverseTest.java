package Brain;

import NLP.DomainOperationsFinders.Word2VecDOFinder;
import NLP.ParamFinders.ParametersFinder;
import Things.Domain;
import Things.Operation;
import Things.Parameter;
import Things.ParameterType;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UniverseTest {

    private static Universe universe;

    @BeforeClass
    public static void homeBuilding() throws IOException {
        Set<Domain> domainList = new HashSet<>();
        /**
         * Light
         */
        Domain lampada = new Domain("lampada", Collections.singleton("light"));
        Operation turn_on = new Operation("turn on", Collections.singleton("turn_on"));
        Operation turn_off = new Operation("turn off", Collections.singleton("turn_off"));

        Operation is_on = new Operation("be on", Collections.singleton("be_on"));
        Operation is_off = new Operation("be off", Collections.singleton("be_on"));

        Operation get = new Operation("get", Collections.singleton("get"));
        get.setOptionalParameters(new HashSet<>(Arrays.asList(new Parameter("color", ParameterType.COLOR), new Parameter("intensity", ParameterType.NUMBER))));
        Operation set = new Operation("set", Collections.singleton("set"));
        set.setOptionalParameters(new HashSet<>(Arrays.asList(new Parameter("color", ParameterType.COLOR), new Parameter("intensity", ParameterType.NUMBER))));

        lampada.setOperations(new HashSet<>(Arrays.asList(turn_off, turn_on, is_off, is_on, get, set)));
        domainList.add(lampada);
        /**
         * Build the universe
         */
        universe = Universe.build(domainList);
        universe.setParametersFinder(ParametersFinder.build());
        universe.setDomainOperationFinder(Word2VecDOFinder.build(domainList));
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
        System.out.println(s);
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


}