package LearningAlgorithm;

import Brain.Command;
import Things.Domain;
import Things.Operation;
import Things.Parameter;
import Things.ParameterType;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;


public class CommandLoggerTest {

    private static Command command;
    private static CommandLogger logger;

    @BeforeClass
    public static void init() {
        Operation operation = new Operation("turn on", Collections.singleton("turn_on"));
        Domain domain = new Domain("light", Collections.singleton("lamp"));
        Parameter p = new Parameter("Colore", ParameterType.COLOR);
        operation.setOptionalParameters(Collections.singleton(p));
        command = new Command(domain, operation, "Test", 0);
        logger=new CommandLogger();
    }

    @Test
    public void logRightTest() throws Exception {

        logger.logRight(command);
    }

    @Test
    public  void logWrongTest() throws Exception {

        logger.logWrong(command);
    }

}