package Brain.Confidence;

import Brain.Command;
import Brain.Universe;
import Comunication.UniverseLoader;
import NLP.DomainOperationsFinders.Word2vecDOFinder;
import NLP.ParamFinders.ParametersFinder;
import org.apache.log4j.Logger;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfidenceCalculatorBuilder {

    private final static Logger logger = Logger.getLogger(ConfidenceCalculatorBuilder.class);

    public static StaticConfidenceCalculator getStatic() {
        return new StaticConfidenceCalculator();
    }

    public static BayesianConfidenceCalculator getBayesian(WordVectors wordVectors) {
        BayesianConfidenceCalculator bayesianConfidenceCalculator = new BayesianConfidenceCalculator();
        /*
         * Trains the system
         */
        try {
            Universe universe = Universe.fromJson(new UniverseLoader().loadFromFile("resources/mock_up/vikiBenchmark.json"));

            universe.setDomainOperationFinder(Word2vecDOFinder.build(universe.getDomains(), wordVectors));
            universe.setParametersFinder(ParametersFinder.build());
            List<CommandExecution> trainList = generateTrainSet();
            System.out.println("TrainSet size:" + trainList.size());

            for (CommandExecution commandExecution : trainList) {
                // Generate all possible commands
                List<Command> commandList = universe.textCommand(commandExecution.getSentence());
                for (Command command : commandList) {
                    if (command.equalsIds(commandExecution.getDomainId(), commandExecution.getOperationId())) {
                        System.out.println(String.format("RIGHT\t%f\t%f\t%d\t%d", command.getDomainConfidence(), command.getOperationConfidence(), command.getRightParameters(), command.getWrongParameters()));
                        bayesianConfidenceCalculator.train(Category.RIGHT, command.getFinalConfidence(), command.getRightParameters(), command.getWrongParameters());
                    } else {
                        System.out.println(String.format("WRONG\t%f\t%f\t%d\t%d", command.getDomainConfidence(), command.getOperationConfidence(), command.getRightParameters(), command.getWrongParameters()));
                        bayesianConfidenceCalculator.train(Category.WRONG, command.getFinalConfidence(), command.getRightParameters(), command.getWrongParameters());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bayesianConfidenceCalculator;
    }


    private static List<CommandExecution> generateTrainSet() {
        List<CommandExecution> trainList = new ArrayList<>();
        trainList.add(new CommandExecution("turn on the light", "light1", "turnOn"));
        trainList.add(new CommandExecution("turn the light on", "light1", "turnOn"));
        trainList.add(new CommandExecution("switch on the lamp", "light1", "turnOn"));
        trainList.add(new CommandExecution("switch the lamp on", "light1", "turnOn"));
        trainList.add(new CommandExecution("I want the ball to turn on", "light1", "turnOn"));
        trainList.add(new CommandExecution("light up the lamp", "light1", "turnOn"));
         /*
        Light - Turn Off
         */
        trainList.add(new CommandExecution("turn off the light", "light1", "turnOff"));
        trainList.add(new CommandExecution("turn the light off", "light1", "turnOff"));
        trainList.add(new CommandExecution("switch the lamp off", "light1", "turnOff"));
        trainList.add(new CommandExecution("switch off the lamp", "light1", "turnOff"));
        trainList.add(new CommandExecution("I want the ball to turn off", "light1", "turnOff"));
        trainList.add(new CommandExecution("switch off the lamp", "light1", "turnOff"));
         /*
        Light - Set intensity
         */
        trainList.add(new CommandExecution("I want my light to be at 80%", "light1", "setIntensity"));
        trainList.add(new CommandExecution("I want my lamp to be at 80%", "light1", "setIntensity"));
        trainList.add(new CommandExecution("Set light intensity to 80", "light1", "setIntensity"));
        trainList.add(new CommandExecution("Turn the light to 80%", "light1", "setIntensity"));
        trainList.add(new CommandExecution("80% is the light intensity that i desire", "light1", "setIntensity"));
        /*
        Light - Set color
         */
        trainList.add(new CommandExecution("I want my light to turn red", "light1", "setColor"));
        trainList.add(new CommandExecution("I want my light to be red", "light1", "setColor"));
        trainList.add(new CommandExecution("Turn the light red", "light1", "setColor"));
        trainList.add(new CommandExecution("Set light color to red", "light1", "setColor"));
        trainList.add(new CommandExecution("Set to red the light color", "light1", "setColor"));
        /*
        Volume - increase
         */
        trainList.add(new CommandExecution("Increase the volume", "volume", "volumeUp"));
        trainList.add(new CommandExecution("turn up the volume", "volume", "volumeUp"));
        trainList.add(new CommandExecution("turn the volume up", "volume", "volumeUp"));
        trainList.add(new CommandExecution("make it louder", "volume", "volumeUp"));
        trainList.add(new CommandExecution("I can’t hear it well", "volume", "volumeUp"));
        /*
        Volume - decrease
         */
        trainList.add(new CommandExecution("decrease the volume", "volume", "volumeDown"));
        trainList.add(new CommandExecution("turn down the volume", "volume", "volumeDown"));
        trainList.add(new CommandExecution("turn the volume down", "volume", "volumeDown"));
        trainList.add(new CommandExecution("it’s too loud", "volume", "volumeDown"));
        trainList.add(new CommandExecution("make it quieter", "volume", "volumeDown"));
        /*
        Media - resume
         */
        trainList.add(new CommandExecution("Could you please resume the song ?", "mediaCenter", "resume"));
        trainList.add(new CommandExecution("I want you to resume the song", "mediaCenter", "resume"));
        trainList.add(new CommandExecution("Resume play", "mediaCenter", "resume"));
        /*
        Media - next
         */
        trainList.add(new CommandExecution("Go to the next song", "mediaCenter", "next"));
        trainList.add(new CommandExecution("Play the next song", "mediaCenter", "next"));
        trainList.add(new CommandExecution("Play the next song please", "mediaCenter", "next"));
        trainList.add(new CommandExecution("Skip this one", "mediaCenter", "next"));
        trainList.add(new CommandExecution("Skip", "mediaCenter", "next"));
        trainList.add(new CommandExecution("Next", "mediaCenter", "next"));
        /*
        Alarm - turn On
         */
        trainList.add(new CommandExecution("turn on the alarm", "alarm", "turnOn"));
        trainList.add(new CommandExecution("turn the alarm on", "alarm", "turnOn"));
        trainList.add(new CommandExecution("activate the alarm", "alarm", "turnOn"));
        trainList.add(new CommandExecution("switch on the alarm", "alarm", "turnOn"));
        trainList.add(new CommandExecution("switch the alarm on", "alarm", "turnOn"));
        /*
        Alarm - turn Off
         */
        trainList.add(new CommandExecution("turn off the alarm", "alarm", "turnOff"));
        trainList.add(new CommandExecution("switch off the alarm", "alarm", "turnOff"));
        trainList.add(new CommandExecution("switch the alarm off", "alarm", "turnOff"));
        trainList.add(new CommandExecution("turn the alarm off", "alarm", "turnOff"));
        trainList.add(new CommandExecution("deactivate the alarm", "alarm", "turnOff"));
        trainList.add(new CommandExecution("switch off the alarm", "alarm", "turnOff"));
        /*
        Timer - remind
         */
        trainList.add(new CommandExecution("Remind me to shut up in 10 minutes", "timer", "remind"));
        trainList.add(new CommandExecution("Remind me to shut up on the 4th of may", "timer", "remind"));
        trainList.add(new CommandExecution("Remind me to shut up every 15 minutes", "timer", "remind"));
        /*
        Telephone - pick up
         */
        trainList.add(new CommandExecution("pick up the phone", "telephone", "pickUp"));
        trainList.add(new CommandExecution("answer the phone", "telephone", "pickUp"));
        trainList.add(new CommandExecution("get the phone", "telephone", "pickUp"));
         /*
        Telephone - hang up
         */
        trainList.add(new CommandExecution("hang up the phone", "telephone", "hangUp"));
        trainList.add(new CommandExecution("end the call", "telephone", "hangUp"));
        /*
        Telephone - call
         */
        trainList.add(new CommandExecution("I want to call my mom", "telephone", "call"));
        trainList.add(new CommandExecution("Could you please call my mom?", "telephone", "call"));
        /*
        Heater - increase
         */
        trainList.add(new CommandExecution("increase the temperature", "heater1", "increaseTemperature"));
        trainList.add(new CommandExecution("turn up the temperature", "heater1", "increaseTemperature"));
        trainList.add(new CommandExecution("it’s chilly in here", "heater1", "increaseTemperature"));
        /*
        Heater - decrease
         */
        trainList.add(new CommandExecution("decrease the temperature", "heater1", "decreaseTemperature"));
        trainList.add(new CommandExecution("turn down the temperature", "heater1", "decreaseTemperature"));
        trainList.add(new CommandExecution("turn the temperature down", "heater1", "decreaseTemperature"));
        trainList.add(new CommandExecution("make it cooler", "heater1", "decreaseTemperature"));
        /*
        Heater - setTemperature
         */
        trainList.add(new CommandExecution("set the heater temperature to 25", "heater1", "setTemperature"));
        trainList.add(new CommandExecution("set the temperature to 25", "heater1", "setTemperature"));
        trainList.add(new CommandExecution("set to 25 the heater temperature", "heater1", "setTemperature"));
        /*
        Heater - turnOn
         */
        trainList.add(new CommandExecution("turn on the heater", "heater1", "turnOn"));
        trainList.add(new CommandExecution("turn the heater on", "heater1", "turnOn"));
        trainList.add(new CommandExecution("switch on the heater", "heater1", "turnOn"));
        trainList.add(new CommandExecution("switch the heater on", "heater1", "turnOn"));
        trainList.add(new CommandExecution("turn the heat on", "heater1", "turnOn"));
        trainList.add(new CommandExecution("heater on", "heater1", "turnOn"));
        /*
        Heater - turnOff
         */
        trainList.add(new CommandExecution("turn off the heater", "heater1", "turnOff"));
        trainList.add(new CommandExecution("turn the heater off", "heater1", "turnOff"));
        trainList.add(new CommandExecution("turn the heat off", "heater1", "turnOff"));
        trainList.add(new CommandExecution("heater off", "heater1", "turnOff"));
        return trainList;
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
}
