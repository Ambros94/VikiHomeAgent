package GUI;


import Brain.UniverseController;
import Comunication.CommandHandler;
import Comunication.CommandReceiver;
import Comunication.CommandSender;
import Main.Main;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.net.URL;

public class JavaFxGui extends Application implements CommandSender, CommandReceiver {

    private CommandHandler commandHandler;

    public static Scene scene;
    /**
     * GUI components
     */
    @FXML
    private Button execute;
    @FXML
    private Button wrong;
    @FXML
    private Button right;
    @FXML
    private TextArea input;
    @FXML
    private TextArea output;
    /**
     * Fields cannot be static, so i have to use this awful singleton approach
     */
    private static JavaFxGui mySelf;

    /**
     * Log4j logger
     */
    Logger logger = Logger.getLogger(JavaFxGui.class);

    @FXML
    private void initialize() {
        mySelf = this;
        /*
         * Build alias for singleton
         */
        final UniverseController controller = Main.getController();
        /*
         * Bind buttons listeners
         */
        execute.setOnAction(event -> {
            JavaFxGui.scene.setCursor(Cursor.WAIT);
            try {
                controller.submitText(input.getText());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            JavaFxGui.scene.setCursor(Cursor.DEFAULT);
        });

        right.setOnAction(event -> {
            controller.markLastCommandAsRight();
        });

        wrong.setOnAction(event -> {
            controller.markLastCommandAsWrong();
        });
    }


    public static JavaFxGui getSingleton() {
        return mySelf;
    }

    @Override
    public boolean send(String message) {
        output.setText(message);
        return true;
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

    @Override
    public void addCommandHandler(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public void startReceiver() {
        launch();
    }

    @Override
    public void stopReceiver() {
        try {
            stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
