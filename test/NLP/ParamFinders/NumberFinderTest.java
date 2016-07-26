package NLP.ParamFinders;

import Brain.ParamValue;
import NLP.Params.MyNumber;
import Things.Parameter;
import Things.ParameterType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
        Parameter parameter = new Parameter("Intensity", ParameterType.NUMBER);
        ParamValue pair = finder.find(parameter, "Set light intensity to 80 percent");
        assertEquals(new ParamValue<>(parameter, new MyNumber(80)), pair);
    }

    @Test
    public void findMoreThanOne() throws Exception {
        Parameter parameter = new Parameter("Numbers", ParameterType.NUMBER);
        ParamValue pair2 = finder.find(parameter, "There are more than -2 and less than 12 numbers here");
        assertEquals(new ParamValue<>(parameter, new MyNumber(-2)), pair2);
    }

    @Test
    public void findNothing() throws Exception {
        Parameter parameter = new Parameter("Numbers", ParameterType.NUMBER);
        ParamValue pair2 = finder.find(parameter, "I'm very happy to say that there are no numbers in here");
        assertEquals(null, pair2);
    }


}