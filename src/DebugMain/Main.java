package DebugMain;

import Brain.Command;
import Brain.Home;
import Things.Operation;
import Things.Domain;
import Things.Synonyms;
import edu.mit.jwi.item.POS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Synonyms synonyms = new Synonyms("lamp", POS.NOUN, Collections.singleton("light"));

        Set<Domain> domainList = new HashSet<>();
        Domain t = new Domain("lampada", Collections.singleton("lamp"));
        Operation turnon = new Operation("turn on", Collections.singleton("turn_on"));
        Operation turnoff = new Operation("turn off", Collections.singleton("turn_off"));
        Set<Operation> operationList = new HashSet<>();
        operationList.add(turnoff);
        operationList.add(turnon);
        t.setOperations(operationList);
        domainList.add(t);

        Home home = new Home(domainList);

        try {
            List<Command> commandList = home.textCommand("turn on the lamp");
            System.out.println("Command List:" + commandList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
