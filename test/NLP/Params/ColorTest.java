package NLP.Params;

import Things.ParameterType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ColorTest {
    @Test
    public void equals() throws Exception {
        Color c = new Color("#FF0001");
        Color c2 = new Color("#FF0002");
        Color cbis= new Color("#FF0001");
        assertEquals(c,cbis);
        assertNotEquals(c,c2);
        assertEquals(c.hashCode(),cbis.hashCode());
        assertNotEquals(c.hashCode(),c2.hashCode());

    }

    @Test(expected = NumberFormatException.class)
    public void rotto() throws Exception {
        new Color("#FF0TT01");
    }

    @Test
    public void getType() throws Exception {
        Color c = new Color("#FF0001");
        assertEquals(ParameterType.COLOR, c.getType());
    }
    @Test
    public void string() throws Exception {
        Color c = new Color("#FF0001");
        assertEquals("Color{r=255, g=0, b=1}", c.toString());
    }

    @Test
    public void toJson() throws Exception {
        Color c = new Color("#FF0001");
        assertEquals("[255,0,1]", c.toJson());
    }


}