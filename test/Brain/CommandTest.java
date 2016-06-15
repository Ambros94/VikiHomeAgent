package Brain;

import Things.Domain;
import Things.Operation;
import Things.Parameter;
import Things.ParameterType;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

public class CommandTest {

    @Test
    public void getOperation() throws Exception {
        Operation operation = new Operation("turn on", Collections.singleton("turn_on"));
        Domain domain = new Domain("light", Collections.singleton("lamp"));
        Command c = new Command(domain, operation);
        assertEquals(operation, c.getOperation());
    }

    @Test
    public void getDomain() throws Exception {
        Operation operation = new Operation("turn on", Collections.singleton("turn_on"));
        Domain domain = new Domain("light", Collections.singleton("lamp"));
        Command c = new Command(domain, operation);
        assertEquals(domain, c.getDomain());
    }

    @Test
    public void getParameters() throws Exception {
        Operation operation = new Operation("turn on", Collections.singleton("turn_on"));
        Domain domain = new Domain("light", Collections.singleton("lamp"));
        Parameter p = new Parameter("Colore", ParameterType.COLOR);
        operation.setOptionalParameters(Collections.singleton(p));
        Command c = new Command(domain, operation);
        assertTrue(c.getParamValue().size() == 0);
        c.addParamValue(new ParamValuePair(p, null));
        assertTrue(c.getParamValue().size() == 1);
    }

    @Test(expected = RuntimeException.class)
    public void exceptions() {
        Operation operation = new Operation("turn on", Collections.singleton("turn_on"));
        Domain domain = new Domain("light", Collections.singleton("lamp"));
        Parameter p = new Parameter("Colore", ParameterType.COLOR);
        operation.setOptionalParameters(Collections.singleton(p));
        Command c = new Command(domain, operation);
        Parameter p2 = new Parameter("Location", ParameterType.LOCATION);
        c.addParamValue(new ParamValuePair(p2, null));
    }

    @Test(expected = RuntimeException.class)
    public void exceptions2() {
        Operation operation = new Operation("turn on", Collections.singleton("turn_on"));
        Domain domain = new Domain("light", Collections.singleton("lamp"));
        Parameter p = new Parameter("Location", ParameterType.LOCATION);
        operation.setOptionalParameters(Collections.singleton(p));
        Command c = new Command(domain, operation);
        c.addParamValue(new ParamValuePair(p, null));
        c.addParamValue(new ParamValuePair(p, null));
    }


    @Test
    public void toStringTest() throws Exception {
        Operation operation = new Operation("turn on", Collections.singleton("turn_on"));
        Domain domain = new Domain("light", Collections.singleton("lamp"));
        Parameter p = new Parameter("Colore", ParameterType.COLOR);
        operation.setOptionalParameters(Collections.singleton(p));
        Command c = new Command(domain, operation);
        System.out.println(c);
        assertEquals("Command{operation=Operation{Synonyms{id='turn on', words=[turn_on],\n" +
                " adjectiveSynonyms=[],\n" +
                " adverbSynonyms=[],\n" +
                " nounSynonyms=[],\n" +
                " verbSynonyms=[hinge on, charge, trip out, switch on, sex, charge up, excite, arouse, get off, depend upon, ride, turn on, rouse, depend on, hinge upon, trip, commove, devolve on, agitate, wind up]}optionalParameters=[Parameter{id='Colore', type=COLOR}], mandatoryParameters=[], textInvocation='null'}, domain=Domain{friendlyNames=[]Synonyms{id='light', words=[lamp],\n" +
                " adjectiveSynonyms=[unaccented, wakeful, sluttish, tripping, weak, scant, calorie-free, light-headed, lite, swooning, faint, abstemious, idle, light-colored, lightsome, clear, clean, easy, lightheaded, unclouded, wanton, light, promiscuous, low-cal, loose, short],\n" +
                " adverbSynonyms=[],\n" +
                " nounSynonyms=[luminance, Christ Within, sparkle, twinkle, illumination, lightness, luminousness, Inner Light, lighter, visible light, lamp, lighting, ignitor, Light Within, visible radiation, brightness, brightness level, light, spark, Light, luminosity, igniter, light source],\n" +
                " verbSynonyms=[ignite, get off, illumine, fall, light, illume, fire up, light up, perch, alight, illuminate, dismount, get down, unhorse]}, operations=[]}, parameters=[]}", c.toString());
    }

}