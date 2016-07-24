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

    public boolean addCommandSender(CommandSender sender) {
        return senders.add(sender);
    }

    public void submitText(String textCommand) throws FileNotFoundException {
        if (textCommand != null && textCommand.length() == 0)
            return;
        commandIndex = 0;
        /*
         * First try if the text represent the last command missing parameters
         */
        if (lastReceived != null && !lastReceived.isFullFilled()) {
            Command c = universe.findMissingParameters(textCommand, lastReceived);
            if (c.isFullFilled()) {//The command is now fullFilled
                sendCommand(c);
                return;
            }
        }
        /*
         * Try to detect the new command
         */
        commandList = universe.textCommand(textCommand);
        /*
         * No commands found in the given sentence
         */
        if (commandList.size() == 0) {
            //TODO  there are no commands in the given sentence
        } else {
            /*
             * There is a command in the sentence, if it is full filled it will be send, otherwise stored and next time we will try to find his parameters
             */
            Command bestCommand = commandList.get(0);
            lastReceived = bestCommand;
            if (bestCommand.isFullFilled()) {
                bestCommand.setStatus(CommandStatus.OK);
                sendCommand(bestCommand);
            } else {
                bestCommand.setStatus(CommandStatus.MISSING_PARAMETERS);
                //TODO Tell someone that a parameter is missing
            }
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
}
