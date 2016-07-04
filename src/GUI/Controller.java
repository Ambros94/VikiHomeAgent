package GUI;


import Brain.Command;
import DebugMain.Main;
import Utility.PrettyJsonConverter;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
        final Command[] bestShot = new Command[1];
        final List<Command> commandList = new ArrayList<>();
        final AtomicInteger i = new AtomicInteger(0);
        execute.setOnAction(event -> {
            try {
                commandList.clear();
                Main.scene.setCursor(Cursor.WAIT);
                if (bestShot[0] != null && !bestShot[0].isFullFilled()) {
                    commandList.add(universe.findMissingParameters(input.getText(), bestShot[0]));
                }
                /**
                 * Aks the Universe to detect commands
                 */
                commandList.addAll(universe.textCommand(input.getText()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                output.setText("Cannot execute this command, impossible to find relative model!");
            }
            String commands = "";
            /**
             *  Prints commands output
             */
            bestShot[0] = commandList.get(0);
            commands += new PrettyJsonConverter().convert(bestShot[0].toJson());
            if (commandList.size() == 0)
                commands = "No commands found!";
            if (!bestShot[0].isFullFilled())
                commands += "This is not full filled yet, insert some other text with missing parameters";
            output.setText(commands);
            i.set(1);
            Main.scene.setCursor(Cursor.DEFAULT);
        });

        right.setOnAction(event -> System.out.println("Right"));

        wrong.setOnAction(event -> {
            if (i.get() < commandList.size())
                output.setText(new PrettyJsonConverter().convert(commandList.get(i.getAndIncrement()).toJson()));
            else
                output.setText("No come commands");
        });
    }
}
