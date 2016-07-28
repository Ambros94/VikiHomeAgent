package NLP.ParamFinders;

import NLP.Params.MyNumber;
import NLP.Params.Value;
import Things.ParameterType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NumberFinderTest {

    private NumberFinder finder;

    @Before
    public void init() {
        finder = new NumberFinder();
    }

    @Test
    public void getAssociatedType() throws Exception {
        assertEquals(ParameterType.NUMBER, finder.getAssociatedType());
    }


    @Test
    public void find() throws Exception {
        Value pair = finder.find(ParameterType.NUMBER, "Set light intensity to 80 percent");
        assertEquals(new MyNumber(80), pair);
    }

    @Test
    public void findMoreThanOne() throws Exception {
        Value pair2 = finder.find(ParameterType.NUMBER, "There are more than -2 and less than 12 numbers here");
        assertEquals(new MyNumber(-2), pair2);
    }

    @Test
    public void findNothing() throws Exception {
        Value pair2 = finder.find(ParameterType.NUMBER, "I'm very happy to say that there are no numbers in here");
        assertEquals(null, pair2);
    }


}