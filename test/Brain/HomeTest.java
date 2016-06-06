package Brain;

import Things.Domain;
import Things.Operation;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class HomeTest {

    Home home;

    @Before
    public void beforeMethod() {
        Set<Domain> domainList = new HashSet<>();
        Domain t = new Domain("lampada", Collections.singleton("light"));
        Operation turnon = new Operation("turn on", Collections.singleton("turn_on"));
        Operation ison = new Operation("be on", Collections.singleton("be_on"));
        Set<Operation> operationList = new HashSet<>();
        operationList.add(turnon);
        operationList.add(ison);
        t.setOperations(operationList);
        domainList.add(t);
        home = new Home(domainList);
    }

    @Test
    public void textCommand() throws Exception {
        List<Command> commandList = home.textCommand("Could you please turn on the light?");
        System.out.println(commandList);
    }

}