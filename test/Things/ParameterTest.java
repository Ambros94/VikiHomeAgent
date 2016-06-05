package Things;

import org.junit.Test;

import static org.junit.Assert.*;


public class ParameterTest {
    @Test
    public void getName() throws Exception {
        Parameter p = new Parameter("Colore", ParameterType.COLOR);
        assertEquals("Colore",p.getName());

    }

    @Test
    public void getType() throws Exception {
        Parameter p = new Parameter("Colore", ParameterType.COLOR);
        assertEquals(ParameterType.COLOR,p.getType());
    }

}