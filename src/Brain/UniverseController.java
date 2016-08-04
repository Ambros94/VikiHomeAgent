package Brain;

import Comunication.CommandSender;
import LearningAlgorithm.CommandLogger;
import Memory.Memory;
import Things.Domain;
import Things.Operation;
import Utility.PrettyJsonConverter;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class UniverseController {
    /**
     * Data Model
     */
    private final Universe universe;
    private Command previousEmptyCommand;
    private final Memory<Command> memory;
    /**
     * Loggers
     */
    private final CommandLogger commandLogger = new CommandLogger();
    private final Logger logger = Logger.getLogger(UniverseController.class);
    /**
     * CommandSender that is used to execute the command
     */
    private final Collection<CommandSender> senders;

    /**
     * Teaching algorithm
     */
    private String lastSentence;
    private boolean teachingMode = false;

    /**
     * Last command that has been inferred
     */
    private Command lastCommand;

    public UniverseController(Universe universe, Memory<Command> memory) {
        this.universe = universe;
        this.senders = new ArrayList<>();
        // Add a fake command for teaching at the Universe
        Domain teachDomain = new Domain("teach", new HashSet<>(Arrays.asList("teach", "learn")));
        Operation teachOperation = new Operation("teach", new HashSet<>(Arrays.asList("teach", "learn")));
        teachDomain.setOperations(Collections.singleton(teachOperation));
        this.universe.getDomains().add(teachDomain);
        // Load the memory from file
        this.memory = memory;

    }

    /**
     * Extracts command from the natural given sentence.
     * If a command with an high confidence is found that is automatically executed
     *
     * @param textCommand Sentence that contains the command
     * @return null if nothing has been found in the given sentence
     * Command with status CommandStatus.MISSING_PARAMETERS if some parameter are missing
     * Command with status CommandStatus.LOW_CONFIDENCE if the best show confidence is too low
     * Command with status CommandStatus.OK if the command is READY to be sent
     */
    public Command submitText(String textCommand) throws FileNotFoundException {
        // Empty command, nothing can be found
        if (textCommand == null || textCommand.length() == 0) {
            logger.info("No commands found in this sentence (null or empty sentence)");
            return null;
        }

        // First try if the text represent the last command missing parameters
        if (previousEmptyCommand != null) {
            logger.info("Last command was not full filled, looking for parameters");
            Command c = universe.findMissingParameters(textCommand, previousEmptyCommand);
            previousEmptyCommand = null;// Parameter can only be found in the next sentence
            if (c.getStatus().equals(CommandStatus.OK)) {//The command is now fullFilled
                logger.info("Previous a command SUCCESSFULLY FILLED");
                return c;
            }
        }

        // Try to detect the new command
        List<Command> commandList = universe.textCommand(textCommand);
        //No commands found in the given sentence
        if (commandList.size() == 0) {
            logger.info("No commands found in this sentence");
            return null;
        }
        commandList.sort((o1, o2) -> Double.compare(o2.getFinalConfidence(), o1.getFinalConfidence()));
        Command bestCommand = commandList.get(0);

        //Last command was about teachingMode and now u said something correct
        if (teachingMode && bestCommand.getStatus().equals(CommandStatus.OK) && !isTeachingCommand(bestCommand)) {
            bestCommand.setStatus(CommandStatus.LEARNED);
        }
        // U want to teach something and the sentence before that was a low confidence
        if (isTeachingCommand(bestCommand) && lastCommand.getStatus().equals(CommandStatus.LOW_CONFIDENCE))
            bestCommand.setStatus(CommandStatus.TEACH);
        else
            teachingMode = false;

        if (bestCommand.getStatus().equals(CommandStatus.LOW_CONFIDENCE) && memory != null) {
            Command command = memory.isInMemory(textCommand);
            System.err.println("Command from memory -->" + command);
            if (command != null) {
                command.setStatus(CommandStatus.OK);
                bestCommand = command;
            }
        }

        switch (bestCommand.getStatus()) {
            case LEARNED:
                logger.info("Learned this:" + lastSentence + "---- > " + bestCommand.toJson());
                System.err.println("Learned this: " + lastSentence + " ----> " + bestCommand.toJson());
                if (memory != null)
                    memory.remind(lastSentence, bestCommand);
                break;
            case TEACH:
                logger.info("Ready to learn something");
                teachingMode = true;
                break;
            case LOW_CONFIDENCE:
                logger.info("Lower confidence commands");
                logger.info("Best shot is:" + bestCommand.toJson());
                System.err.println("UPDATED LAST SENTENCE");
                System.err.println(lastSentence + "--->" + textCommand);
                lastSentence = textCommand;
                break;
            case UNKNOWN:
                break;
            case OK:
                logger.info("Executing command:" + bestCommand.toJson());
                sendCommand(bestCommand);
                break;
            case MISSING_NUMBER:
                logger.info("MISSING_NUMBER");
                logger.info(bestCommand.toJson());
                previousEmptyCommand = bestCommand;
                break;
            case MISSING_LOCATION:
                logger.info("MISSING_LOCATION");
                logger.info(bestCommand.toJson());
                previousEmptyCommand = bestCommand;
                break;
            case MISSING_COLOR:
                logger.info("MISSING_COLOR");
                logger.info(bestCommand.toJson());
                previousEmptyCommand = bestCommand;
                break;
            case MISSING_DATETIME:
                logger.info("MISSING_DATETIME");
                logger.info(bestCommand.toJson());
                previousEmptyCommand = bestCommand;
                break;
            case MISSING_FREE_TEXT:
                logger.info("MISSING_FREE_TEXT");
                logger.info(bestCommand.toJson());
                previousEmptyCommand = bestCommand;
                break;
        }
        commandLogger.logMiscellaneous(bestCommand);
        lastCommand = bestCommand;
        return bestCommand;
    }

    private boolean isTeachingCommand(Command bestCommand) {
        return bestCommand.equalsIds("teach", "teach");
    }

    /**
     * Send a command with the senders
     *
     * @param c Command that has to be sent using command senders
     */
    private void sendCommand(Command c) {
        senders.forEach(sender -> sender.send(new PrettyJsonConverter().convert(c.toJson())));
    }

    public Universe getUniverse() {
        return universe;
    }

    public boolean addCommandSender(CommandSender sender) {
        return senders.add(sender);
    }

    public void stop() {
        try {
            if (memory != null)
                memory.persistMemory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
