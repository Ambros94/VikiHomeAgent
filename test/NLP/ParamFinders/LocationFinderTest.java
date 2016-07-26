package NLP.ParamFinders;

import Brain.ParamValue;
import NLP.Params.Location;
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
        ParamValue pair = new ParamValue<>(p, new Location("London"));
        assertEquals(pair, finder.find(p, "I want to live in London"));
    }

}