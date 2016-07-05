package GUI;


import Brain.UniverseController;
import DebugMain.UniverseGui;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.FileNotFoundException;

public class GUIController {

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

    private static GUIController mySelf;

    @FXML
    private void initialize() {
        mySelf = this;
        /**
         * Build alias, i hate static access
         */
        final UniverseController controller = UniverseGui.controller;
        /**
         * Bind buttons listeners
         */
        execute.setOnAction(event -> {
            UniverseGui.scene.setCursor(Cursor.WAIT);

            String textCommand = input.getText();
            try {
                controller.submitText(textCommand);
            } catch (FileNotFoundException e) {
                setOutputText("e");
            }

            UniverseGui.scene.setCursor(Cursor.DEFAULT);
        });

        right.setOnAction(event -> {
            String response = controller.markLastCommandAsRight();
            output.setText(response);
        });

        wrong.setOnAction(event -> {
            String response = controller.markLastCommandAsWrong();
            output.setText(response);
        });
    }

    public void setOutputText(String text) {
        output.setText(text);
    }

    public static GUIController getSingleton() {
        return mySelf;
    }
}
