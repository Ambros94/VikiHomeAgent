package Main;

import Brain.Universe;
import Brain.UniverseController;
import Comunication.CommandReceiver;
import Comunication.UniverseLoader;
import Comunication.WSCommandReceiver;
import Comunication.WSCommandSender;
import GUI.JavaFxGui;
import NLP.DomainOperationsFinders.DebugDOFinder;
import NLP.ParamFinders.ParametersFinder;
import javafx.scene.Cursor;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    /*
     * Model
     * View
     * Controller
     */
    private static Universe universe;
    private static UniverseController controller;

    private static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        /*
         * Command receivers
         */
        JavaFxGui gui = new JavaFxGui();
        gui.addCommandHandler(message -> {
            JavaFxGui.scene.setCursor(Cursor.DEFAULT);
            try {
                controller.submitText(message);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            JavaFxGui.scene.setCursor(Cursor.WAIT);
        });
        CommandReceiver socketServer = new WSCommandReceiver("localhost", 8887);//TODO Export hostname and port in config
        socketServer.addCommandHandler(message -> {
            try {
                System.out.println("Command received from socket.io");
                controller.submitText(message);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        try {
            /*
             * Load the JSON
             */
            String json = new UniverseLoader().loadFromFile();
            //String json = new UniverseLoader().loadFromRemote();
            /*
             * Build the model
             */
            universe = Universe.fromJson(json);
            universe.setDomainOperationFinder(DebugDOFinder.build(universe.getDomains()));
            universe.setParametersFinder(ParametersFinder.build());

            logger.info("Loaded universe: " + universe);
            /*
             * Build the controller
             */
            controller = new UniverseController(universe);
            controller.addCommandSender(new WSCommandSender());
            /*
             * The system is ready, we can startReceiver gui (That is a command receiver) and a socket.io command receiver
             */

            socketServer.startReceiver();
            gui.startReceiver();
        } catch (IOException e) {
            logger.error("Cannot load from file");
            logger.error(e.getMessage());
        }
        socketServer.stopReceiver();
    }

    public static Universe getUniverse() {
        return universe;
    }

    public static UniverseController getController() {
        return controller;
    }
}
