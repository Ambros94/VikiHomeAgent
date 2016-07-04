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
        final List<Command> commandList = new ArrayList<>();
        final AtomicInteger i = new AtomicInteger(0);
        execute.setOnAction(event -> {
            commandList.clear();
            Main.scene.setCursor(Cursor.WAIT);
            /**
             * Aks the Universe to detect commands
             */
            try {
                commandList.addAll(universe.textCommand(input.getText()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                output.setText("Cannot execute this command, impossible to find relative model!");
            }
            String commands = "";
            /**
             *
             */
            List<Command> filteredCommands = commandList.stream().filter(Command::isFullFilled).collect(Collectors.toList());
            /**
             *  Prints commands output
             */
            commands += new PrettyJsonConverter().convert(filteredCommands.get(0).toJson());
            if (filteredCommands.size() == 0)
                commands = "No commands found!";
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
