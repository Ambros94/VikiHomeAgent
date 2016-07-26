package Brain;

import Comunication.CommandSender;
import LearningAlgorithm.CommandLogger;
import Utility.PrettyJsonConverter;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
    private final Logger logger = Logger.getLogger(UniverseController.class);
    private int commandIndex = 0;
    /**
     * CommandSender that is used to execute the command
     */
    private final Collection<CommandSender> senders;

    public UniverseController(Universe universe) {
        this.universe = universe;
        this.senders = new ArrayList<>();

    }

    /**
     * Extracts command from the natural given sentence.
     * If a command with an high confidence is found that is automatically executed
     *
     * @param textCommand Sentence that contains the command
     * @return 0 if nothing has been found in the given sentence
     * -1 if some parameter are missing
     * 1 if a command has been executed
     */
    public int submitText(String textCommand) throws FileNotFoundException {
        // Empty command, nothing can be found
        if (textCommand != null && textCommand.length() == 0)
            return 0;
        commandIndex = 0;
        // First try if the text represent the last command missing parameters
        if (lastReceived != null && !lastReceived.isFullFilled()) {
            Command c = universe.findMissingParameters(textCommand, lastReceived);
            if (c.isFullFilled()) {//The command is now fullFilled
                sendCommand(c);
                return 1;
            }
        }
        // Try to detect the new command
        commandList = universe.textCommand(textCommand);
        //No commands found in the given sentence
        if (commandList.size() == 0) {
            logger.info("No commands found in this sentence");
            return 0;
        }
        // There is a command in the sentence, if it is full filled it will be send, otherwise stored and next time we will try to find his parameters
        Command bestCommand = commandList.get(0);
        lastReceived = bestCommand;
        if (bestCommand.isFullFilled()) {
            bestCommand.setStatus(CommandStatus.OK);
            sendCommand(bestCommand);
            return 1;
        } else {
            bestCommand.setStatus(CommandStatus.MISSING_PARAMETERS);
            logger.info("Command is NOT full filled");
            logger.info(bestCommand.toJson());
            return -1;
        }

    }

    /**
     * TODO Send the command only if the confidence is higher than a value, otherwise ask the user if it correct
     * Send a command with the senders
     *
     * @param c Command that has to be sent with the command senders
     */

    private void sendCommand(Command c) {
        senders.forEach(sender -> sender.send(new PrettyJsonConverter().convert(c.toJson())));
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
                //TODO Tell someone that the command is not full filled
            }
        }
    }

    public Universe getUniverse() {
        return universe;
    }

    public boolean addCommandSender(CommandSender sender) {
        return senders.add(sender);
    }
}
