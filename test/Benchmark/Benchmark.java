package Benchmark;

import Brain.Command;
import Brain.Universe;
import Brain.UniverseController;
import Comunication.UniverseLoader;
import NLP.DomainOperationsFinders.Word2vecDOFinder;
import NLP.ParamFinders.ParametersFinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Benchmark {

    private static UniverseController controller;

    public static void init() throws IOException {
        String universeJson;
        universeJson = new UniverseLoader().loadFromFile("resources/mock_up/vikiBenchmark.json");

        Universe universe = Universe.fromJson(universeJson);
        universe.setParametersConfidenceBoost(true);
        universe.setDomainOperationFinder(Word2vecDOFinder.build(universe.getDomains()));

        universe.setParametersFinder(ParametersFinder.build());
        // Create the controller
        controller = new UniverseController(universe);
    }

    public static void main(String[] args) throws IOException {
        init();
        List<CommandExecution> executionList = new ArrayList<>();
        /*
         * Add Executions to the list
         */
        addBenchmark1(executionList);

        int good = 0;
        int wrong = 0;
        int low_confidence = 0;
        int nothing = 0;
        int missing_parameters = 0;
        int n = executionList.size();
        for (CommandExecution commandExecution : executionList) {
            Command c = controller.submitText(commandExecution.getSentence());
            switch (c.getStatus()) {
                case LOW_CONFIDENCE:
                    low_confidence++;
                    break;
                case UNKNOWN:
                    nothing++;
                    break;
                case OK:
                    if (c.equalsIds(commandExecution.getDomainId(), commandExecution.getOperationId()))
                        good++;
                    else
                        wrong++;
            }
        }
        System.out.println("===============================================");
        System.out.println("============ J.A.R.V.I.S. STATISTICS ==========");
        System.out.println("===============================================");
        System.out.println("Success-rate : " + 100.0d * good / n + "%");
        System.out.println("Good : " + good);
        System.out.println("Wrong : " + wrong);
        System.out.println("Low-confidence : " + low_confidence);
        System.out.println("Nothing : " + nothing);
        System.out.println("Missing-parameters : " + missing_parameters);
    }

    private static void addBenchmark1(List<CommandExecution> executionList) {
        executionList.add(new CommandExecution("turn on the light", "light1", "turnOn"));
    }


    private static class CommandExecution {
        private final String sentence;
        private final String domainId;
        private final String operationId;

        String getSentence() {
            return sentence;
        }

        String getDomainId() {
            return domainId;
        }

        String getOperationId() {
            return operationId;
        }

        private CommandExecution(String sentence, String domainId, String operationId) {
            this.sentence = sentence;
            this.domainId = domainId;
            this.operationId = operationId;
        }
    }


}