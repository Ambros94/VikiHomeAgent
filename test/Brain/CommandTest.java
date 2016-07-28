package Brain;

import NLP.Params.Color;
import NLP.Params.Location;
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
        c.addParamValue(p.getType(), new Color("#FF00AA"));
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
        c.addParamValue(p.getType(), new Color("red"));
    }

    @Test(expected = RuntimeException.class)
    public void exceptions2() {
        Operation operation = new Operation("turn on", Collections.singleton("turn_on"));
        Domain domain = new Domain("light", Collections.singleton("lamp"));
        Parameter p = new Parameter("Location", ParameterType.LOCATION);
        operation.setOptionalParameters(Collections.singleton(p));
        Command c = new Command(domain, operation, "Test", 0);
        c.addParamValue(p.getType(), new Color("red"));
        c.addParamValue(p.getType(), new Color("red"));
    }


    @Test
    public void toStringTest() throws Exception {
        Operation operation = new Operation("turn on", Collections.singleton("turn_on"));
        Domain domain = new Domain("light", Collections.singleton("lamp"));
        Parameter p = new Parameter("Colore", ParameterType.COLOR);
        operation.setOptionalParameters(Collections.singleton(p));
        Command c = new Command(domain, operation, "Test", 0);
        System.out.println(c);
        assertEquals("Command{operation=Operation{id=turn onwords[turn_on], optionalParameters=[Parameter{id='Colore', type=COLOR}], mandatoryParameters=[], textInvocation=[]}, domain=Domain{words[lamp]\n" +
                "friendlyNames=[]\n" +
                "operations=[]}, pairs=[], confidence=0.0, status=LOW_CONFIDENCE}", c.toString());
    }

    @Test
    public void json() {
        /*
        Create a command with location as optional parameter and color as mandatory parameter
         */
        Operation operation = new Operation("turn on", Collections.singleton("turn_on"));
        Domain domain = new Domain("light", Collections.singleton("lamp"));
        Parameter p = new Parameter("Location", ParameterType.LOCATION);
        operation.setOptionalParameters(Collections.singleton(p));
        Parameter p2 = new Parameter("Color", ParameterType.COLOR);
        operation.setMandatoryParameters(Collections.singleton(p2));

        Command c = new Command(domain, operation, "Test", 0.78d);
        c.addParamValue(ParameterType.LOCATION, new Location("London"));
        // MISSING LOCATION
        assertEquals(CommandStatus.MISSING_PARAMETERS, c.getStatus());
        c.addParamValue(ParameterType.COLOR, new Color("#FF0000"));
        assertEquals(CommandStatus.OK, c.getStatus());
        assertEquals("Test", c.getSaidSentence());
        assertTrue(c.equalsIds("light", "turn on"));
        assertFalse(c.equalsIds("light2", "turn on"));
        assertFalse(c.equalsIds("light", "turn on2"));
        assertEquals("{\"domain\":\"light\",\"operation\":\"turn on\",\"confidence\":\"0.78\",\"said\":\"Test\",\"status\":\"OK\",\"understood\":\"No default sentence inserted\",\"parameters\":[{\"id\":\"Location\",\"type\":\"LOCATION\",\"value\":\"London\"},{\"id\":\"Color\",\"type\":\"COLOR\",\"value\":[255,0,0]}]}", c.toJson());
    }

    @Test
    public void addValue() {
        Operation operation = new Operation("turn on", Collections.singleton("turn_on"));
        Domain domain = new Domain("light", Collections.singleton("lamp"));
        Parameter p = new Parameter("Location", ParameterType.LOCATION);
        operation.setOptionalParameters(Collections.singleton(p));

        Command c = new Command(domain, operation, "Test", 0.78d);
        Set<ParamValue> pair = Collections.singleton(new ParamValue<>(p, new Location("London")));
        c.addParamValue(ParameterType.LOCATION, new Location("London"));
        assertEquals(pair, c.getParamValue());
    }

    @Test
    public void fullFilled() {
        Domain domain = new Domain("light", Collections.singleton("lamp"));
        Operation operation = new Operation("turn on", Collections.singleton("turn_on"));
        Command c = new Command(domain, operation, "Test", 0.78d);
        assertTrue(c.isFullFilled());
        /*
         * Add an optional parameter, still fullFilled
         */
        Parameter p = new Parameter("Location", ParameterType.LOCATION);
        operation.setOptionalParameters(Collections.singleton(p));
        assertTrue(c.isFullFilled());
        /*
         * Add a mandatory parameter, not fullFilled anymore
         */
        Parameter p2 = new Parameter("Color", ParameterType.COLOR);
        operation.setMandatoryParameters(Collections.singleton(p2));
        assertFalse(c.isFullFilled());
        /*
         * Adds the missing parameter
         */
        c.addParamValue(ParameterType.COLOR, new Color("#FF00AA"));
        assertTrue(c.isFullFilled());

    }

}