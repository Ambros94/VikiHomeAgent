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
                "'operations': " +
                "[" +
                "{'id': 'turn_off'," +
                "'words': ['turn_off']" +
                "}" +
                "]" +
                "}";
        Domain json = Domain.fromJson(s);
        /**
         * Build expected object
         */
        Domain expected = new Domain("lampada", Collections.singleton("lamp"));
        Operation turnoff = new Operation("turn off", Collections.singleton("turn_off"));
        Set<Operation> operationList = new HashSet<>();
        operationList.add(turnoff);
        expected.setOperations(operationList);
        /**
         * Assert
         */
        System.out.println("cose");
        System.out.println(json);
        System.out.println("altre");

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

}