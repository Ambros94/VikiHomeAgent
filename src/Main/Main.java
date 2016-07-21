package Main;

import Brain.Universe;
import Brain.UniverseController;
import Comunication.*;
import GUI.JavaFxGui;
import NLP.DomainOperationsFinders.Word2vecDOFinder;
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
        CommandSender cs;
        /*
         * Command receivers
         */
        JavaFxGui gui = new JavaFxGui();
        CommandHandler commandHandler = stringCommand -> {
            JavaFxGui.scene.setCursor(Cursor.DEFAULT);
            try {
                controller.submitText(stringCommand);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            JavaFxGui.scene.setCursor(Cursor.WAIT);
        };
        gui.addCommandHandler(commandHandler);
        CommandReceiver socketServer = new WSCommandReceiver();
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
            logger.info("Loaded universe: " + universe);
            universe.setDomainOperationFinder(Word2vecDOFinder.build(universe.getDomains()));
            universe.setParametersFinder(ParametersFinder.build());
            /*
             * Build the controller
             */
            controller = new UniverseController(universe);
            cs = new WSCommandSender();
            controller.addCommandSender(cs);
            /*
             * The system is ready, we can startReceiver gui (That is a command receiver) and a socket.io command receiver
             */
            socketServer.startReceiver();
            cs.startSender();
            gui.startReceiver();
            /*
             *  The gui has been closed
             */
            cs.stopSender();
            socketServer.stopReceiver();
        } catch (IOException e) {
            logger.error("Cannot load from file");
            logger.error(e.getMessage());
        }

    }

    public static Universe getUniverse() {
        return universe;
    }

    public static UniverseController getController() {
        return controller;
    }
}
