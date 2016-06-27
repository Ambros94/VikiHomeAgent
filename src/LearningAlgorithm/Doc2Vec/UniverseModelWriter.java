package LearningAlgorithm.Doc2Vec;


import Things.Domain;
import Things.Operation;
import org.deeplearning4j.arbiter.util.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class UniverseModelWriter {

    private static final String SEMI_PATH = "resources/doc2vecModel/";

    public void write(Set<Domain> domainSet) {
        for (Domain d : domainSet) {
            d.getOperations().stream().filter(o -> o.getTextInvocation().size() > 0).forEach(o -> {
                String dirName = d.getId() + "." + o.getId() + "/";
                createDirectory(d, o, dirName);
                try {
                    createFiles(o, dirName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void createFiles(Operation o, String dirName) throws IOException {
        int i = 0;
        for (String s : o.getTextInvocation()) {
            Path file = Paths.get(SEMI_PATH + dirName + String.valueOf(i++) + ".txt");
            Files.write(file, Collections.singletonList(s), Charset.forName("UTF-8"));
        }
    }

    private void createDirectory(Domain d, Operation o, String dirName) {
        new File(SEMI_PATH + dirName).mkdir();//TODO Check if works on windows
    }
}
