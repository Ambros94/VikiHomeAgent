package Things;

import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class OperationTest {
    @Test
    public void getMandatoryParameters() throws Exception {
        Operation turnon = new Operation("color", Collections.singleton("color"));
        Parameter p = new Parameter("Color", ParameterType.COLOR);
        Parameter p2 = new Parameter("Location", ParameterType.LOCATION);
        Set<Parameter> parameters = new HashSet<>();
        parameters.add(p);
        parameters.add(p2);
        turnon.setMandatoryParameters(parameters);
        for (Parameter parameter : parameters)
            assertTrue(turnon.getMandatoryParameters().contains(parameter));
    }

    @Test
    public void getOptionalParameters() throws Exception {
        Operation turnon = new Operation("color", Collections.singleton("color"));
        Parameter p = new Parameter("Color", ParameterType.COLOR);
        Parameter p2 = new Parameter("Location", ParameterType.LOCATION);
        Set<Parameter> parameters = new HashSet<>();
        parameters.add(p);
        parameters.add(p2);
        turnon.setOptionalParameters(parameters);
        for (Parameter parameter : parameters)
            assertTrue(turnon.getOptionalParameters().contains(parameter));
    }

}