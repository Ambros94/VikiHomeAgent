package NLP.ParamFinders;

import NLP.Params.Location;
import Things.ParameterType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
        assertEquals(new Location("London"), finder.find(ParameterType.LOCATION, "I want to live in London"));
    }

}