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
        universe.setDomainOperationFinder(Word2vecDOFinder.build(universe.getDomains()));

        universe.setParametersFinder(ParametersFinder.build());
        // Create the controller
        controller = new UniverseController(universe, null);
    }

    public static void main(String[] args) throws IOException {
        init();
        List<CommandExecution> executionList = new ArrayList<>();
        List<PrintPair> wrongExecutions = new ArrayList<>();
        List<PrintPair> lowConfidence = new ArrayList<>();

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
                    lowConfidence.add(new PrintPair(commandExecution, c));
                    low_confidence++;
                    break;
                case UNKNOWN:
                    nothing++;
                    break;
                case OK:
                    if (c.equalsIds(commandExecution.getDomainId(), commandExecution.getOperationId()))
                        good++;
                    else {
                        wrong++;
                        wrongExecutions.add(new PrintPair(commandExecution, c));
                    }
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
        System.out.println("===============================================");
        System.out.println("=============== Wrong details  ================");
        System.out.println("===============================================");
        wrongExecutions.forEach(System.out::println);
        System.out.println("===============================================");
        System.out.println("=============== Low confidence ================");
        System.out.println("===============================================");
        lowConfidence.forEach(System.out::println);
    }

    private static void addBenchmark1(List<CommandExecution> executionList) {
        /*
        Light - Turn On
         */
        executionList.add(new CommandExecution("turn on the light", "light1", "turnOn"));
        executionList.add(new CommandExecution("switch on the lamp", "light1", "turnOn"));
        executionList.add(new CommandExecution("I want the ball to turn on", "light1", "turnOn"));
        executionList.add(new CommandExecution("light up the lamp", "light1", "turnOn"));
         /*
        Light - Turn Off
         */
        executionList.add(new CommandExecution("turn off the light", "light1", "turnOff"));
        executionList.add(new CommandExecution("switch off the lamp", "light1", "turnOff"));
        executionList.add(new CommandExecution("I want the ball to turn off", "light1", "turnOff"));
        executionList.add(new CommandExecution("switch off the lamp", "light1", "turnOff"));
         /*
        Light - Set intensity
         */
        executionList.add(new CommandExecution("I want my light to be at 80%", "light1", "setIntensity"));
        executionList.add(new CommandExecution("Set light intensity to 80", "light1", "setIntensity"));
        executionList.add(new CommandExecution("Turn the light to 80%", "light1", "setIntensity"));
        /*
        Light - Set color
         */
        executionList.add(new CommandExecution("I want my light to turn red", "light1", "setColor"));
        executionList.add(new CommandExecution("Turn the light red", "light1", "setColor"));
        executionList.add(new CommandExecution("Set light color to red", "light1", "setColor"));
        /*
        Volume - increase
         */
        executionList.add(new CommandExecution("Increase the volume", "volume", "volumeUp"));
        executionList.add(new CommandExecution("turn up the volume", "volume", "volumeUp"));
        executionList.add(new CommandExecution("make it louder", "volume", "volumeUp"));
        executionList.add(new CommandExecution("I can’t hear it well", "volume", "volumeUp"));
        /*
        Volume - decrease
         */
        executionList.add(new CommandExecution("decrease the volume", "volume", "volumeDown"));
        executionList.add(new CommandExecution("turn down the volume", "volume", "volumeDown"));
        executionList.add(new CommandExecution("it’s too loud", "volume", "volumeDown"));
        executionList.add(new CommandExecution("make it quieter", "volume", "volumeDown"));
        /*
        Media - resume
         */
        executionList.add(new CommandExecution("Could you please resume the song ?", "mediaCenter", "resume"));
        executionList.add(new CommandExecution("Resume play", "mediaCenter", "resume"));
        /*
        Media - next
         */
        executionList.add(new CommandExecution("Go to the next song", "mediaCenter", "next"));
        executionList.add(new CommandExecution("Play the next song", "mediaCenter", "next"));
        executionList.add(new CommandExecution("Skip this one", "mediaCenter", "next"));
        executionList.add(new CommandExecution("Skip", "mediaCenter", "next"));
        executionList.add(new CommandExecution("Next", "mediaCenter", "next"));
        /*
        Alarm - turn On
         */
        executionList.add(new CommandExecution("turn on the alarm", "alarm", "turnOn"));
        executionList.add(new CommandExecution("activate the alarm", "alarm", "turnOn"));
        executionList.add(new CommandExecution("switch on the alarm", "alarm", "turnOn"));
        /*
        Alarm - turn Off
         */
        executionList.add(new CommandExecution("turn off the alarm", "alarm", "turnOff"));
        executionList.add(new CommandExecution("deactivate the alarm", "alarm", "turnOff"));
        executionList.add(new CommandExecution("switch off the alarm", "alarm", "turnOff"));
        /*
        Timer - remind
         */
        executionList.add(new CommandExecution("Remind me to shut up in 10 minutes", "timer", "remind"));
        executionList.add(new CommandExecution("Remind me to shut up on the 4th of may", "timer", "remind"));
        executionList.add(new CommandExecution("Remind me to shut up every 15 minutes", "timer", "remind"));
        /*
        Telephone - pick up
         */
        executionList.add(new CommandExecution("pick up the phone", "telephone", "pickUp"));
        executionList.add(new CommandExecution("answer the phone", "telephone", "pickUp"));
        executionList.add(new CommandExecution("get the phone", "telephone", "pickUp"));
         /*
        Telephone - hang up
         */
        executionList.add(new CommandExecution("hang up the phone", "telephone", "hangUp"));
        executionList.add(new CommandExecution("end the call", "telephone", "hangUp"));
        /*
        Telephone - call
         */
        executionList.add(new CommandExecution("I want to call my mom", "telephone", "call"));
        /*
        Heater - increase
         */
        executionList.add(new CommandExecution("increase the temperature", "heater1", "increaseTemperature"));
        executionList.add(new CommandExecution("turn up the temperature", "heater1", "increaseTemperature"));
        executionList.add(new CommandExecution("it’s chilly in here", "heater1", "increaseTemperature"));
        /*
        Heater - decrease
         */
        executionList.add(new CommandExecution("decrease the temperature", "heater1", "decreaseTemperature"));
        executionList.add(new CommandExecution("turn down the temperature", "heater1", "decreaseTemperature"));
        executionList.add(new CommandExecution("make it cooler", "heater1", "decreaseTemperature"));
        /*
        Heater - setTemperature
         */
        executionList.add(new CommandExecution("set the heater temperature to 25", "heater1", "setTemperature"));
        /*
        Heater - turnOn
         */
        executionList.add(new CommandExecution("turn on the heater", "heater1", "turnOn"));
        executionList.add(new CommandExecution("turn the heat on", "heater1", "turnOn"));
        executionList.add(new CommandExecution("heater on", "heater1", "turnOn"));
                /*
        Heater - turnOff
         */
        executionList.add(new CommandExecution("turn off the heater", "heater1", "turnOff"));
        executionList.add(new CommandExecution("turn the heat off", "heater1", "turnOff"));
        executionList.add(new CommandExecution("heater off", "heater1", "turnOff"));



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

        @Override
        public String toString() {
            return "sentence='" + sentence + '\'' +
                    ",=>'" + domainId + '\'' +
                    ", '" + operationId + '\'' +
                    '}';
        }
    }

    private static class PrintPair {
        private final CommandExecution execution;
        private final Command shot;

        private PrintPair(CommandExecution execution, Command shot) {
            this.execution = execution;
            this.shot = shot;
        }

        @Override
        public String toString() {
            return "{" + execution +
                    ", shot=" + shot +
                    '}';
        }
    }
}