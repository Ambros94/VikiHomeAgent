package NLP.ParamFinders;

import Brain.DomainOperationPair;
import NLP.Params.MyDate;
import NLP.Params.MyNumber;
import NLP.Params.Value;
import Things.Domain;
import Things.Operation;
import Things.Parameter;
import Things.ParameterType;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.assertEquals;


public class ParametersFinderTest {

    private IParametersFinder iParametersFinder;
    private DomainOperationPair domainOperationPair;

    @Before
    public void init() {
        Domain domain = new Domain("light", Collections.singleton("light"));
        Operation operation = new Operation("setIntensity", Collections.singleton("setIntensity"));
        operation.setTextInvocation(Collections.singleton("Could you please set the light intensity to 80%"));
        Parameter intensity = new Parameter("intensity", ParameterType.NUMBER);
        Parameter date = new Parameter("when", ParameterType.DATETIME);
        operation.setMandatoryParameters(Collections.singleton(intensity));
        operation.setOptionalParameters(Collections.singleton(date));
        domainOperationPair = new DomainOperationPair(domain, 0.5, operation, 0.4d);
        iParametersFinder = ParametersFinder.build();
    }


    @Test
    public void findParameters1() throws Exception {
        Map<ParameterType, Value> commands = iParametersFinder.findParameters(Collections.singleton(domainOperationPair), "Set light intensity to 80");
        assertEquals(commands.get(ParameterType.NUMBER), new MyNumber(80));
    }

    @Test
    public void find2Parameters() throws Exception {
        Map<ParameterType, Value> commands = iParametersFinder.findParameters(Collections.singleton(domainOperationPair), "in march Set light intensity to 80 ");
        assertEquals(commands.get(ParameterType.NUMBER), new MyNumber(80));
        assertEquals(commands.get(ParameterType.DATETIME), new MyDate("2016-03"));
    }

}