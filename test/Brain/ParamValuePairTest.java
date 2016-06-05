package Brain;

import Things.Parameter;
import Things.ParameterType;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class ParamValuePairTest {
    @Test
    public void getValue() throws Exception {
        Parameter p = new Parameter("Colore", ParameterType.COLOR);
        ParamValuePair pair = new ParamValuePair(p, Color.black);
        assertEquals(pair.getValue(), Color.black);
    }

    @Test
    public void getParameter() throws Exception {
        Parameter p = new Parameter("Colore", ParameterType.COLOR);
        ParamValuePair pair = new ParamValuePair(p, Color.black);
        assertEquals(pair.getParameter(), p);
    }

    @Test
    public void equals() throws Exception {
        Parameter p = new Parameter("Colore", ParameterType.COLOR);
        Parameter p2 = new Parameter("Location", ParameterType.LOCATION);

        ParamValuePair pair1 = new ParamValuePair(p, Color.black);
        ParamValuePair pair2 = new ParamValuePair(p, Color.white);
        assertTrue(pair1.equals(pair2));
        ParamValuePair pair3 = new ParamValuePair(p2, "Lecco");
        assertFalse(pair1.equals(pair3));

    }

    @Test
    public void hashTest() {
        Parameter p = new Parameter("Colore", ParameterType.COLOR);
        ParamValuePair pair1 = new ParamValuePair(p, Color.black);
        ParamValuePair pair2 = new ParamValuePair(p, Color.white);
        assertEquals(pair1.hashCode(),pair2.hashCode());
    }
}