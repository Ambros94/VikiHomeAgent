package Things;

import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class DomainTest {
    @Test
    public void setOperations() throws Exception {
        Domain t = new Domain("lampada", Collections.singleton("lamp"));
        Operation turnon = new Operation("turn on", Collections.singleton("turn_on"));
        Operation turnoff = new Operation("turn off", Collections.singleton("turn_off"));
        Set<Operation> operationList = new HashSet<>();
        operationList.add(turnoff);
        operationList.add(turnon);
        t.setOperations(operationList);
        for (Operation operation : operationList)
            assertTrue(t.getOperations().contains(operation));

    }

    @Test
    public void fromJson() throws Exception {
        final String s = "{" +
                "'id': 'lampada'," +
                "'words': ['light','lamp']," +
                "'friendlyNames':['nomeAmico']," +
                "'operations': " +
                "[" +
                "{'id': 'turn_off'," +
                "'words': ['turn_off']" +
                "}" +
                "]" +
                "}";
        Domain json = Domain.fromJson(s);
        Set<String> friendlyNames = json.getFriendlyNames();
        assertEquals(new HashSet<>(Collections.singletonList("nomeAmico")), friendlyNames);
        /*
         * Build expected object
         */
        Domain expected = new Domain("lampada", Collections.singleton("lamp"));
        Operation turnoff = new Operation("turn off", Collections.singleton("turn_off"));
        expected.setFriendlyNames(new HashSet<>(Collections.singletonList("nomeAmico")));
        Set<Operation> operationList = new HashSet<>();
        operationList.add(turnoff);
        expected.setOperations(operationList);
        /*
         * Assert
         */
        assertEquals(expected, json);
    }

    @Test
    public void constructors() {
        Operation turnon = new Operation("turn on", Collections.singleton("turn_on"));
        Operation turnoff = new Operation("turn off", Collections.singleton("turn_off"));
        Set<Operation> operationList = new HashSet<>();
        operationList.add(turnoff);
        operationList.add(turnon);
        Domain t = new Domain("lampada", Collections.singleton("lamp"), operationList);
        assertEquals(operationList, t.getOperations());
    }

    @Test
    public void syn() {
        Operation turnon = new Operation("turn on", Collections.singleton("turn_on"));
        Operation turnoff = new Operation("turn off", Collections.singleton("turn_off"));
        Set<Operation> operationList = new HashSet<>();
        operationList.add(turnoff);
        operationList.add(turnon);
        Domain t = new Domain("lampada", Collections.singleton("lamp"), operationList);
        t.updateDomainSynonyms();
    }

    @Test
    public void hash() {
        Operation turnon = new Operation("turn on", Collections.singleton("turn_on"));
        Operation turnoff = new Operation("turn off", Collections.singleton("turn_off"));
        Set<Operation> operationList = new HashSet<>();
        operationList.add(turnoff);
        operationList.add(turnon);
        Domain t = new Domain("lampada", Collections.singleton("lamp"), operationList);
        assertEquals(t.hashCode(), t.hashCode());
    }

    @Test
    public void string() {
        Operation turnon = new Operation("turn on", Collections.singleton("turn_on"));
        Operation turnoff = new Operation("turn off", Collections.singleton("turn_off"));
        Set<Operation> operationList = new HashSet<>();
        operationList.add(turnoff);
        operationList.add(turnon);
        String expected = "Domain{words[lamp]\n" +
                "friendlyNames=[]\n" +
                "operations=[Operation{id=turn onwords[turn_on], optionalParameters=[], mandatoryParameters=[], textInvocation=[]}, Operation{id=turn offwords[turn_off], optionalParameters=[], mandatoryParameters=[], textInvocation=[]}]}";
        Domain t = new Domain("lampada", Collections.singleton("lamp"), operationList);
        System.out.println(t.toString());
        assertEquals(expected, t.toString());
    }

}