package DebugMain;

import Brain.Universe;
import Brain.UniverseController;
import GUI.JavaFxGui;
import NLP.DomainOperationsFinders.Word2vecDOFinder;
import NLP.ParamFinders.ParametersFinder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            /**
             * Build the model
             */
            universe = Universe.fromJson(new String(Files.readAllBytes(Paths.get("resources/mock_up/viki.json"))));
            universe.setDomainOperationFinder(Word2vecDOFinder.build(universe.getDomains()));
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
