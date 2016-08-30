package Brain;

import NLP.Params.Color;
import NLP.Params.Location;
import Things.Domain;
import Things.Operation;
import Things.Parameter;
import Things.ParameterType;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class CommandTest {


    @Test
    public void getOperation() throws Exception {
        Operation operation = new Operation("turn on", Collections.singleton("turn_on"));
        Domain domain = new Domain("light", Collections.singleton("lamp"));
        Command c = new Command(domain, operation, "Test",1,1);
        assertEquals(operation, c.getOperation());
    }

    @Test
    public void getDomain() throws Exception {
        Operation operation = new Operation("turn on", Collections.singleton("turn_on"));
        Domain domain = new Domain("light", Collections.singleton("lamp"));
        Command c = new Command(domain, operation, "Test",1,1);
        assertEquals(domain, c.getDomain());
    }

    @Test
    public void getParameters() throws Exception {
        Operation operation = new Operation("turn on", Collections.singleton("turn_on"));
        Domain domain = new Domain("light", Collections.singleton("lamp"));
        Parameter p = new Parameter("Colore", ParameterType.COLOR);
        operation.setOptionalParameters(Collections.singleton(p));
        Command c = new Command(domain, operation, "Test",1,1);
        assertTrue(c.getParamValue().size() == 0);
        c.addParamValue(p.getType(), new Color("#FF00AA"));
        assertTrue(c.getParamValue().size() == 1);
    }

    @Test(expected = RuntimeException.class)
    public void exceptions() {
        Operation operation = new Operation("turn on", Collections.singleton("turn_on"));
        Domain domain = new Domain("light", Collections.singleton("lamp"));
        Parameter p = new Parameter("Colore", ParameterType.COLOR);
        operation.setOptionalParameters(Collections.singleton(p));
        Command c = new Command(domain, operation, "Test", 0, 0);
        Parameter p2 = new Parameter("Location", ParameterType.LOCATION);
        c.addParamValue(p.getType(), new Color("red"));
    }

    @Test(expected = RuntimeException.class)
    public void exceptions2() {
        Operation operation = new Operation("turn on", Collections.singleton("turn_on"));
        Domain domain = new Domain("light", Collections.singleton("lamp"));
        Parameter p = new Parameter("Location", ParameterType.LOCATION);
        operation.setOptionalParameters(Collections.singleton(p));
        Command c = new Command(domain, operation, "Test", 0, 0);
        c.addParamValue(p.getType(), new Color("red"));
        c.addParamValue(p.getType(), new Color("red"));
    }


    @Test
    public void toStringTest() throws Exception {
        Operation operation = new Operation("turn on", Collections.singleton("turn_on"));
        Domain domain = new Domain("light", Collections.singleton("lamp"));
        Parameter p = new Parameter("Colore", ParameterType.COLOR);
        operation.setOptionalParameters(Collections.singleton(p));
        Command c = new Command(domain, operation, "Test", 0, 0);
        System.out.println(c);
        assertEquals("Command{operation=O {id=turn on, words[turn_on]}, domain=D{words[lamp], friendlyNames=[]}, pairs=[], finalConfidence=0.0, domainConfidence=0.0, operationConfidence=0.0, rightParameters=0, wrongParameters=0, status=UNKNOWN}", c.toString());
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

        Command c = new Command(domain, operation, "Test", 1.0, 1.0);
        c.addParamValue(ParameterType.LOCATION, new Location("London"));
        // MISSING COLOR
        assertEquals(CommandStatus.LOW_CONFIDENCE, c.getStatus());
        c.addParamValue(ParameterType.COLOR, new Color("#FF0000"));
        c.setFinalConfidence(1.0);
        assertEquals(CommandStatus.OK, c.getStatus());
        assertEquals("Test", c.getSaidSentence());
        assertTrue(c.equalsIds("light", "turn on"));
        assertFalse(c.equalsIds("light2", "turn on"));
        assertFalse(c.equalsIds("light", "turn on2"));
        assertEquals("{\"confidence\":\"1.00\",\"said\":\"Test\",\"domain\":\"light\",\"operation\":\"turn on\",\"status\":\"OK\",\"understood\":\"No default sentence inserted\",\"parameters\":[{\"id\":\"Location\",\"type\":\"LOCATION\",\"value\":\"London\"},{\"id\":\"Color\",\"type\":\"COLOR\",\"value\":[255,0,0]}]}", c.toJson());
    }

    @Test
    public void addValue() {
        Operation operation = new Operation("turn on", Collections.singleton("turn_on"));
        Domain domain = new Domain("light", Collections.singleton("lamp"));
        Parameter p = new Parameter("Location", ParameterType.LOCATION);
        operation.setOptionalParameters(Collections.singleton(p));

        Command c = new Command(domain, operation, "Test", 0, 0);
        Set<ParamValue> pair = Collections.singleton(new ParamValue<>(p, new Location("London")));
        c.addParamValue(ParameterType.LOCATION, new Location("London"));
        assertEquals(pair, c.getParamValue());
    }

    @Test
    public void fullFilled() {
        Domain domain = new Domain("light", Collections.singleton("lamp"));
        Operation operation = new Operation("turn on", Collections.singleton("turn_on"));
        Command c = new Command(domain, operation, "Test", 0, 0);
        assertNull(c.isFullFilled());
        /*
         * Add an optional parameter, still fullFilled
         */
        Parameter p = new Parameter("Location", ParameterType.LOCATION);
        operation.setOptionalParameters(Collections.singleton(p));
        assertNull(c.isFullFilled());
        /*
         * Add a mandatory parameter, not fullFilled anymore
         */
        Parameter p2 = new Parameter("Color", ParameterType.COLOR);
        operation.setMandatoryParameters(Collections.singleton(p2));
        assertNotNull(c.isFullFilled());
        /*
         * Adds the missing parameter
         */
        c.addParamValue(ParameterType.COLOR, new Color("#FF00AA"));
        assertNull(c.isFullFilled());

    }

}