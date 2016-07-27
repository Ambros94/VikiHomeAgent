package Brain;

import NLP.Params.*;
import Things.Parameter;
import Things.ParameterType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ParamValueTest {
    @Test
    public void getValue() throws Exception {
        Parameter p = new Parameter("Colore", ParameterType.COLOR);
        ParamValue<NLP.Params.Color> paramValue = new ParamValue<>(p, new Color("#00FF00"));
        assertEquals(new Color("#00FF00"), paramValue.getValue());
        assertEquals(p, paramValue.getParameter());
    }

    @Test
    public void json() throws Exception {
        Parameter p = new Parameter("Colore", ParameterType.COLOR);
        ParamValue<NLP.Params.Color> paramValue = new ParamValue<>(p, new Color("#00FF00"));
        assertEquals("{\"id\":\"Colore\",\"type\":\"COLOR\",\"value\":[0,255,0]}",paramValue.toJson());
    }

    @Test
    public void hashTest() {
        Parameter p = new Parameter("Colore", ParameterType.COLOR);
        ParamValue<NLP.Params.Color> paramValue = new ParamValue<>(p, new Color("#00FF00"));
        ParamValue<NLP.Params.Color> paramValue2 = new ParamValue<>(p, new Color("#00FF01"));
        assertNotEquals(paramValue.hashCode(), paramValue2.hashCode());
        assertNotEquals(paramValue, paramValue2);
        assertEquals(new Color("#00FF00"), paramValue.getValue());
        assertEquals(p, paramValue.getParameter());
    }

    @Test
    public void StringerTest() {
        Parameter p = new Parameter("Colore", ParameterType.COLOR);
        ParamValue<NLP.Params.Color> paramValue = new ParamValue<>(p, new Color("#00FF00"));
        assertEquals("ParamValue{value=Color{r=0, g=255, b=0}, parameter=Parameter{id='Colore', type=COLOR}}", paramValue.toString());
    }
}