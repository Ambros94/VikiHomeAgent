package DebugMain;

import Brain.Command;
import Brain.Home;
import Things.Domain;
import Things.Operation;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        /**
         * Populate the home with devices and operations
         */
        Set<Domain> domainList = getDomains();
        Home home = new Home(domainList);
        /**
         * Execute command on the brain
         */
        try {
            List<Command> commandList = home.textCommand("Viki, Could you please turn the light on?");
            System.out.println("Command List:" + commandList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Set<Domain> getDomains() {
        Set<Domain> domainList = new HashSet<>();
        Domain t = new Domain("lampada", Collections.singleton("light"));
        Operation turnon = new Operation("turn on", Collections.singleton("turn_on"));
        Operation turnoff = new Operation("turn off", Collections.singleton("turn_off"));
        Set<Operation> operationList = new HashSet<>();
        operationList.add(turnoff);
        operationList.add(turnon);
        t.setOperations(operationList);
        domainList.add(t);
        return domainList;
    }
}
