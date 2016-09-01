package Main;

import Brain.Command;
import Brain.Confidence.ConfidenceCalculatorBuilder;
import Brain.Universe;
import Brain.UniverseController;
import Comunication.*;
import Memory.Memory;
import NLP.DomainOperationsFinders.Word2vecDOFinder;
import NLP.ParamFinders.ParametersFinder;
import Utility.Config;
import org.apache.log4j.Logger;
import org.deeplearning4j.arbiter.util.ClassPathResource;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;

import java.io.IOException;

public class Main {

    private static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {

        Universe universe;
        UniverseController controller;

        // Input
        CommandReceiver input = new WSCommandReceiver(Config.getConfig().getCommandReceiverAddress(), Config.getConfig().getCommandReceiverPort());
        //Output
        CommandSender executor = new WSCommandSender(Config.getConfig().getVikiAddress());
        CommandSender gui = new WSCommandSender("http://localhost:5678");
        try {
            String json = new UniverseLoader().loadFromFile("resources/mock_up/vikiCache.json");
            //String json = new UniverseLoader().loadFromRemote();
            gui.startSender();
            executor.startSender();
            input.startReceiver();

            //Create the universe
            universe = Universe.fromJson(json);
            logger.info("Loaded universe: " + universe);
            logger.info("Started wordVectors loading");
            ClassPathResource resource = new ClassPathResource("word2vec/GoogleNews-vectors-negative300.bin");
            WordVectors wordVectors = WordVectorSerializer.loadGoogleModel(resource.getFile(), true, false);
            logger.info("Word vectors loading completed!");
            universe.setDomainOperationFinder(Word2vecDOFinder.build(universe.getDomains(), wordVectors));
            universe.setParametersFinder(ParametersFinder.build());

            //Load the memory from file
            Memory<Command> memory = new Memory<Command>(wordVectors, Config.getConfig().getMemoryPath());
            // Create the controller
            controller = new UniverseController(universe, memory, ConfidenceCalculatorBuilder.getStatic());
            input.setUniverseController(controller);
            controller.addCommandSender(executor);
            controller.addCommandSender(gui);

            //Shutdown everything gracefully
            final boolean[] interrupted = {false};
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    System.out.println("Shutdown requested!");
                    interrupted[0] = true;
                    input.stopReceiver();
                    executor.stopSender();
                    gui.stopSender();
                    controller.stop();
                }
            });
            while (!interrupted[0]) {
                Thread.sleep(1000);
            }
        } catch (IOException e) {
            logger.error("Cannot load the universe from the selected source");
            logger.error(e.getMessage());
        }

    }
}
