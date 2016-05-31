package DebugMain;

import Brain.Command;
import Brain.Home;
import Things.Operation;
import Things.Domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        Set<Domain> domainList = new HashSet<>();
        Domain t = new Domain("lamp");
        Operation turnon = new Operation("turn on");
        Operation turnoff = new Operation("turn off");
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
