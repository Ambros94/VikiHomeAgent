package Main;

import Brain.Universe;
import Brain.UniverseController;
import Comunication.*;
import NLP.DomainOperationsFinders.DebugDOFinder;
import NLP.ParamFinders.ParametersFinder;
import Utility.Config;
import org.apache.log4j.Logger;

import java.io.IOException;

public class Main {

    private static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {

        Universe universe;
        UniverseController controller;

        // Input
        CommandReceiver input = new WSCommandReceiver(Config.getConfig().getCommandReceiverAddress(), Config.getConfig().getCommandReceiverPort());
        //Output
        CommandSender output = new WSCommandSender();
        try {
            //String json = new UniverseLoader().loadFromFile();
            String json = new UniverseLoader().loadFromRemote();

            //Create the universe
            universe = Universe.fromJson(json);
            logger.info("Loaded universe: " + universe);
            universe.setDomainOperationFinder(DebugDOFinder.build(universe.getDomains()));
            universe.setParametersFinder(ParametersFinder.build());
            // Create the controller
            controller = new UniverseController(universe);
            input.setUniverseController(controller);
            controller.addCommandSender(output);
            /*
            Everything has been set up, we can start IO channels
             */
            output.startSender();
            input.startReceiver();
            while (true) {
                //TODO Maybe do something smarter than that
            }
        } catch (IOException e) {
            logger.error("Cannot load the universe from the selected source");
            logger.error(e.getMessage());
        }

    }
}
