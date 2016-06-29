package NLP.ParamFinders;

import Brain.Command;
import Brain.DomainOperationPair;
import Things.Domain;
import Things.Operation;
import Things.Parameter;
import Things.ParameterType;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;


public class ParametersFinderTest {

    private IParametersFinder iParametersFinder;
    private DomainOperationPair domainOperationPair;

    @Before
    public void init() {
        Domain domain = new Domain("light", Collections.singleton("light"));
        Operation operation = new Operation("setIntensity", Collections.singleton("setIntensity"));
        operation.setTextInvocation(Collections.singleton("Set light intensity to 80%"));
        operation.setOptionalParameters(Collections.singleton(new Parameter("intensity", ParameterType.NUMBER)));
        operation.setMandatoryParameters(Collections.singleton(new Parameter("when", ParameterType.DATETIME)));
        domainOperationPair = new DomainOperationPair(domain, operation, 0.9d);
        iParametersFinder = ParametersFinder.build();
    }


    @Test
    public void findParameters() throws Exception {
        Collection<Command> commands = iParametersFinder.findParameters(Collections.singleton(domainOperationPair), "Set light intensity to 80%");
        System.out.println(commands);

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