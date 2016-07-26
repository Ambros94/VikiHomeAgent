package NLP.ParamFinders;

import Brain.ParamValue;
import NLP.Params.Color;
import Things.Parameter;
import Things.ParameterType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class ColorFinderTest {
    private ColorFinder finder;

    @Before
    public void init() {
        finder = new ColorFinder();
    }

    @Test
    public void getAssociatedType() throws Exception {
        assertEquals(ParameterType.COLOR, finder.getAssociatedType());
    }

    @Test
    public void find() throws Exception {
        Parameter parameter = new Parameter("Colore", ParameterType.COLOR);
        ParamValue pair = finder.find(parameter, "Make the light red");
        System.out.println(pair);
        assertEquals(new ParamValue<>(parameter, new Color("#FF0000")), pair);
    }

    @Test
    public void findLongColor() throws Exception {
        Parameter parameter = new Parameter("Colore", ParameterType.COLOR);
        ParamValue pair = finder.find(parameter, "Make the light sandy brown");
        assertEquals(new ParamValue<>(parameter, new Color("#F4A460")), pair);
    }

}