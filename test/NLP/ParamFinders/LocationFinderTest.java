package NLP.ParamFinders;

import Brain.ParamValuePair;
import Things.Parameter;
import Things.ParameterType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LocationFinderTest {

    private LocationFinder finder;

    @Before
    public void init() {
        finder = new LocationFinder();
    }

    @Test
    public void getAssociatedType() throws Exception {
        assertEquals(ParameterType.LOCATION, finder.getAssociatedType());
    }

    @Test
    public void find() throws Exception {
        Parameter p = new Parameter("Location", ParameterType.LOCATION);
        ParamValuePair pair = new ParamValuePair(p, "London");
        assertEquals(pair, finder.find(p, "I want to live in London"));
    }

}