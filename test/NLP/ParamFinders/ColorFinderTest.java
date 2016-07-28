package NLP.ParamFinders;

import NLP.Params.Color;
import NLP.Params.Value;
import Things.ParameterType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


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
        Value pair = finder.find(ParameterType.COLOR, "Make the light red");
        assertEquals(new Color("#FF0000"), pair);
    }

    @Test
    public void findLongColor() throws Exception {
        Value pair = finder.find(ParameterType.COLOR, "Make the light sandy brown");
        assertEquals(new Color("#F4A460"), pair);
    }

}