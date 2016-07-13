package Main;

import Brain.Universe;
import Brain.UniverseController;
import Comunication.VikiRemoteLoader;
import Comunication.WSServer;
import GUI.JavaFxGui;
import NLP.DomainOperationsFinders.DebugDOFinder;
import NLP.ParamFinders.ParametersFinder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    /**
     * Model
     * View
     * Controller
     */
    private static Universe universe;
    private static UniverseGui gui;
    private static UniverseController controller;

    public static Scene scene;
    private static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        /**
         * Starts the server able to receive commands
         */
        WSServer socketServer = new WSServer(8887);
        socketServer.addMessageHandler(message -> {
            try {
                controller.submitText(message);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        socketServer.start();
        try {
            /**
             * Load the JSON
             */
            String json = new VikiRemoteLoader().loadFromFile();
            //String json = new VikiRemoteLoader().loadFromRemote();
            /**
             * Build the model
             */
            universe = Universe.fromJson(json);
            universe.setDomainOperationFinder(DebugDOFinder.build(universe.getDomains()));
            universe.setParametersFinder(ParametersFinder.build());
            logger.info("Loaded universe: " + universe);
            /**
             * Build the controller
             */
            controller = new UniverseController(universe);
            /**
             * Create the GUI
             */
            gui = JavaFxGui.getSingleton();
            launch(args);
        } catch (IOException e) {
            logger.error("Cannot load from file");
            logger.error(e.getMessage());
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String path = "resources/layout/main.fxml";
        URL url = new URL("file", null, path);
        BorderPane root = FXMLLoader.load(url);
        primaryStage.setTitle("Viki's Awesome GUI");
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Universe getUniverse() {
        return universe;
    }

    public static UniverseGui getGui() {
        return gui;
    }

    public static UniverseController getController() {
        return controller;
    }
}
