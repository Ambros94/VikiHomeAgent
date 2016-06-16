package Things;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void fromJson() throws Exception {
        Operation json = Operation.fromJson("{'id': 'set_temperature'," +
                "'textInvocation': ['Set the heater to 21']," +
                "'words': ['set','bring']," +
                "'optionalParameters': " +
                "[" +
                "{'id': 'temperature'," +
                "'type': 'NUMBER' }" +
                "]," +
                "" + "'mandatoryParameters': " +
                "[" +
                "{'id': 'prova'," +
                "'type': 'COLOR' }" +
                "]" +
                "}");
        Set<Parameter> optionals = new HashSet<>(Collections.singletonList(new Parameter("temperature", ParameterType.NUMBER)));
        Set<Parameter> mandatory = new HashSet<>(Collections.singletonList(new Parameter("prova", ParameterType.COLOR)));
        Operation expected = new Operation("set_temperature", new HashSet<>(Arrays.asList("set", "bring")), Collections.singletonList("Set the heater to 21"), optionals, mandatory);
        System.out.println(json);
        assertEquals(expected, json);
    }

}