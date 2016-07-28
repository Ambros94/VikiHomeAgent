package NLP.ParamFinders;

import NLP.Params.MyDate;
import NLP.Params.Value;
import Things.ParameterType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
        Value pair = finder.find(ParameterType.DATETIME, "Turn on the light in 15 minutes");
        assertEquals(new MyDate("PT15M"), pair);

    }

    @Test
    public void findNothing() throws Exception {
        Value pair = finder.find(ParameterType.DATETIME, "Quando");
        assertEquals(null, pair);
    }

    @Test
    public void findTooMuch() throws Exception {
        Value pair = finder.find(ParameterType.DATETIME, "Three interesting dates are 18 Feb 1997, the 20th of july and 4 days from today.");
        assertEquals(new MyDate("1997-02-18"), pair);
    }

}