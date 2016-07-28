package NLP.ParamFinders;

import Brain.Command;
import Brain.DomainOperationPair;
import Brain.ParamValue;
import Things.Domain;
import Things.Operation;
import Things.Parameter;
import Things.ParameterType;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class able to find different type of Parameters
 */
public class ParametersFinder implements IParametersFinder {

    private final Map<ParameterType, ITypeFinder> finders;
    private static Logger logger = Logger.getLogger(ParametersFinder.class);

    /**
     * Instantiate every TypeFinder
     */
    private ParametersFinder() {
        finders = new HashMap<>();
        finders.put(ParameterType.LOCATION, new LocationFinder());
        finders.put(ParameterType.DATETIME, new DateTimeFinder());
        finders.put(ParameterType.COLOR, new ColorFinder());
        finders.put(ParameterType.NUMBER, new NumberFinder());
        finders.put(ParameterType.FREE_TEXT, new FreeTextFinder());
    }

    public static IParametersFinder build() {
        return new ParametersFinder();
    }

    @Override
    public Collection<Command> findParameters(Collection<DomainOperationPair> domainOperationPairs, String sentence) {
        /*
         * Collection to be returned
         */
        Collection<Command> commands = new ArrayList<>();
        /*
         * Loop on possible parametersType
         */


        /*
         * Go through every possible operation and find relative parameters
         */
        for (DomainOperationPair pair : domainOperationPairs) {
            /*
             * Build a command with every yet defined params
             */
            Operation o = pair.getOperation();
            Domain d = pair.getDomain();
            Command c = new Command(d, o, sentence, pair.getConfidence());
            /*
             * Loops over params and find them in the sentence
             */
            Collection<ParamValue> paramValues = new ArrayList<>();
            paramValues.addAll(o.getMandatoryParameters().stream().map(p -> findParameters(p, sentence)).collect(Collectors.toList()));
            paramValues.addAll(o.getOptionalParameters().stream().map(p -> findParameters(p, sentence)).collect(Collectors.toList()));
            /*
             * Add params to the command
             * Add command to the collection that will be returned
             */
            c.addParamValue(paramValues);
            commands.add(c);
        }
        return commands;
    }

    private ParamValue findParameters(Parameter p, String sentence) {
        /*
         * Check if a paramFinder for the requested type exists
         * Calls the right paramFinder depending on ParamType
         */
        if (!finders.containsKey(p.getType())) {
            logger.error("There is no finders for " + p.getType() + " ParameterType");
            throw new MissingFinderException(p.getType());
        }
        return finders.get(p.getType()).find(p, sentence);
    }
}
