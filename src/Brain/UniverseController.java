package Brain;

import GUI.GUIController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class UniverseController {

    /**
     * Data Model
     */
    private final Universe universe;
    /**
     * Field used to store executed command history
     */
    private List<Command> commandList = new ArrayList<>();
    private Command lastReceived;
    /**
     * Logger
     */
    private final Logger logger = LoggerFactory.getLogger(UniverseController.class);

    public UniverseController(Universe universe) {
        this.universe = universe;
    }

    public void submitText(String textCommand) throws FileNotFoundException {
        /**
         * First try if the text represent the last command missing parameters
         */
        if (lastReceived != null && !lastReceived.isFullFilled()) {
            Command c = universe.findMissingParameters(textCommand, lastReceived);
            if (c.isFullFilled()) {//The command is now fullFilled
                sendCommand(c);
                return;
            }
        }
        /**
         * Try to detect the new command
         */
        commandList = universe.textCommand(textCommand);
        Command bestCommand = commandList.get(0);
        sendCommand(bestCommand);


        logger.debug("Text submitted" + textCommand);
    }

    private void sendCommand(Command c) {
        lastReceived = c;
        System.out.println("Sending the command");
        if (c == null) {
            GUIController.getSingleton().setOutputText("Cannot find any command");
            return;
        }
        if (!c.isFullFilled()) {
            GUIController.getSingleton().setOutputText("This command IS NOT FULL FILLED" + c.toJson());
            return;
        }
        GUIController.getSingleton().setOutputText("APPROVED " + c.toJson());
    }

    public String markLastCommandAsRight() {
        logger.debug("Write positive command on file");
        return "Broken";
    }

    public String markLastCommandAsWrong() {
        logger.debug("Write negative command on file");
        return "Ok";
    }
}
