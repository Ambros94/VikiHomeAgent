package Things;

import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;


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
        for(Operation operation:operationList)
            assertTrue(t.getOperations().contains(operation));

    }
    @Test
    public void constructors(){
        Domain t = new Domain("lampada", Collections.singleton("lamp"));
        Domain t2 = new Domain("lampada", Collections.singleton("lamp"),Collections.singleton("palla"));

    }

}