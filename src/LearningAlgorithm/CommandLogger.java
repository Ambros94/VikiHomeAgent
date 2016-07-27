package LearningAlgorithm;


import Brain.Command;
import Utility.Config;

import java.io.*;

/**
 * Utility class, used to log command on file.
 * Those file will be used to train a word2vec and maybe a doc2vec model later
 */
public class CommandLogger {


    private final String WRONG_PATH;
    private final String RIGHT_PATH;
    private final String MISCELLANEOUS_PATH;

    public CommandLogger() {
        Config config = Config.getConfig();
        WRONG_PATH = config.getWrongFilePath();
        RIGHT_PATH = config.getRightFilePath();
        MISCELLANEOUS_PATH = config.getMiscellaneousPath();
    }


    /**
     * @param c Command that was detected to be RIGHT and need to be written on file (different file for wrong and right commands)
     * @throws IOException Some problems during file opening, usually the file does not exist
     */
    public void logRight(Command c) throws IOException {
        log(c, RIGHT_PATH);
    }

    /**
     * @param c Command that will be written on file
     * @throws IOException Some problems during file opening, usually the file does not exist
     */
    public void logMiscellaneous(Command c) {
        try {
            log(c, MISCELLANEOUS_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param c Command that was detected to be WRONG and need to be written on file (different file for wrong and right commands)
     * @throws IOException Some problems during file opening, usually the file does not exist
     */
    public void logWrong(Command c) throws IOException {
        log(c, WRONG_PATH);

    }

    /**
     * @param c    Command that will be written on file
     * @param path Path to the file that will be written
     * @throws IOException If the file does not exist
     */
    private void log(Command c, String path) throws IOException {
        File yourFile = new File(path);
        if (!yourFile.exists()) {
            yourFile.createNewFile();
        }
        try (FileWriter fw = new FileWriter(yourFile, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(c.toJson());
        } catch (IOException e) {
            System.err.println(e);
        }
    }

}
