package NLP.ParamFinders;

import Brain.ParamValue;
import Things.Parameter;
import Things.ParameterType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DateTimeFinderTest {

    private DateTimeFinder finder;

    @Before
    public void init() {
        finder = new DateTimeFinder();
    }

    @Test
    public void getAssociatedType() throws Exception {
        assertEquals(ParameterType.DATETIME, finder.getAssociatedType());
    }

    @Test
    public void find() throws Exception {
        Parameter p = new Parameter("Quando", ParameterType.DATETIME);
        ParamValue pair = finder.find(p, "Turn on the light in 15 minutes");
        assertEquals("'PT15M'", pair.getValue().toJson());

    }

    @Test
    public void findNothing() throws Exception {
        Parameter p = new Parameter("Quando", ParameterType.DATETIME);
        ParamValue pair = finder.find(p, "Turn on the light");
        assertEquals(null, pair);
    }

    @Test
    public void findTooMuch() throws Exception {
        Parameter p = new Parameter("Quando", ParameterType.DATETIME);
        ParamValue pair = finder.find(p, "Three interesting dates are 18 Feb 1997, the 20th of july and 4 days from today.");
        assertEquals("'1997-02-18'", pair.getValue().toJson());
    }

}