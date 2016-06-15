package Things;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class ParameterTest {
    @Test
    public void getName() throws Exception {
        Parameter p = new Parameter("Colore", ParameterType.COLOR);
        assertEquals("Colore", p.getId());

    }

    @Test
    public void getType() throws Exception {
        Parameter p = new Parameter("Colore", ParameterType.COLOR);
        assertEquals(ParameterType.COLOR, p.getType());
    }

    @Test
    public void fromJson() throws Exception {
        assertEquals(new Parameter("lampColor", ParameterType.COLOR), Parameter.fromJson("{'type':'COLOR','id':'lampColor'}"));
    }

}