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
        CommandSender executor = new WSCommandSender(Config.getConfig().getVikiAddress());
        CommandSender gui = new WSCommandSender("http://localhost:5678");
        try {
            String json = new UniverseLoader().loadFromFile();
            //String json = new UniverseLoader().loadFromRemote();
            gui.startSender();//TODO They should no be after init, but here i can avoid socket problems
            executor.startSender();
            input.startReceiver();

            //Create the universe
            universe = Universe.fromJson(json);
            logger.info("Loaded universe: " + universe);
            universe.setDomainOperationFinder(DebugDOFinder.build(universe.getDomains()));
            universe.setParametersFinder(ParametersFinder.build());
            // Create the controller
            controller = new UniverseController(universe);
            input.setUniverseController(controller);
            controller.addCommandSender(executor);
            controller.addCommandSender(gui);
            /*
            Everything has been set up, we can start IO channels
             */
            final boolean[] interrupted = {false};
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    System.out.println("Shutdown hook ran!");
                    interrupted[0] = true;
                    input.stopReceiver();
                    executor.stopSender();
                    gui.stopSender();
                }
            });
            while (!interrupted[0]) {

            }
        } catch (IOException e) {
            logger.error("Cannot load the universe from the selected source");
            logger.error(e.getMessage());
        }

    }
}
