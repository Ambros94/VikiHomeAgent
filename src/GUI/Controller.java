package GUI;


import Brain.Command;
import DebugMain.Main;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.util.List;

import static DebugMain.Main.universe;

public class Controller {

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

    @FXML
    private void initialize() {
        execute.setOnAction(event -> {
            Main.scene.setCursor(Cursor.WAIT);
            List<Command> commandList = universe.textCommand(input.getText());
            String commands = "";
            for (Command c : commandList) {
                commands += Utility.prettyJsonString(c.toJson());
            }
            if (commandList.size() == 0)
                commands = "No commands found!";
            output.setText(commands);
            Main.scene.setCursor(Cursor.DEFAULT);
        });
        //right.setOnAction(event -> System.out.println("Right"));
        //wrong.setOnAction(event -> System.out.println("Wrong"));
    }
}
