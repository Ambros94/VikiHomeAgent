package Comunication;

import Utility.Config;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Helper class that loads the universe from a file or from an url.
 * Both the url and the path needs to be in the properties file
 */
public class VikiRemoteLoader {

    private static final String vikiGetUrl = Config.getConfig().getVikiGetUrl();

    private static final String vikiFilePath = Config.getConfig().getVikiFilePath();

    /**
     * Load viki's universe from url
     *
     * @return String JSON formatted that represent the loaded universe
     * @throws IOException when cannot open the connection to vikiGetUrl
     */
    public String loadFromRemote() throws IOException {
        Scanner scanner = new Scanner(new URL(vikiGetUrl).openStream(), "UTF-8");
        String out = scanner.useDelimiter("\\A").next();
        scanner.close();
        return out;
    }

    /**
     * Load viki universe from file
     *
     * @return String JSON formatted that represent the loaded universe
     * @throws IOException if cannot find the file representing the universe
     */
    public String loadFromFile() throws IOException {
        return new String(Files.readAllBytes(Paths.get(vikiFilePath)));
    }
}
