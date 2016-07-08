package Brain;

import GUI.JavaFxGui;
import LearningAlgorithm.CommandLogger;
import Utility.PrettyJsonConverter;
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
     * Loggers
     */
    private final CommandLogger commandLogger = new CommandLogger();
    private final Logger logger = LoggerFactory.getLogger(UniverseController.class);
    private int commandIndex = 0;
    /**
     * CommandSender that is used to execute the command
     */
    private final CommandSender sender = new GuiCommandSender();

    public UniverseController(Universe universe) {
        this.universe = universe;
    }

    public void submitText(String textCommand) throws FileNotFoundException {
        commandIndex = 0;
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
            JavaFxGui.getSingleton().showMessage("Cannot find any command in this text");
        } else {
            /**
             * There is a command in the sentence, if it is full filled it will be send, otherwise stored and next time we will try to find his parameters
             */
            Command bestCommand = commandList.get(0);
            lastReceived = bestCommand;
            if (bestCommand.isFullFilled()) {
                sendCommand(bestCommand);
            } else {
                JavaFxGui.getSingleton().showMessage("This command IS NOT FULL FILLED" + bestCommand.toJson());

            }
        }
    }

    /**
     * TODO Send the command only if the confidence is higher than a value, otherwise ask the user if it correct
     * Send a command with the sender
     *
     * @param c Command that has to be sent with the command sender
     */
    private void sendCommand(Command c) {
        sender.send("Approved" + new PrettyJsonConverter().convert(c.toJson()));
    }

    /**
     * Mark a command as right. Invokes the command logger to log the command
     */
    public void markLastCommandAsRight() {
        logger.debug("Write positive command on file");
        try {
            commandLogger.logRight(lastReceived);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mark a command as wrong. Invokes the command logger to log the command
     * Displays the next highly possible command
     */
    public void markLastCommandAsWrong() {
        logger.debug("Write negative command on file");
        try {
            commandLogger.logWrong(lastReceived);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (commandIndex < commandList.size()) {
            lastReceived = commandList.get(++commandIndex);
            if (lastReceived.isFullFilled()) {
                sendCommand(lastReceived);
            } else {
                JavaFxGui.getSingleton().showMessage("This command IS NOT FULL FILLED" + lastReceived.toJson());

            }
        }
    }
}
