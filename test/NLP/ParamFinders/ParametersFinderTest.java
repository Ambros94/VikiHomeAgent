package NLP.ParamFinders;

import Brain.Command;
import Brain.DomainOperationPair;
import Brain.ParamValuePair;
import Things.Domain;
import Things.Operation;
import Things.Parameter;
import Things.ParameterType;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;

import static org.junit.Assert.*;


public class ParametersFinderTest {

    private IParametersFinder iParametersFinder;
    private DomainOperationPair domainOperationPair;
    Parameter intensity, date;

    @Before
    public void init() {
        Domain domain = new Domain("light", Collections.singleton("light"));
        Operation operation = new Operation("setIntensity", Collections.singleton("setIntensity"));
        operation.setTextInvocation(Collections.singleton("Could you please set the light intensity to 80%"));
        intensity = new Parameter("intensity", ParameterType.NUMBER);
        date = new Parameter("when", ParameterType.DATETIME);
        operation.setMandatoryParameters(Collections.singleton(intensity));
        operation.setOptionalParameters(Collections.singleton(date));
        domainOperationPair = new DomainOperationPair(domain, operation, 0.9d);
        iParametersFinder = ParametersFinder.build();
    }


    @Test
    public void findParameters1() throws Exception {
        Collection<Command> commands = iParametersFinder.findParameters(Collections.singleton(domainOperationPair), "Set light intensity to 80");
        assertTrue(commands.size() == 1);
        commands.forEach(command -> command.getParamValue().forEach(paramValuePair -> assertEquals("80", paramValuePair.getValue())));
    }

    @Test
    public void find2Parameters() throws Exception {
        Collection<Command> commands = iParametersFinder.findParameters(Collections.singleton(domainOperationPair), "Set light intensity to 80 next tuesday");
        assertTrue(commands.size() == 1);
        Command command = new ArrayList<>(commands).get(0);
        Set<ParamValuePair> pairs = command.getParamValue();
        System.out.println(pairs);
        assertTrue(pairs.contains(new ParamValuePair(intensity,"80")));
        assertTrue(pairs.contains(new ParamValuePair(date,"2016-07-12")));

    }

    @Test(expected = MissingFinderException.class)
    public void findParameters2() throws Exception {
        Domain domain = new Domain("light", Collections.singleton("light"));
        Operation operation = new Operation("setIntensity", Collections.singleton("setIntensity"));
        operation.setTextInvocation(Collections.singleton("Set light intensity to 80%"));
        operation.setMandatoryParameters(Collections.singleton(new Parameter("when", ParameterType.ERROR)));
        DomainOperationPair domainOperationPair = new DomainOperationPair(domain, operation, 0.9d);
        IParametersFinder iParametersFinder = ParametersFinder.build();
        /**
         * Looks for a parameter of type ERROR, but it does not exist, so it throws an exception
         */
        iParametersFinder.findParameters(Collections.singleton(domainOperationPair), "Set light intensity to 80%");
    }

}