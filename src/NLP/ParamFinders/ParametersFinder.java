package NLP.ParamFinders;

import Brain.Command;
import Brain.DomainOperationPair;
import Brain.ParamValuePair;
import Things.Domain;
import Things.Operation;
import Things.Parameter;
import Things.ParameterType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class ParametersFinder implements IParametersFinder {

    private final Map<ParameterType, ITypeFinder> finders;//TODO Ask why it does not work with the generic
    private static Logger logger = LoggerFactory.getLogger(ParametersFinder.class);

    private ParametersFinder() {
        finders = new HashMap<>();
        finders.put(ParameterType.LOCATION, new LocationFinder());
        finders.put(ParameterType.COLOR, new ColorFinder());
        finders.put(ParameterType.DATETIME, new DateTimeFinder());
        finders.put(ParameterType.FREE_TEXT, new FreeTextFinder());
        finders.put(ParameterType.NUMBER, new NumberFinder());
    }

    public static IParametersFinder build() {
        return new ParametersFinder();
    }

    @Override
    public Collection<Command> findParameters(Collection<DomainOperationPair> domainOperationPairs, String sentence) {
        /**
         * Collection to be returned
         */
        Collection<Command> commands = new ArrayList<>();
        /**
         * Go through every possible operation and find relative parameters
         */
        for (DomainOperationPair pair : domainOperationPairs) {
            /**
             * Build a command with every yet defined params
             */
            Operation o = pair.getOperation();
            Domain d = pair.getDomain();
            Command c = new Command(d, o, sentence, pair.getConfidence());
            /**
             * Loops over params and find them in the sentence
             */
            Collection<ParamValuePair> paramValuePairs = new ArrayList<>();
            paramValuePairs.addAll(o.getMandatoryParameters().stream().map(p -> findParameters(p, sentence)).collect(Collectors.toList()));
            paramValuePairs.addAll(o.getOptionalParameters().stream().map(p -> findParameters(p, sentence)).collect(Collectors.toList()));
            /**
             * Add params to the command
             * Add command to the collection that will be returned
             */
            c.addParamValue(paramValuePairs);
            commands.add(c);
        }
        return commands;
    }

    private ParamValuePair findParameters(Parameter p, String sentence) {
        /**
         * Check if a paramFinder for the requested type exists
         * Calls the right paramFinder depending on ParamType
         */
        if (!finders.containsKey(p.getType())) {
            logger.error("There is no finders for " + p.getType() + " ParameterType");
            throw new MissingFinderException(p.getType());
        }
        return finders.get(ParameterType.COLOR).find(p, sentence);
    }
}
