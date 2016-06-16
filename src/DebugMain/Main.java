package DebugMain;

import Brain.Universe;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class Main extends Application {

    public static Universe universe;
    public static Scene scene;

    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class.getSimpleName());
        /**
         * Populate the universe with devices and operations
         */
        try {
            universe = Universe.fromJson(new String(Files.readAllBytes(Paths.get("resources/mokeup/vikiv2.json"))));
            /**
             * Execute command on the brain
             */
            launch(args);
        } catch (IOException e) {
            logger.warning("Cannot load from file");
            logger.warning(e.getMessage());
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
