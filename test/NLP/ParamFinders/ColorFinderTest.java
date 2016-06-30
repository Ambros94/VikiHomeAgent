package NLP.ParamFinders;

import Brain.ParamValuePair;
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
        ParamValuePair pair = finder.find(parameter, "Make the light red");
        System.out.println(pair.toJson());
    }

}