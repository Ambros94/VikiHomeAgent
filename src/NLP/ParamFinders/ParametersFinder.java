package NLP.ParamFinders;

import Brain.DomainOperationPair;
import NLP.Params.Value;
import Things.ParameterType;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
    public Map<ParameterType, Value> findParameters(Collection<DomainOperationPair> domainOperationPairs, String sentence) {
        /*
         * Collection to be returned
         */
        Map<ParameterType, Value> typeValueMap = new HashMap<>();
        /*
         * Loop on possible parametersType
         */
        for (ParameterType parameterType : finders.keySet()) {
            typeValueMap.put(parameterType, finders.get(parameterType).find(parameterType, sentence));
        }

        return typeValueMap;
    }


}
