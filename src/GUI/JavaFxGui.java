package GUI;


import Brain.UniverseController;
import DebugMain.Main;
import DebugMain.UniverseGui;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.FileNotFoundException;

public class JavaFxGui implements UniverseGui {
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

    @FXML
    private void initialize() {
        mySelf = this;
        /**
         * Build alias for singleton
         */
        final UniverseController controller = Main.getController();
        /**
         * Bind buttons listeners
         */
        execute.setOnAction(event -> {
            Main.scene.setCursor(Cursor.WAIT);

            String textCommand = input.getText();
            try {
                controller.submitText(textCommand);
            } catch (FileNotFoundException e) {
                showMessage("e");
            }

            Main.scene.setCursor(Cursor.DEFAULT);
        });

        right.setOnAction(event -> {
            controller.markLastCommandAsRight();
        });

        wrong.setOnAction(event -> {
            controller.markLastCommandAsWrong();
        });
    }

    @Override
    public void showMessage(String text) {
        output.setText(text);
    }

    public static JavaFxGui getSingleton() {
        return mySelf;
    }
}
