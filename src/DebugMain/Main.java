package DebugMain;

import Brain.Command;
import Brain.Universe;
import Things.Domain;
import Things.Operation;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        /**
         * Populate the universe with devices and operations
         */
        Set<Domain> domainList = getDomains();
        Universe universe = new Universe(domainList);
        /**
         * Execute command on the brain
         */
        try {
            List<Command> commandList = universe.textCommand("turn on the light");
            System.out.println("Command List:" + commandList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Set<Domain> getDomains() {
        Set<Domain> domainList = new HashSet<>();
        Domain t = new Domain("lampada", Collections.singleton("light"));
        Operation turnon = new Operation("turn on", Collections.singleton("turn_on"));
        Operation ison = new Operation("be on", Collections.singleton("be_on"));
        Set<Operation> operationList = new HashSet<>();
        operationList.add(turnon);
        operationList.add(ison);
        t.setOperations(operationList);
        domainList.add(t);
        return domainList;
    }
}
