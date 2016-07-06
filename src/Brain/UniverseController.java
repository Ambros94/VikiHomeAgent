package Brain;

import GUI.GUIController;
import LearningAlgorithm.CommandLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
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
    private final CommandLogger commandLogger = new CommandLogger();
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
        /**
         * No commands found in the given sentence
         */
        if (commandList.size() == 0) {
            GUIController.getSingleton().setOutputText("Cannot find any command in this text");
        } else {
            /**
             * There is a command in the sentence, if it is full filled it will be send, otherwise stored and next time we will try to find his parameters
             */
            Command bestCommand = commandList.get(0);
            lastReceived = bestCommand;
            if (bestCommand.isFullFilled()) {
                sendCommand(bestCommand);
            } else {
                GUIController.getSingleton().setOutputText("This command IS NOT FULL FILLED" + bestCommand.toJson());

            }
        }
    }

    private void sendCommand(Command c) {
        System.out.println("Sending the command");
        GUIController.getSingleton().setOutputText("APPROVED " + c.toJson());

    }

    public String markLastCommandAsRight() {
        logger.debug("Write positive command on file");
        try {
            commandLogger.logRight(lastReceived);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Ok";
    }

    public String markLastCommandAsWrong() {
        logger.debug("Write negative command on file");
        try {
            commandLogger.logWrong(lastReceived);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Broken";
    }
}
