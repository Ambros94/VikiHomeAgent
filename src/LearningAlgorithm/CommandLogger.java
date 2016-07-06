package LearningAlgorithm;


import Brain.Command;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;

/**
 * Utility class, used to log command on file.
 * Those file will be used to train a word2vec and maybe a doc2vec model later
 */
public class CommandLogger {

    private final String WRONG_PATH = "resources/commandLog/wrong.txt";
    private final String RIGHT_PATH = "resources/commandLog/right.txt";

    /**
     * @param c Command that was detected to be RIGHT and need to be written on file (different file for wrong and right commands)
     * @throws IOException Some problems during file opening, usually the file does not exist
     */
    public void logRight(Command c) throws IOException {
        Path file = Paths.get(RIGHT_PATH);
        Files.write(file, Collections.singletonList(c.toJson()), Charset.forName("UTF-8"), StandardOpenOption.APPEND);
    }

    /**
     * @param c Command that was detected to be WRONG and need to be written on file (different file for wrong and right commands)
     * @throws IOException Some problems during file opening, usually the file does not exist
     */
    public void logWrong(Command c) throws IOException {
        Path file = Paths.get(WRONG_PATH);
        Files.write(file, Collections.singletonList(c.toJson()), Charset.forName("UTF-8"), StandardOpenOption.APPEND);
    }


}
