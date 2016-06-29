package DebugMain;

import Brain.Universe;
import NLP.DomainOperationsFinders.Word2VecDOFinder;
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

    public static Universe universe;
    public static Scene scene;
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        try {
            /**
             * Populate the universe with devices and operations
             */
            universe = Universe.fromJson(new String(Files.readAllBytes(Paths.get("resources/mock_up/viki.json"))));
            /**
             * Build DomainFinders and ParameterFinders
             */
            universe.setDomainOperationFinder(Word2VecDOFinder.build(universe.getDomains()));
            universe.setParametersFinder(ParametersFinder.build());
            logger.info("Loaded universe: " + universe);
            /**
             * Execute command on the brain
             */
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
        primaryStage.setTitle("Viki's Trainer");
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
