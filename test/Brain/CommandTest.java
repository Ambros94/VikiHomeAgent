package Brain;

import Things.Domain;
import Things.Operation;
import Things.Parameter;
import Things.ParameterType;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CommandTest {


    @Test
    public void getOperation() throws Exception {
        Operation operation = new Operation("turn on", Collections.singleton("turn_on"));
        Domain domain = new Domain("light", Collections.singleton("lamp"));
        Command c = new Command(domain, operation, "Test", 0);
        assertEquals(operation, c.getOperation());
    }

    @Test
    public void getDomain() throws Exception {
        Operation operation = new Operation("turn on", Collections.singleton("turn_on"));
        Domain domain = new Domain("light", Collections.singleton("lamp"));
        Command c = new Command(domain, operation, "Test", 0);
        assertEquals(domain, c.getDomain());
    }

    @Test
    public void getParameters() throws Exception {
        Operation operation = new Operation("turn on", Collections.singleton("turn_on"));
        Domain domain = new Domain("light", Collections.singleton("lamp"));
        Parameter p = new Parameter("Colore", ParameterType.COLOR);
        operation.setOptionalParameters(Collections.singleton(p));
        Command c = new Command(domain, operation, "Test", 0.78d);
        assertTrue(c.getParamValue().size() == 0);
        c.addParamValue(new ParamValuePair(p, null));
        assertTrue(c.getParamValue().size() == 1);
        assertEquals(c.getConfidence(), 0.78d, 0.0001d);

    }

    @Test(expected = RuntimeException.class)
    public void exceptions() {
        Operation operation = new Operation("turn on", Collections.singleton("turn_on"));
        Domain domain = new Domain("light", Collections.singleton("lamp"));
        Parameter p = new Parameter("Colore", ParameterType.COLOR);
        operation.setOptionalParameters(Collections.singleton(p));
        Command c = new Command(domain, operation, "Test", 0);
        Parameter p2 = new Parameter("Location", ParameterType.LOCATION);
        c.addParamValue(new ParamValuePair(p2, null));
    }

    @Test(expected = RuntimeException.class)
    public void exceptions2() {
        Operation operation = new Operation("turn on", Collections.singleton("turn_on"));
        Domain domain = new Domain("light", Collections.singleton("lamp"));
        Parameter p = new Parameter("Location", ParameterType.LOCATION);
        operation.setOptionalParameters(Collections.singleton(p));
        Command c = new Command(domain, operation, "Test", 0);
        c.addParamValue(new ParamValuePair(p, null));
        c.addParamValue(new ParamValuePair(p, null));
    }


    @Test
    public void toStringTest() throws Exception {
        Operation operation = new Operation("turn on", Collections.singleton("turn_on"));
        Domain domain = new Domain("light", Collections.singleton("lamp"));
        Parameter p = new Parameter("Colore", ParameterType.COLOR);
        operation.setOptionalParameters(Collections.singleton(p));
        Command c = new Command(domain, operation, "Test", 0);
        System.out.println(c);
        assertEquals("Command{operation=Operation{id=turn onwords[turn_on], optionalParameters=[Parameter{id='Colore', type=COLOR}], mandatoryParameters=[], textInvocation=[]}, domain=Domain{words[lamp]friendlyNames=[], operations=[]}, pairs=[], confidence=0.0, status=UNKNOWN}", c.toString());
    }

    @Test
    public void json() {
        Operation operation = new Operation("turn on", Collections.singleton("turn_on"));
        Domain domain = new Domain("light", Collections.singleton("lamp"));
        Parameter p = new Parameter("Location", ParameterType.LOCATION);
        operation.setOptionalParameters(Collections.singleton(p));
        Parameter p2 = new Parameter("Color", ParameterType.COLOR);
        operation.setMandatoryParameters(Collections.singleton(p2));

        Command c = new Command(domain, operation, "Test", 0.78d);
        List<ParamValuePair> pairs = Arrays.asList(new ParamValuePair(p, "London"), new ParamValuePair(p2, "Rosso"));
        c.addParamValue(pairs);
        assertEquals("{\"domain\":\"light\",\"operation\":\"turn on\",\"confidence\":\"0.78\",\"said\":\"Test\",\"status\":\"UNKNOWN\",\"understood\":\"No default sentence inserted\",\"paramValuePairs\":[{\"id\":\"Location\",\"type\":\"LOCATION\",\"value\":\"London\"},{\"id\":\"Color\",\"type\":\"COLOR\",\"value\":\"Rosso\"}]}", c.toJson());
    }

    @Test
    public void addValue() {
        Operation operation = new Operation("turn on", Collections.singleton("turn_on"));
        Domain domain = new Domain("light", Collections.singleton("lamp"));
        Parameter p = new Parameter("Location", ParameterType.LOCATION);
        operation.setOptionalParameters(Collections.singleton(p));
        Command c = new Command(domain, operation, "Test", 0.78d);
        Set<ParamValuePair> pair = Collections.singleton(new ParamValuePair(p, "London"));
        c.addParamValue(pair);
        assertEquals(pair, c.getParamValue());
    }

    @Test
    public void fullFilled() {
        Domain domain = new Domain("light", Collections.singleton("lamp"));
        Operation operation = new Operation("turn on", Collections.singleton("turn_on"));
        Command c = new Command(domain, operation, "Test", 0.78d);
        assertTrue(c.isFullFilled());
        /**
         * Add an optional parameter, still fullFilled
         */
        Parameter p = new Parameter("Location", ParameterType.LOCATION);
        operation.setOptionalParameters(Collections.singleton(p));
        assertTrue(c.isFullFilled());
        /**
         * Add a mandatory parameter, not fullFilled anymore
         */
        Parameter p2 = new Parameter("Color", ParameterType.COLOR);
        operation.setMandatoryParameters(Collections.singleton(p2));
        assertFalse(c.isFullFilled());
        /**
         * Adds the missing parameter
         */
        ParamValuePair pair = new ParamValuePair(p2, "Rosso");
        c.addParamValue(pair);
        assertTrue(c.isFullFilled());

    }

}